<!DOCTYPE html>
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Edit Article</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link rel="stylesheet" typr="text/css" href="Styles/GlobalStyle.css">
</head>

<body>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");
require_once("lib/lib-coords.php");
require_once("lib/ImageResize.php");

include_once("config.php");
include_once("configDebug.php");

// Maximum time allowed for the upload
set_time_limit(300);

if (!isset($_FILES['userFile']) || empty($_FILES['userFile']['name'])) {
    echo "<p>No File to upload</p>";
} else {
    // The highlighted part of the code
    if ($_FILES['userFile']['error'] != 0) {
        $msg = showUploadFileError($_FILES['userFile']['error']);
        echo "\t\t<p>$msg</p>\n";
        echo "\t\t<p><a href='javascript:history.back()'>Back</a></p>\n";
        echo "\t</body>\n";
        echo "</html>\n";
        die();
    }

    // Get the MIME type of the uploaded file
    $fileType = $_FILES['userFile']['type'];

    // Define the allowed MIME types
    $allowedTypes = [
        'image/jpeg',
        'image/jpg',
        'image/png',
        'image/gif',
        'video/mp4',
        'video/mpeg'
    ];

    // Check if the uploaded file's MIME type is in the list of allowed types
    if (!in_array($fileType, $allowedTypes)) {
        echo "Error: Only images and videos are allowed.";
        return;
    }

    $srcName = $_FILES['userFile']['name'];

    // Read configurations from data base
    $configurations = getConfiguration();
    $dstDir = $configurations['destination'];

    // Destination for the uploaded file
    $src = $_FILES['userFile']['tmp_name'];
    $dst = $dstDir . DIRECTORY_SEPARATOR . /* $USER_ID . DIRECTORY_SEPARATOR . */
        $srcName;

    $copyResult = copy($src, $dst);

    if ($copyResult === false) {
        $msg = "Could not write '$src' to '$dst'";
        echo "\t\t<p>$msg</p>\n";
        echo "\t\t<p><a href='javascript:history.back()'>Back</a></p>";
        echo "\t</bobdy>\n";
        echo "\t</html>\n";
        die();
    }

    unlink($src);
    echo "<p>File uploaded with success.</p>";

    $fileInfo = finfo_open(FILEINFO_MIME);

    $fileInfoData = finfo_file($fileInfo, $dst);

    if ($debug == true) {
        echo "<pre>\n";
        print_r($fileInfoData);
        echo "</pre>\n<br>";
    }

    $fileTypeComponents = explode(";", $fileInfoData);

    $mimeTypeFileUploaded = explode("/", $fileTypeComponents[0]);
    $mimeFileName = $mimeTypeFileUploaded[0];
    $typeFileName = $mimeTypeFileUploaded[1];

    $thumbsDir = $dstDir . DIRECTORY_SEPARATOR . /* $USER_ID . DIRECTORY_SEPARATOR . */
        "thumbs";
    $pathParts = pathinfo($dst);

    echo "<p>File is of type <?php echo $mimeFileName; ?>.</p>";
}

// Continue with the rest of the code
?>

<?php

$protected = isset($_POST['protectedArticle']) ? 1 : 0;

// Get the selected category from the form
$selectedCategory = $_POST['category'];

$newCategory = ucfirst($_POST['newCategory']);

$categories = getCategories();
// Define the categories and their positions
$categories = array_flip($categories); // This will exchange the keys with their associated values

// Get the position of the selected category
$category = $categories[$selectedCategory] + 1; // Adding 1 because array indices start from 0

if (!isset($_SESSION)) {
    session_start();
}

// Check if username is set in the session
if (isset($_SESSION['username'])) {
    $name = $_SESSION['username'];
} else {
    echo "Username is not set in the session.";
    return;
}

$title = filter_input(INPUT_POST, 'title', FILTER_UNSAFE_RAW, $flags);
$title = ucfirst($title);

if ($_POST['content'] != NULL) {
    $content = addslashes($_POST['content']);
} else {
    $content = "No content available";
}

if (!empty($_POST['caption'])) {
    $caption = addslashes($_POST['caption']);
} else if (!empty($srcName)) {
    $pathParts = pathinfo($srcName);
    $caption = $pathParts['filename'];
} else {
    $caption = $title;
}

$width = $configurations['thumbWidth'];
$height = $configurations['thumbHeight'];
?>

<?php

$imageFileNameAux = $imageMimeFileName = $imageTypeFileName = null;

$thumbFileNameAux = $thumbMimeFileName = $thumbTypeFileName = null;

switch ($mimeFileName) {
    case "image":
        $exif = @exif_read_data($dst, 'IFD0', true);

        if ($exif === false) {
            ?>
            <p>No exif header data found.</p>
            <?php
        } else {
            if ($debug == true) {
                echo "<pre>";
                foreach ($exif as $key => $section) {
                    foreach ($section as $name => $val) {
                        echo "$key.$name: <br>\n";
                        print_r($val);
                        echo "<br>\n";
                    }
                }
                echo "</pre>\n<br>";
            }

            $gps = @$exif['GPS'];
            if ($gps != NULL) {
                $latitudeAux = $gps['GPSLatitude'];
                $latitudeRef = $gps['GPSLatitudeRef'];
                $longitudeAux = $gps['GPSLongitude'];
                $longitudeRef = $gps['GPSLongitudeRef'];

                if (($latitudeAux != NULL) && ($longitudeAux != NULL)) {

                    if ($debug == true) {
                        echo '$latitudeAux: ';
                        print_r($latitudeAux);
                        echo "<br>\n";
                        echo '$latitudeRef: ';
                        print_r($latitudeRef);
                        echo "<br>\n";
                        echo '$longitudeAux: ';
                        print_r($longitudeAux);
                        echo "<br>\n";
                        echo '$longitudeRef: ';
                        print_r($longitudeRef);
                        echo "<br>\n";
                    }

                    $lat = getCoordFromEXIF($latitudeAux, $latitudeRef);
                    $lon = getCoordFromEXIF($longitudeAux, $longitudeRef);
                    ?>
                    <p>File latitude: <?php echo $lat; ?></p>
                    <p>File longitude: <?php echo $lon; ?></p>
                    <?php
                } else {
                    ?>
                    <p>File include GPS information.</p>
                    <?php
                }
            } else {
                ?>
                <p>File does not have GPS information.</p>
                <?php
            }
        }

        $imageFileNameAux = $dst;
        $imageMimeFileName = "image";
        $imageTypeFileName = $typeFileName;

        $thumbFileNameAux = $thumbsDir . DIRECTORY_SEPARATOR . $pathParts['filename'] . "." . $typeFileName;
        $thumbMimeFileName = "image";
        $thumbTypeFileName = $typeFileName;

        $resizeObj = new ImageResize($dst);
        $resizeObj->resizeImage($width, $height, 'crop');
        $resizeObj->saveImage($thumbFileNameAux, $typeFileName, 100);
        $resizeObj->close();
        break;

    case "video":
        $size = "$width" . "x" . "$height";

        $imageFileNameAux = $thumbsDir . DIRECTORY_SEPARATOR . $pathParts['filename'] . "-Large.jpg";
        $imageMimeFileName = "image";
        $imageTypeFileName = "jpeg";
        echo "\t\t<p>Generating video 1st image...</p>\n";

        // -itsoffset -1 -> "moves" the film one second forward
        // -i $dst -> input file
        // -vcodec mjpeg -> codec do tipo mjpeg
        // -vframes 1 -> obter uma frame
        // -s 640x480 -> dimensão do output
        $cmdFirstImage = " $ffmpegBinary -itsoffset -1 -i $dst -vcodec mjpeg -vframes 1 -an -f rawvideo -s 640x480 $imageFileNameAux";

        echo "\t\t<p><code>$cmdFirstImage</code></p>\n";
        system($cmdFirstImage, $status);
        echo "\t\t<p>Status from the generation of video 1st image: $status.</p>\n";

        $thumbFileNameAux = $thumbsDir . DIRECTORY_SEPARATOR . $pathParts['filename'] . ".jpg";
        $thumbMimeFileName = "image";
        $thumbTypeFileName = "jpeg";
        echo "\t\t<p>Generating video thumb...</p>\n";

        $cmdVideoThumb = "$ffmpegBinary -itsoffset -1  -i $dst -vcodec mjpeg -vframes 1 -an -f rawvideo -s $size $thumbFileNameAux";
        echo "\t\t<p><code>$cmdVideoThumb</code></p>\n";
        system($cmdVideoThumb, $status);
        echo "\t\t<p>Status from the generation of video thumb: $status.</p>\n";
        break;

    default:
        $imageFileNameAux = $dstDir . DIRECTORY_SEPARATOR . "default" . DIRECTORY_SEPARATOR . "Unknown-Large.jpg";
        $imageMimeFileName = "image";
        $imageTypeFileName = "jpeg";

        $thumbFileNameAux = $dstDir . DIRECTORY_SEPARATOR . "default" . DIRECTORY_SEPARATOR . "Unknown.jpg";
        $thumbMimeFileName = "image";
        $thumbTypeFileName = "jpeg";
        break;
}

if (isset($_POST['newCategory']) && !empty($_POST['newCategory'])) {
    $newCategory = $_POST['newCategory'];

    // Connect to the database
    dbConnect(ConfigFile);
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName);

    // Prepare the query to insert the new category
    $query = "INSERT INTO `category` (`category`) VALUES ('$newCategory')";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if ($result) {
        echo "New category '$newCategory' was inserted successfully.";

        // Get the idCategory of the newly inserted category
        $category = mysqli_insert_id($GLOBALS['ligacao']);
    } else {
        echo "Failed to insert new category '$newCategory'. Error: " . dbGetLastError();
    }

    // Disconnect from the database
    dbDisconnect();
}

$fileName = addslashes($dst);
$imageFileName = addslashes($imageFileNameAux);
$thumbFileName = addslashes($thumbFileNameAux);

// Write information about file into the data base
dbConnect(ConfigFile);
$dataBaseName = $GLOBALS['configDataBase']->db;

mysqli_select_db($GLOBALS['ligacao'], $dataBaseName);

if (isset($_GET['id'])) {
    $articleID = $_GET['id'];
} else {
    // Handle the case where no id is provided in the URL
    echo "No article ID provided.";
    return;
}

// Start the transaction
mysqli_begin_transaction($GLOBALS['ligacao']);

if (!isset($_FILES['userFile']) || empty($_FILES['userFile']['name'])) {
    // No user file, update the caption only
    $query1 = "UPDATE `$dataBaseName`.`article-images` SET `caption` = '$caption' WHERE `idArticle` = '$articleID'";
} else {
    // User file is set and not empty, update the file and the caption
    $query1 = "UPDATE `$dataBaseName`.`article-images` SET " .
        "`fileName` = '$fileName', " .
        "`mimeFileName` = '$mimeFileName', " .
        "`typeFileName` = '$typeFileName', " .
        "`imageFileName` = '$imageFileName', " .
        "`imageMimeFileName` = '$imageMimeFileName', " .
        "`imageTypeFileName` = '$imageTypeFileName', " .
        "`thumbFileName` = '$thumbFileName', " .
        "`thumbMimeFileName` = '$thumbMimeFileName', " .
        "`thumbTypeFileName` = '$thumbTypeFileName', " .
        "`caption` = '$caption' " .
        "WHERE `idArticle` = '$articleID'";
}

$query2 = "UPDATE `$dataBaseName`.`article` SET " .
"`title` = '$title', " .
"`content` = '$content', " .
"`idCategory` = '$category', " .
"`protected` = '$protected' " .
"WHERE `idArticle` = '$articleID'";

$result1 = mysqli_query($GLOBALS['ligacao'], $query1);
$result2 = mysqli_query($GLOBALS['ligacao'], $query2);

if ($result1 && $result2) {
    // If both queries were successful, commit the transaction
    mysqli_commit($GLOBALS['ligacao']);
    $msg = "Information about file was inserted into data base.";
} else {
    // If any query failed, roll back the transaction
    mysqli_rollback($GLOBALS['ligacao']);
    $msg = "Information about file could not be inserted into the data base. Details : " . dbGetLastError();
}

dbDisconnect();
?>
<p><?php echo $msg; ?></p>
<?php
header("Location: showArticle.php?title=" . urlencode($title));
exit();
?>
</body>
</html>