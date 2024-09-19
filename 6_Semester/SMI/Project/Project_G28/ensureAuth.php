<?php
  if ( !isset( $_SESSION ) ) {
    session_start();
  }
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  if ( !isset($_SESSION['username']) ) {
  	
    $_SESSION[ 'locationAfterAuth' ] = filter_input( INPUT_SERVER, 'REQUEST_URI', FILTER_SANITIZE_URL, $flags);
      
    header( "Location: formLogin.php" );
    exit;
  }
?>