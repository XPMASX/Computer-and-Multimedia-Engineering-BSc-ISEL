<?php
if ( !isset( $_SESSION ) ) {
    session_start();
}

require_once("lib/db.php");
require_once("lib/lib.php");
require_once( "lib/lib-mail-v2.php" );
require_once( "lib/HtmlMimeMail.php" );

require_once( "regExps.php" );

if (isset($_GET['profile'])) {
    $perfilName = $_GET['profile'];
} else {
    // Handle the case where no id is provided in the URL
    echo "No idUser provided.";
    return;
}

$flags[] = FILTER_NULL_ON_FAILURE;

$serverName = filter_input( INPUT_SERVER, 'SERVER_NAME', FILTER_UNSAFE_RAW, $flags );
$referer = filter_input( INPUT_SERVER, 'HTTP_REFERER', FILTER_UNSAFE_RAW, $flags );

$password1 = filter_input( INPUT_POST, 'password1', FILTER_UNSAFE_RAW, $flags);
$password2 = filter_input( INPUT_POST, 'password2', FILTER_UNSAFE_RAW, $flags);

$isPasswordChanged = filter_input(INPUT_POST, 'isPasswordChanged', FILTER_VALIDATE_BOOLEAN);

if ($isPasswordChanged) {
    if ($password1 === FALSE || $password2 === FALSE || $password1 !== $password2 || !preg_match($filterPassword, $password1)) {
        // Passwords do not exist or do not match
        redirectToPage($referer, "Invalid arguments", "Invalid or non existing passwords", 5);
    }
}

$userName = filter_input( INPUT_POST, 'userName', FILTER_UNSAFE_RAW, $flags);
if ( $userName===FALSE || !preg_match($filterUserName, $userName) ) {
    redirectToPage( $referer, "Invalid arguments", "Invalid or non existing user name", 5);
}

$email = filter_input( INPUT_POST, 'email', FILTER_SANITIZE_EMAIL, $flags);
if ( ($email===FALSE) || !filter_var( $email, FILTER_VALIDATE_EMAIL ) ) {
    redirectToPage( $referer, "Invalid arguments", "Invalid email", 5);
}

dbConnect(ConfigFile);

$dataBaseName = $GLOBALS['configDataBase']->db;

mysqli_select_db($GLOBALS['ligacao'], $dataBaseName);

// Start the transaction
mysqli_begin_transaction($GLOBALS['ligacao']);

if ($isPasswordChanged) {
    $queryProfile =
        "UPDATE `$dataBaseName`.`perfil` " .
        "SET `name` = '$userName', `password` = '$password1', `email` = '$email' " .
        "WHERE `name` = '$perfilName'";
} else {
    $queryProfile =
        "UPDATE `$dataBaseName`.`perfil` " .
        "SET `name` = '$userName', `email` = '$email' " .
        "WHERE `name` = '$perfilName'";
}

// Execute the query
$resultProfile = mysqli_query($GLOBALS['ligacao'], $queryProfile);

// Update every article name where name is $perfilName
$queryArticle =
    "UPDATE `$dataBaseName`.`article` " .
    "SET `name` = '$userName' " .
    "WHERE `name` = '$perfilName'";

// Execute the query
$resultArticle = mysqli_query($GLOBALS['ligacao'], $queryArticle);

// Check if both queries were successful
if ($resultProfile && $resultArticle) {
    // If both queries were successful, commit the transaction
    mysqli_commit($GLOBALS['ligacao']);

    // Disconnect from the database
    dbDisconnect();

    // Redirect to logout.php
    header("Location: logout.php");
    exit();
} else {
    // If any query failed, roll back the transaction
    mysqli_rollback($GLOBALS['ligacao']);
}

dbDisconnect();


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
