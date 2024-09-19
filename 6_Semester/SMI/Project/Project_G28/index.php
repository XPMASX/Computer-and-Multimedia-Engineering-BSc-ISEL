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
    <title>Home</title>

    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">

    <script type="text/javascript" src="./scripts/forms.js"></script>
</head>

<body>
<div id="containerDiv">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php") ?>
    </div>

    <div class="nav-container"
         style="position: absolute; right: 0; width: 20%; height: 100%; display: flex; flex-direction: column; align-items: center; justify-content: center;">
        <?php echo "<h2>Recent Articles</h2>"; ?>
        <nav>
            <ul>
                <?php foreach ($articles as $article): ?>
                    <li>
                        <a href="showArticle.php?title=<?php echo $article['title']; ?>"><?php echo $article['title']; ?></a>
                    </li>
                <?php endforeach; ?>
            </ul>
        </nav>
    </div>

    <div id="mainContent" style=" display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>
        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">Home</h1>
            </div>
        </div>
    </div>
</div>
</body>
</html>