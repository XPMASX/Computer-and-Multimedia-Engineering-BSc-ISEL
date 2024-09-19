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

if (!isset($_SESSION['username'])) { // Check if the user is not logged in
    header('Location: formLogin.php'); // Redirect to the login page
    exit(); // Stop further execution of the script
}

$id = $_SESSION['id'];

$role = getRole($id);

$configurations = getConfiguration();
$categories = getCategories();
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
<div id="containerDiv" style="display: flex; flex-direction: column; height: 95vh; width: 95vw;">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php") ?>
    </div>

    <div id="mainContent" style="display: flex; width: 100%; height: 100%;">
        <div id="menuDiv" style="width: 15%; height: 100%; box-sizing: border-box;">
            <?php include_once("sideBar.php") ?>
        </div>
        <div id="contentDiv">
            <div id="titleRow">
                <h1 class="titleClass">Create Article</h1>
            </div>
        <div style="width: 70%; height: 100%; display: flex; justify-content: center; align-items: center;">
            <form
                    enctype="multipart/form-data"
                    action="processArticleUpload.php"
                    method="POST"
                    name="FormUpload"
                    onsubmit="return validateForm()">

                Title<br>
                <input type="text" id="title" name="title" onblur="CheckTitle()" required>
                <td>
                    <div id="titleState"></div>
                </td>
                <br>

                Content:<br>
                <textarea name="content" rows="4" cols="50" required></textarea><br>

                Category:<br>
                <select name="category" id="category" onchange="showCreateCategoryField()">
                    <?php foreach ($categories as $category): ?>
                        <option value="<?php echo $category; ?>"><?php echo $category; ?></option>
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
                <input type="checkbox" name="protectedArticle"><br>

                Allow Location:<br>
                <input type="checkbox" name="shareLocation" id="shareLocation" onchange="getLocation()"><br>

                <input type="hidden" id="latitude" name="latitude">
                <input type="hidden" id="longitude" name="longitude">

                Select file to be cover of article:<br>
                <input type="hidden" name="MAX_FILE_SIZE" value="<?php echo $configurations['maxFileSize'] ?>">
                <input type="file" name="userFile" size="64" required onchange="validateFileType(this)">

                <p></p>
                Caption<br>
                <input type="text" id="caption" name="caption"><br>

                <input type="submit" name="Submit" value="Upload file">
            </form>

            <script>
                function getLocation() {
                    if (document.getElementById('shareLocation').checked) {
                        if (navigator.geolocation) {
                            navigator.geolocation.getCurrentPosition(function (position) {
                                document.getElementById('latitude').value = position.coords.latitude;
                                document.getElementById('longitude').value = position.coords.longitude;
                                console.log(position.coords.latitude);
                            });
                        } else {
                            alert("Geolocation is not supported by this browser.");
                        }
                    } else {
                        document.getElementById('latitude').value = '';
                        document.getElementById('longitude').value = '';
                    }
                }

                function validateForm() {
                    var titleIsValid = CheckTitle();
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

                window.onload = function () {
                    document.getElementsByName('title')[0].value = '';
                    document.getElementsByName('content')[0].value = '';
                    document.getElementsByName('category')[0].selectedIndex = 0;
                    document.getElementsByName('protectedArticle')[0].checked = false;
                    document.getElementsByName('shareLocation')[0].checked = false;
                    document.getElementsByName('latitude')[0].value = '';
                    document.getElementsByName('longitude')[0].value = '';
                    document.getElementsByName('userFile')[0].value = '';
                    document.getElementsByName('caption')[0].value = '';
                }
            </script>
        </div>
    </div>
</div>
</body>
</html>