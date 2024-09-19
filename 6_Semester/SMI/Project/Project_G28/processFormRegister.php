<?php
if ( !isset( $_SESSION ) ) {
    session_start();
}

require_once("lib/db.php");
require_once("lib/lib.php");
require_once( "lib/lib-mail-v2.php" );
require_once( "lib/HtmlMimeMail.php" );

require_once( "regExps.php" );
  
$flags[] = FILTER_NULL_ON_FAILURE;
  
$serverName = filter_input( INPUT_SERVER, 'SERVER_NAME', FILTER_UNSAFE_RAW, $flags );
$referer = filter_input( INPUT_SERVER, 'HTTP_REFERER', FILTER_UNSAFE_RAW, $flags );
  
$activationUrl = "http://" . $serverName . webAppName() . "activateAccount.php";

$captcha = filter_input( INPUT_POST, 'captcha', FILTER_UNSAFE_RAW, $flags);

if ( ($captcha===FALSE) || (!isset( $_SESSION['captcha'] )) || ($_SESSION['captcha'] != $captcha) ) {
    // Não existe imagem de captcha ou valor do captcha diferente da imagem de captcha
    redirectToPage( $referer, "Invalid arguments", "Invalid or non existing captch", 5);
}
  
$password1 = filter_input( INPUT_POST, 'password1', FILTER_UNSAFE_RAW, $flags);
$password2 = filter_input( INPUT_POST, 'password2', FILTER_UNSAFE_RAW, $flags);

if ( $password1===FALSE || $password2===FALSE ||  $password1!==$password2 || !preg_match($filterPassword, $password1)) {
    // As passwords não existem ou não fazem match 
    redirectToPage( $referer, "Invalid arguments", "Invalid or non existing passwords", 5);
}

$userName = filter_input( INPUT_POST, 'userName', FILTER_UNSAFE_RAW, $flags);
if ( $userName===FALSE || !preg_match($filterUserName, $userName) ) {
    redirectToPage( $referer, "Invalid arguments", "Invalid or non existing user name", 5);
}

$email = filter_input( INPUT_POST, 'email', FILTER_SANITIZE_EMAIL, $flags);
if ( ($email===FALSE) || !filter_var( $email, FILTER_VALIDATE_EMAIL ) ) {
    redirectToPage( $referer, "Invalid arguments", "Invalid email", 5);
}
  
// Aqui existe:
// captch, username, password, e-mail
// Inserir na base de dados, se não falhar enviar um e-mail de confirmação
// A inserção na base de dados implica o insert em duas tabelas distintas
// Como os dois inserts têm de funcionar vão ser realizados no contexto de uma transação
  
dbConnect( ConfigFile );
  
$dataBaseName = $GLOBALS['configDataBase']->db;

mysqli_autocommit( $GLOBALS['ligacao'], false);
  
mysqli_select_db( $GLOBALS['ligacao'], $dataBaseName );
  
$queryString =
        "INSERT INTO `$dataBaseName`.`perfil` " .
        "(`name` ,`password`, `email`, `registerDate`, `active`) " .
        "VALUES ('$userName', '$password1', '$email', CURDATE(), '0')";

if ( mysqli_query( $GLOBALS['ligacao'], $queryString )==FALSE ) {
    $browserMessage = "Could create account.<br>\n";
    $browserMessage .= "Datails:<br>\n";
    $browserMessage .= dbGetLastError() . "<br>\n";
    
    mysqli_rollback( $GLOBALS['ligacao'] );
    
    dbDisconnect();
    
    redirectToPage( $referer, "Error creating account", $browserMessage, 5);
}
else {
    $lastIdUser = mysqli_insert_id( $GLOBALS['ligacao'] );
}

$challenge = md5( time() . $captcha . $userName );

$queryString =
        "INSERT INTO `$dataBaseName`.`perfil-challenge` " .
        "(`idUser` ,`challenge`, `registerDate`) " .
        "VALUES ( '$lastIdUser', '$challenge', CURDATE() )";

if ( mysqli_query( $GLOBALS['ligacao'], $queryString )==FALSE ) {
    $browserMessage = "Could not create challenge.<br>\n";
    $browserMessage .= "Datails:<br>\n";
    $browserMessage .= dbGetLastError() . "<br>\n";

    mysqli_rollback( $GLOBALS['ligacao'] );
    
    dbDisconnect();
    
    redirectToPage( $referer, "Error saving challenge", $browserMessage, 5);
}

mysqli_commit( $GLOBALS['ligacao'] );

$queryString = "SELECT * FROM `$dataBaseName`.`email-accounts`";
$queryResult = mysqli_query( $GLOBALS['ligacao'], $queryString );
$record = mysqli_fetch_array( $queryResult );
        
$smtpServer = $record[ 'smtpServer' ];
$port = intval( $record[ 'port' ] );
$useSSL = boolval( $record[ 'useSSL' ] );
$timeout = intval( $record[ 'timeout' ] );
$loginName = $record[ 'loginName' ];
$password = $record[ 'password' ];
$fromEmail = $record[ 'email' ];
$fromName = $record[ 'displayName' ];
    
mysqli_free_result( $queryResult );
  
dbDisconnect();
  
$title = "Success when creating new account";

$from = "$fromName <" . $fromEmail . ">";
$replyTo = $from;

$to = $userName . " <" . $email . ">";
$subject = "Account confirmation";

$activationLink=$activationUrl."?challenge=".$challenge;

$EmailMessageHTML = <<<EOD
<html>
    <body>
        <p>Your account is created.</p>
        <p>Click the follwing link to activate your account.</p>
        <a href="$activationLink">Activate account.</a>
    </body>
</html>
EOD;

$EmailMessagePlain=$activationUrl."?challenge=".$challenge;

$mail = new HtmlMimeMail();
$mail->add_html( $EmailMessageHTML, $EmailMessagePlain);

$mail->build_message();

$result = $mail->send(
              $smtpServer,
              $useSSL,
              $port,
              $loginName,
              $password,
              $userName, 
              $email,
              $fromName, 
              $fromEmail,
              $subject,
              "X-Mailer: Html Mime Mail Class");
      
if ($result == TRUE) {
    $browserMessage = "The account confirmation e-mail was sent $email.";
}
else {
    $browserMessage = "The account confirmation e-mail could not be delivered to $email.";
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
        <title>Register New User Response</title>

        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link REL="stylesheet" TYPE="text/css" href="Styles/GlobalStyle.css">
    </head>
    <body>
        <p><?php echo $browserMessage;?></p>
        
        <hr>
        <br><a href="<?php echo ".";?>">Back</a>
    </body>
</html>
