<!DOCTYPE html>
<html>
<?php
ini_set('display_errors', 'On');
error_reporting(E_ALL);

if (!isset($_SESSION)) {
    session_start();
}

require_once("lib/lib.php");
require_once("lib/db.php");
require_once("lib/lib-coords.php");

include_once("config.php");
include_once("configKeys.php");

$userRole = "convidado";
$userName = "convidado";

if (isset($_SESSION['id'])) {
    $userRole = getRole($_SESSION['id']);
    $userName = $_SESSION['username'];
}

$name = webAppName();

$articletitles = getAllArticleTitles();
$articleTitle = $_GET['title'];

// Check if the article title exists in the array $articletitles
if (!in_array($articleTitle, $articletitles)) {
    echo "<div style='color: red; font-weight: bold; text-align: center;'>Please consider adding this article.</div>";
    echo "<div style='text-align: center;'><a href='javascript:history.back()'>Go Back</a></div>";
    exit(); // Stop execution if article title doesn't exist
}

$article = getArticle($articleTitle);
$id = $article['idArticle'];
$imageDetails = getImageDetails($id);

$imageDetailsFileName = $imageDetails['fileName'];
$imageDetailsMime = $imageDetails['mimeFileName'];
$imageDetailsType = $imageDetails['typeFileName'];
$imageDetailsCaption = $imageDetails['caption'];


$articleProtected = $article['protected'];

if (!isset($_SESSION['id']) && $articleProtected == 1)
{
    echo "<div style='color: red; font-weight: bold; text-align: center;'>This Article is protected. Consider logging in</div>";
    echo "<div style='text-align: center;'><a href='formLogin.php' class='login-button'>Login</a></div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if article title doesn't exist
}

$articleLatitude = $article['latitude'];
$articleLongitude = $article['longitude'];

$articleName = $article['name'];
$articleCategory = getCategoryName($article['idCategory']);
$articleContent = htmlentities($article['content'], ENT_NOQUOTES, "UTF-8");

$articleDate = $article['date'];
$date = DateTime::createFromFormat('Y-m-d', $articleDate);
$formattedDate = $date->format('d-m-Y');

$dateNow = date('F d Y');

$pathParts = pathinfo($imageDetailsFileName);
$fileName = $pathParts['filename'];

/*$locationGoogle = getCoordInGoogleFormat($articleLatitude, $articleLongitude);
$latGoogle = $locationGoogle['latitude'];
$lonGoogle = $locationGoogle['longitude'];*/

$latGoogle = $articleLatitude;
$lonGoogle = $articleLongitude;

// Check if latitude or longitude is empty
if (empty($latGoogle) || empty($lonGoogle)) {
    $mapExists = false;
} else {
    $mapExists = true;
}

$imageHeight = 85;
$imageWidth = 90;

$mapHeight = 60;
$mapWidth = 90;

$defaultZoom = 10;
?>

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><?php echo $articleTitle; ?></title>
    <link rel="icon" href="images/icon.png">
    <link rel="stylesheet" type="text/css" href="Styles/GlobalStyle.css">

    <!-- Open Street Maps API - Begin -->
    <!-- Versão 1.9.4 - https://leafletjs-cdn.s3.amazonaws.com/content/leaflet/v1.9.4/leaflet.zip -->
    <link rel="stylesheet" href="./static/external/leaflet/leaflet.css"/>
    <script src="./static/external/leaflet/leaflet.js"></script>
    <!-- Open Street Maps API - End -->

    <style>
        #image_canvas {
            width: <?php echo $imageWidth; ?>%;
            height: <?php echo $imageHeight; ?>%;
            margin-left: auto; /* Align to the right */
        }

        #map_canvas {
            width: <?php echo $mapWidth; ?>%;
            height: <?php echo $mapHeight; ?>%;
            display: none;
            margin-left: auto; /* Align to the right */
        }
    </style>

    <script type="text/javascript" src="<?php echo $jwplayerScript; ?>"></script>
    <script type="text/javascript">
        jwplayer.key = "<?php echo $jwplayerKey; ?>";
    </script>
    <script type="text/javascript">
        let map;

        function initializeMaps(container) {
            <?php if ($mapExists): ?>
            let latCenter = <?php echo $latGoogle; ?>;
            let lngCenter = <?php echo $lonGoogle; ?>;

            const mapOptions = {
                center: [latCenter, lngCenter],
                zoom: <?php echo $defaultZoom; ?>
            };

            const markerOptionsCenter = {
                title: "<?php echo $article['title'] ?>",
                draggable: false
            };

            // Creating a map object
            map = new L.map(document.getElementById(container), mapOptions);

            // Creating a Layer object
            let layer = new L.TileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png');

            // Adding layer to the map
            map.addLayer(layer);

            let pinCenter = new L.Marker([latCenter, lngCenter], markerOptionsCenter);
            pinCenter.addTo(map);
            <?php endif; ?>
        }

        function initializeMap() {
            // Check if mapExists is true
            <?php if ($mapExists): ?>
            initializeMaps("map_canvas");
            var mapCanvas = document.getElementById('map_canvas');
            mapCanvas.style.display = 'none';
            <?php endif; ?>
        }

        function initializePlayer(container) {
            <?php if ($imageDetailsType == "mpeg") {
            $fileType = "mp3";
        } else {
            $fileType = $imageDetailsType;
        } ?>
            jwplayer(container).setup({
                primary: "flash",
                autostart: "false",
                author: "Carlos Gonçalves",
                date: "<?php echo $dateNow ?>",
                height: "<?php echo $imageHeight ?>%",
                width: "100%",
                playlist: [{
                    title: "<?php echo $imageDetailsCaption ?>",
                    image: "<?php echo $name; ?>showMovieImage.php?id=<?php echo $id ?>",
                    sources: [{
                        file: "<?php echo $name; ?>getFileContents.php?id=<?php echo $id ?>",
                        type: "<?php echo $fileType; ?>"
                    }]
                }]
            });
        }

        function toggleView() {
            var mapCanvas = document.getElementById('map_canvas');
            var imageCanvas = document.getElementById('image_canvas');
            var creatorText = document.getElementById('creatorText'); // add this line

            if (mapCanvas.style.display === 'none') {
                mapCanvas.style.display = 'block';
                creatorText.style.display = 'block';
                imageCanvas.style.display = 'none';

                setTimeout(function () {
                    map.invalidateSize();
                }, 100);
            } else {
                mapCanvas.style.display = 'none';
                imageCanvas.style.display = 'block';
                creatorText.style.display = 'none'; // hide creator when map is visible
            }
        }

    </script>
</head>

<body onload="initializeMap()">
<div id="containerDiv">
    <div id="headerDiv" style="width: 100%;">
        <?php include_once("header.php") ?>
    </div>
    <div id="mainContent" style="width: 100%;">
        <div id="sideBarContainer">
            <?php include_once("sideBar.php") ?>
        </div>
        <div id="contentContainer">
            <div id="titleRow">
                <h1 class="titleClass"><?php echo $articleTitle ?></h1>
                <a href="contents.php?category=<?php echo urlencode($articleCategory); ?>" style="color: grey; text-decoration: none;"><h3><?php echo $articleCategory ?></h3></a>
                <div style="display: flex; padding: 10px">
                    <?php if (($articleProtected == 1 && ($userRole != "[Simpatizante]" && $userRole != "[Administrador]") && $userName != $articleName) || !isset($_SESSION['username'])): ?>
                        <span class="lockIcon">🔒</span>
                    <?php else: ?>
                        <span class="lockIcon">🔓</span>
                        <a href="editArticle.php?title=<?php echo urlencode($articleTitle); ?>" style="margin-left: 5px;">
                            <button type="button" style="padding: 5px; ">Edit</button>
                        </a>
                    <?php endif; ?>
                </div>
            </div>

            <div id="content">
                <div style="width: 70%; margin-top: 45px">
                    <?php echo parseContent($articleContent); ?>
                    <br>
                </div>

                <div id="imageContainer">
                    <?php if ($mapExists): ?>
                        <button id="toggleButton" onclick="toggleView()" style="margin-left: auto;">Toggle Map/Image
                        </button>
                    <?php endif; ?>
                    <div id="image_canvas">
                        <?php
                        if ($imageDetails['mimeFileName'] == "image") {
                            echo "<img width=\"100%\" height=\"" . $imageHeight . "%\" src=\"" . $name . "getFileContents.php?id=" . $article['idArticle'] . "\">";
                            echo "<p align=\"center\">$imageDetailsCaption</p>";
                        } elseif ($imageDetails['mimeFileName'] == "video" || $imageDetails['mimeFileName'] == "audio") {
                            echo "<div id=\"playerCanvas\">JW Player is loading...</div>\n";
                            echo "<script type=\"text/javascript\">initializePlayer('playerCanvas');</script>";
                            echo "<p align=\"center\">$imageDetailsCaption</p>";
                        }
                        ?>
                    </div>
                    <div id="map_canvas">

                    </div>
                    <p id="creatorText" style="display: none; margin-left: auto;">Created <?php echo $formattedDate; ?>
                        by <a href="profile.php?profile=<?php echo urlencode($articleName); ?>"><?php echo $articleName; ?></a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
