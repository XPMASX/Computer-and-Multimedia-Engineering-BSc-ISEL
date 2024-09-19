<?php
  require_once("lib/lib.php");
  require_once("lib/db.php");
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  $name = filter_input( INPUT_GET, 'userName', FILTER_UNSAFE_RAW, $flags);
  
  if ( existUserField( "name", $name) ) {
  	echo "#FF0000;User name already exists";
  }
  else {
  	echo "#00FF00;User name available";
  }
?>