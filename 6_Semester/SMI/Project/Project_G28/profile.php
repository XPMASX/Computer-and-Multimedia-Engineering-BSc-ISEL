<!DOCTYPE html>
<html>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");

if (!isset($_SESSION)) {
    session_start();
}

if ($_GET['profile']!= '') {
    $perfilName = $_GET['profile'];
    $user = getUser($perfilName);
} else {
    echo "<div style='color: red; font-weight: bold; text-align: center;'>Consider logging in</div>";
    echo "<div style='text-align: center;'><a href='formLogin.php' class='login-button'>Login</a></div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if article title doesn't exist
}

$articles = getArticlesByName($perfilName, isset($_SESSION['id']));

if(empty($user)) {
    echo "<div style='color: red; font-weight: bold; text-align: center;'>User does not exist</div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if no articles are found
}

// Read from the database the configuration details
$configDetails = getConfiguration();
$numColls = 2 + $configDetails['numColls'];

?>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Profile</title>

    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">

    <script type="text/javascript" src="./scripts/forms.js"></script>

</head>

<body>
<div id="containerDiv">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php") ?>
    </div>
    <div id="mainContent" style="display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>
        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">Profile: <?php echo $perfilName; ?></h1>
                <div style="margin-left: auto; margin-right: auto;">
                    <?php
                    if ($perfilName === $_SESSION['username']) {
                        echo '<button onclick="window.location.href=\'editProfile.php?profile=' . $_SESSION['username'] . '\'">Edit Profile</button>';
                    }
                    ?>
                </div>
            </div>
            <h3>Content published:</h3>
            <table id="thumbnailTable">
                <?php
                $currCol = 1;

                foreach ($articles as $article) {
                    $id = $article['idArticle'];
                    $title = $article['title'];

                    if ($currCol == 1) {
                        echo "<tr>\n";
                    }

                    $target = "<img src=\"showFileThumb.php?id=$id\" alt=\"$title\">";
                    echo "<td><a href='showArticle.php?title=$title'>$target<br>$title</a></td>\n";

                    if ($currCol == $numColls) {
                        echo "</tr>\n";
                        $currCol = 1;
                    } else {
                        ++$currCol;
                    }
                }
                ?>
            </table>
        </div>
    </div>
</div>
</body>
</html>
