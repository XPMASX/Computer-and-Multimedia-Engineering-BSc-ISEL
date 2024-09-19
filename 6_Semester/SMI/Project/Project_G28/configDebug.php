<?php
$debug = false;

if ( $debug===FALSE ) {
  $captchaValue = @substr( md5( time() ), 0, 9);
  $captchaValueForm = "";
  
  $userValueForm = "";
  $emailValueForm = "";
}
else {
  $captchaValue = @substr( md5( "abcdfeghi" ), 0, 9);
  $captchaValueForm = "value=\"$captchaValue\"";
  
  $userValueForm = "value=\"user3\"";
  $emailValueForm = "value=\"cgoncalves@deetc.isel.pt\"";
}
?>
