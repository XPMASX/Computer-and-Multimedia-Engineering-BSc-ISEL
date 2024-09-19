<!DOCTYPE html>
<html>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");
require_once("config.php");
require_once("configDebug.php");
require_once("regExps.php");

if (!isset($_SESSION)) {
    session_start();
}


$id = $_SESSION['id'];
$userName = $_SESSION['username'];

$role = getRole($id);

$configurations = getConfiguration();
$categories = getCategories();

$title = $_GET['title'];
$article = getArticle($title);
$image = getImageDetails($article['idArticle']);
$articleCategory = getCategoryName($article['idCategory']);

if (!isset($_SESSION['id']))
{
    echo "<div style='color: red; font-weight: bold; text-align: center;'>This Article is protected. Consider logging in</div>";
    echo "<div style='text-align: center;'><a href='formLogin.php' class='login-button'>Login</a></div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if article title doesn't exist
}

if ($article['protected'] == 1 && ($role != "[Simpatizante]" && $role != "[Administrador]") && $userName != $article['name'])
{
    echo "<div style='color: red; font-weight: bold; text-align: center;'>You do not have permission to edit this article</div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit();
}
?>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Article Upload</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="icon" href="images/icon.png">

    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">

    <script type="text/javascript">
        <?php
        echo "\t\t\tvar filterTitle = $filterTitle;\n";
        echo "\t\t\tvar filterCategory = $filterCategory;\n";
        ?>
    </script>
    <script type="text/javascript" src="scripts/forms.js"></script>
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

        <!-- Form Upload -->
        <div style="width: 70%; height: 100%; display: flex; justify-content: center; align-items: center;">
            <form
                    enctype="multipart/form-data"
                    action="processEditArticle.php?id=<?php echo $article['idArticle']; ?>"
                    method="POST"
                    name="FormUpload"
                    onsubmit="return validateForm()">

                Title<br>
                <input type="text" id="title" name="title" value="<?php echo $article['title']; ?>"
                       data-original="<?php echo $article['title']; ?>" onblur="checkTitleChange(this)" required>
                <td>
                    <div id="titleState"></div>
                </td>
                <br>

                Content:<br>
                <textarea name="content" rows="4" cols="50" required><?php echo $article['content']; ?></textarea><br>

                Category:<br>
                <select name="category" id="category" onchange="showCreateCategoryField()">
                    <?php foreach ($categories as $category): ?>
                        <option value="<?php echo $category; ?>" <?php echo $category == $articleCategory ? 'selected' : ''; ?>><?php echo $category; ?></option>
                    <?php endforeach; ?>
                    <?php if ($role == "[Administrador]"): ?>
                        <option value="addCategory">Add Category</option>
                    <?php endif; ?>
                </select><br>

                <div id="createCategory" style="display: none;">
                    Create Category:
                    <input type="text" id="newCategory" name="newCategory" onblur="CheckCategory()"><br>
                    <td>
                        <div id="categoryState"></div>
                    </td>
                </div>

                Protected Article:<br>
                <input type="checkbox"
                       name="protectedArticle" <?php echo $article['protected'] == 1 ? 'checked' : ''; ?>><br>

                Select file to be cover of article:<br>
                <input type="hidden" name="MAX_FILE_SIZE" value="<?php echo $configurations['maxFileSize'] ?>">
                <input type="file" name="userFile" size="64" onchange="validateFileType(this)">

                <p></p>
                Caption<br>
                <input type="text" id="caption" name="caption" value="<?php echo $image['caption']; ?>"><br>

                <input type="submit" name="Submit" value="Edit Article">
            </form>
        </div>

        <script>
            function checkTitleChange(input) {
                var original = input.getAttribute('data-original');
                if (input.value !== original) {
                    CheckTitle();
                }
            }

            function validateForm() {
                var titleIsValid = CheckEditTitle();
                var categoryIsValid = CheckCategory();
                var categoryValue = document.getElementById('category').value;

                if (!titleIsValid || (!categoryIsValid && categoryValue === "addCategory")) {
                    alert('Please correct the errors before submitting the form.');
                    return false;
                }

                return true;
            }

            function validateFileType(input) {
                var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif|\.mp4|\.mpeg)$/i;
                if (!allowedExtensions.exec(input.value)) {
                    alert('Invalid file type. Only images and videos are allowed.');
                    input.value = '';
                    return false;
                }
                return true;
            }

            function showCreateCategoryField() {
                var category = document.getElementById('category');
                var createCategory = document.getElementById('createCategory');
                if (category.value == 'addCategory') {
                    createCategory.style.display = 'block';
                } else {
                    createCategory.style.display = 'none';
                }
            }

        </script>
    </div>
</div>
</div>
</body>
</html>