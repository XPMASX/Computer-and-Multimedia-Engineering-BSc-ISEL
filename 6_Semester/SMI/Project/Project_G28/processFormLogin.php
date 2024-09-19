<?php
  if ( !isset( $_SESSION ) ) {
    session_start();
  }

  require_once("lib/lib.php");
  require_once("lib/db.php");
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  $userName = filter_input( INPUT_POST, 'username', FILTER_UNSAFE_RAW, $flags);
  $password = filter_input( INPUT_POST, 'password', FILTER_UNSAFE_RAW,$flags);
  
  if ( $userName==null || $password==null ) {
    $nextUrl = "formLogin.php";
  }
  else {
    $userId = isValid($userName, $password, "basic");
    if ( $userId>0 ) {
      $_SESSION[ 'username' ] = $userName;
      $_SESSION[ 'id' ] = $userId;

      if ( isset( $_SESSION['locationAfterAuth'] ) ) {
        $baseNextUrl = $baseUrl;
        $nextUrl = $_SESSION['locationAfterAuth'];
      }
      else {
        $nextUrl = "index.php";
      }
    }
    else {
      $nextUrl = "formLogin.php";
    }
  }
  
  header( "Location: " . $nextUrl );
?>