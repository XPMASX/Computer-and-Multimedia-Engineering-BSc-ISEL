<!DOCTYPE html>
<html>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");

if (!isset($_SESSION)) {
    session_start();
}


$categories = getCategories();

if (isset($_GET['category'])) {
    $articleCategory = $_GET['category'];
    $categoryId = getCategoryId($articleCategory);
} else {
    $articleCategory = "All";
    $categoryId = 0;
}

$articles = getArticlesByCategory($categoryId, isset($_SESSION['id']));

// Read from the database the configuration details
$configDetails = getConfiguration();
$numColls = 2 + $configDetails['numColls'];

?>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Contents</title>

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
                <h1 class="titleClass">Contents</h1>
                <div style="margin-left: auto; margin-right: auto;">
                    <h3 style="color: grey; text-decoration: none; text-align: center;"><?php echo $articleCategory ?></h3>
                </div>
            </div>
            <div id="categoryDiv">
                <h4 style="margin-left: 10px;">Select Category</h4>
                <select id="categorySelect" style="margin-left: 10px;" onchange="location = this.value;">
                    <option value="contents.php?category=All" <?php if ($articleCategory === "All") echo "selected"; ?>>
                        All
                    </option>
                    <?php foreach ($categories as $category): ?>
                        <?php if ($category === "Select" || $articleCategory === $category): ?>
                            <option value="contents.php?category=<?php echo $category; ?>" selected>
                                <?php echo $category; ?>
                            </option>
                        <?php else: ?>
                            <option value="contents.php?category=<?php echo $category; ?>">
                                <?php echo $category; ?>
                            </option>
                        <?php endif; ?>
                    <?php endforeach; ?>
                </select>
            </div>
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
