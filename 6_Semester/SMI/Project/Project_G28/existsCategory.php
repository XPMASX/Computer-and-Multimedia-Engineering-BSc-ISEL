<?php
  require_once("lib/lib.php");
  require_once("lib/db.php");
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  $category = filter_input( INPUT_GET, 'category', FILTER_UNSAFE_RAW, $flags);
  
  if ( existCategory( "category", $category) ) {
  	echo "#FF0000;Category already exists";
  }
  else {
  	echo "#00FF00;Category available";
  }
?>