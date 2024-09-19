<?php
  require_once("lib/lib.php");
  require_once("lib/db.php");
  
  $flags[] = FILTER_NULL_ON_FAILURE;
  
  $title = filter_input( INPUT_GET, 'title', FILTER_UNSAFE_RAW, $flags);
  
  if ( existArticleField( "title", $title) ) {
  	echo "#FF0000;Title already exists";
  }
  else {
  	echo "#00FF00;Title available";
  }
?>