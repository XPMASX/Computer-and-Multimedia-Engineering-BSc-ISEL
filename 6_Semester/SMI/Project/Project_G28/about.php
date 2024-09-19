<!-- main_template.php (Updated) -->
<!DOCTYPE html>
<html>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");

if (!isset($_SESSION)) {
    session_start();
}

$articles = getRecentArticles(isset($_SESSION['id']));
?>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>About</title>

    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">

    <script type="text/javascript" src="./scripts/forms.js"></script>
</head>

<body>
<div id="containerDiv">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php") ?>
    </div>


    <div id="mainContent" style=" display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>
        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">About</h1>
            </div>
            <div style="clear:both;"></div>
            <div id="contentDiv">
                <p style="margin-left: 10px">Trabalho realizado pelo grupo 28: Diogo Santos A48626 e Pedro Silva A48965</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>