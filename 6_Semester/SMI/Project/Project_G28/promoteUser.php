<?php
require_once("lib/lib.php");
require_once("lib/db.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['userId'])) {
        $userId = $_POST['userId'];
        promoteUser($userId);
    }
}
?><?php
