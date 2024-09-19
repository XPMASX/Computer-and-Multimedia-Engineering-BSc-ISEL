<?php
  $filterCaptcha = "/^([a-z0-9]{9})/";
  
  $filterUserName = "/^([a-zA-Z0-9]{4,16})/";

  $filterCategory = "/^[A-Z][a-zA-Z0-9]{2,15}$/";

  $filterTitle = "/^[A-Z][a-zA-Z0-9_-\s]{1,15}$/";

  $filterPassword = "/^([a-zA-Z0-9]{4,16})/";
  
  $filterEmail = "/^([a-z0-9_\.\-])+\@(([a-z0-9\-])+\.)+([a-z0-9]{2,4})$/i";
?>
