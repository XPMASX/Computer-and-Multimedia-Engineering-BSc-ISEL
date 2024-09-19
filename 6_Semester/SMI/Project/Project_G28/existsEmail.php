<?php
  require_once("lib/lib.php");
  require_once("lib/db.php");
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  $email = filter_input( INPUT_GET, 'email', FILTER_SANITIZE_EMAIL, $flags);
  
  if ( existUserField( "email", $email) ) {
  	echo "#FF0000;E-mail address already exists";
  }
  else {
  	echo "#00FF00;E-mail address available";
  }
?>