<?php
    require_once("lib/db.php");
    require_once("lib/lib.php");

    $flags[] = FILTER_NULL_ON_FAILURE;

    $challenge = filter_input( INPUT_GET, 'challenge', FILTER_UNSAFE_RAW, $flags);

    if ( $challenge==null ) {
        header( "Location: " . "formRegister.php" );
        exit;
    }

    dbConnect( ConfigFile );
  
    $dataBaseName = $GLOBALS['configDataBase']->db;

    $abortAccountActivation = false;
    
    mysqli_autocommit( $GLOBALS['ligacao'], false);

    mysqli_select_db( $GLOBALS['ligacao'], $dataBaseName );

    $querySelectIdFromChallenge =
            "SELECT `idUser` FROM `$dataBaseName`.`perfil-challenge` " .
            "WHERE `challenge`='$challenge'";

    if ( ($result = mysqli_query($GLOBALS['ligacao'], $querySelectIdFromChallenge)) == FALSE ) {
        $title = "Error processing challenge";

        $browserMessage = "Error processing challenge.<br>\n";
        $browserMessage .= "Datails:<br>\n";

        $browserMessage .= dbGetLastError() . "<br>\n";
        
        $abortAccountActivation = true;

        $nextUrl = "formRegister.php";
      }
    else {
        $accountData = mysqli_fetch_array( $result );
        $id = $accountData[ 'idUser' ];

        $queryActivateAccount =
                "UPDATE `$dataBaseName`.`perfil` SET " .
                "`active`='1' " .
                "WHERE `idUser`='$id'";

        if ( mysqli_query($GLOBALS['ligacao'], $queryActivateAccount) == FALSE) {
            $title = "Error activating account";

            $browserMessage = "Error activating account.<br>\n";
            $browserMessage .= "Datails:<br>\n";
            $browserMessage .= "" . mysqli_errno($GLOBALS['ligacao']) . ": " . mysqli_error($GLOBALS['ligacao']) . "<br>\n";
            
            $abortAccountActivation = true;
        }
        else {
            $queryDelete =
                    "DELETE FROM `$dataBaseName`.`perfil-challenge` " .
                    "WHERE `idUser`='$id'";
            if ( mysqli_query( $GLOBALS['ligacao'], $queryDelete)==false ) {
                $abortAccountActivation = true;
            }

            $queryPermissions =
                    "INSERT INTO `$dataBaseName`.`perfil-permissions` " .
                    "(`idRole` ,`idUser`) " .
                    "VALUES ( '3', '$id')";
            if ( mysqli_query($GLOBALS['ligacao'], $queryPermissions)==false ) {
                $abortAccountActivation = true;
                
                $title = "Insuccess";
                $browserMessage = "Account activation was not possible.<br>\n";
            }
            else {
                $title = "Success";
                $browserMessage = "Account is active.<br>\n";
            }
            
            $nextUrl = "formLogin.php";
        }
  }

    if ( $abortAccountActivation==true ) {
        mysqli_rollback( $GLOBALS['ligacao'] );
    }
    else {
        mysqli_commit( $GLOBALS['ligacao'] );
    }
  
    dbDisconnect();

    redirectToPage($nextUrl, $title, $browserMessage, 5);
?>