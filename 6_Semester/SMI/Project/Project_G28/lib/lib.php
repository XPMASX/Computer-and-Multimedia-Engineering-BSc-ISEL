<?php

require_once("db.php");

function getBrowser() {
    $userBrowser = '';
    $userAgent = $_SERVER['HTTP_USER_AGENT'];

    if (preg_match('/Trident/i', $userAgent)) {
        $userBrowser = "Internet Explorer";
    } elseif (preg_match('/MSIE/i', $userAgent)) {
        $userBrowser = "Internet Explorer";
    } elseif (preg_match('/Firefox/i', $userAgent)) {
        $userBrowser = "Mozilla Firefox";
    } elseif (preg_match('/Safari/i', $userAgent)) {
        $userBrowser = "Apple Safari";
    } elseif (preg_match('/Chrome/i', $userAgent)) {
        $userBrowser = "Google Chrome";
    } elseif (preg_match('/Flock/i', $userAgent)) {
        $userBrowser = "Flock";
    } elseif (preg_match('/Opera/i', $userAgent)) {
        $userBrowser = "Opera";
    } elseif (preg_match('/Netscape/i', $userAgent)) {
        $userBrowser = "Netscape";
    }

    if (preg_match('/Mobile/i', $userAgent)) {
        $userBrowser = "Mobile Device";
    }
    return $userBrowser;
}

function redirectToPage($url, $title, $message, $refreshTime = 5) {
    echo "<html>\n";
    echo "  <head>\n";
    echo "    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n";
    echo "    <meta http-equiv=\"REFRESH\" content=\"$refreshTime;url=$url\">\n";
    echo "    <title>$title</title>\n";
    echo "  </head>\n";
    echo "  <body>\n";
    echo "    <p>$message</p>";
    echo "    <p>You will be redirect in $refreshTime seconds.</p>";
    echo "  </body>\n";
    echo "</html>";
    die();
}

$DefaultRedirectMessage = <<<EOD
    <p>Invalid data!</p>
    <p>Please fill all the requiered fields (marked with *).</p>
EOD;

function redirectToLastPage($title, $message = NULL, $refreshTime = 5) {
    $referer = filter_input( INPUT_SERVER, 'HTTP_REFERER', FILTER_SANITIZE_STRING, FILTER_NULL_ON_FAILURE);

    echo "<html>\n";
    echo "  <head>\n";
    echo "    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n";
    echo "    <meta http-equiv=\"REFRESH\" content=\"$refreshTime;url=$referer\">\n";
    echo "    <title>$title</title>\n";
    echo "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n";
    echo ">\n";
    echo "  </head>\n";
    echo "  <body>\n";
    if ( $message != NULL ) {
        echo $message;
    }
    else {
        echo $GLOBALS['DefaultRedirectMessage'];
    }
    echo "    <p>You will be redirect to the last page in $refreshTime seconds.\n";
    echo "  </body>\n";
    echo "</html>";
    die();
}

$find;
$replace;

function convertToEntities($str) {
    global $find;
    global $replace;

    if (($find == NULL) || ($replace == NULL)) {
        $find = array();
        $replace = array();

        foreach (get_html_translation_table(HTML_ENTITIES, ENT_QUOTES) as $key => $value) {
            $find[] = $key;
            $replace[] = $value;
        }
    }

    return str_replace($find, $replace, $str);
}

function webAppName() {
    $uri = explode("/", $_SERVER['REQUEST_URI']);
    $n = count($uri);
    $webApp = "";
    for ($idx = 0; $idx < $n - 1; $idx++) {
        $webApp .= ($uri[$idx] . "/" );
    }

    return $webApp;
}

function prepareHeaders() {
    list($_SERVER['PHP_AUTH_USER'], $_SERVER['PHP_AUTH_PW']) = explode(':', base64_decode(substr($_SERVER['HTTP_AUTHORIZATION'], 6)));
}

function ensureAuth($redirectPage) {
    prepareHeaders();

    if (!isset($_SERVER['PHP_AUTH_USER']) || !isset($_SERVER['PHP_AUTH_PW'])) {
        header("Location: $redirectPage");
        exit;
    }
}

function showAuth($authType, $realm, $message) {
    header("WWW-Authenticate: $authType realm=\"$realm\"");
    header("HTTP/1.0 401 Unauthorized");

    echo $message;
}

function isValid($userName, $password, $authType) {
    $userOk = -1;

    dbConnect(ConfigFile);
    
    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = 
            "SELECT * FROM `$dataBaseName`.`perfil` " .
            "WHERE `name`='$userName' AND `password`='$password' AND `active`='1'";
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    if ( $result!=false ) {
        $userData = mysqli_fetch_array($result);
        $userOk = $userData['idUser'];
    }
    mysqli_free_result($result);

    dbDisconnect();

    return $userOk;
}

function existUserField($field, $value, $authType = "basic") {
    $exists = true;

    dbConnect(ConfigFile);
    
    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`perfil` " .
            "WHERE `$field`='$value'";
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    if ( $result==false || mysqli_num_rows($result)==0 ) {
        $exists = false;
    }

    mysqli_free_result($result);

    dbDisconnect();

    return $exists;
}
function existArticleField($field, $value) {
    $exists = true;

    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`article` " .
            "WHERE `$field`='$value'";
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    if ( $result==false || mysqli_num_rows($result)==0 ) {
        $exists = false;
    }

    mysqli_free_result($result);

    dbDisconnect();

    return $exists;
}
function existCategory($field, $value) {
    $exists = true;

    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`category` " .
            "WHERE `$field`='$value'";
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    if ( $result==false || mysqli_num_rows($result)==0 ) {
        $exists = false;
    }

    mysqli_free_result($result);

    dbDisconnect();

    return $exists;
}

function getRole($userId) {
    $userRoles = "";

    dbConnect(ConfigFile);
    
    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT `friendlyName` " .
            "FROM `$dataBaseName`.`perfil` u " .
            "JOIN `$dataBaseName`.`perfil-permissions` p ON u.`idUser`=p.`idUser` " .
            "JOIN `$dataBaseName`.`perfil-roles` r on p.`idRole`=r.`idRole` WHERE u.`active`=1 AND u.`idUser`='$userId'";

    $result = mysqli_query( $GLOBALS['ligacao'], $query );

    $isFirst = true;
    $userRoles .= "[";

    while ($userData = mysqli_fetch_array($result)) {
        if ($isFirst == true) {
            $isFirst = false;
        } else {
            $userRoles .= ", ";
        }

        $userRoles .= $userData['friendlyName'];
    }
    $userRoles .= "]";

    mysqli_free_result($result);

    dbDisconnect();

    return $userRoles;
}

function promoteUser($userId) {
    // Connect to the database
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    // Select the database
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Define a SQL query to decrease the role of the user by 1
    $query = "UPDATE `$dataBaseName`.`perfil-permissions`
              SET `idRole` = `idRole` - 1
              WHERE `idUser` = '$userId'";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return false;
    }

    // Disconnect from the database
    dbDisconnect();

    return true;
}
function demoteUser($userId) {
    // Connect to the database
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    // Select the database
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Define a SQL query to decrease the role of the user by 1
    $query = "UPDATE `$dataBaseName`.`perfil-permissions`
              SET `idRole` = `idRole` + 1
              WHERE `idUser` = '$userId'";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return false;
    }

    // Disconnect from the database
    dbDisconnect();

    return true;
}

function getEmail($idUser, $authType) {
    $userEmail = -1;

    dbConnect(ConfigFile);
    
    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT `email` FROM `$dataBaseName`.`perfil` WHERE `idUser`='$idUser'";

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    if ( $result!=false ) {
        $userData = mysqli_fetch_array($result);
        $userEmail = $userData['email'];
    }
    mysqli_free_result($result);

    dbDisconnect();

    return $userEmail;
}

function getRecentArticles($login) {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    if($login) {
        $query = "SELECT `idArticle`, `title` FROM `$dataBaseName`.`article` ORDER BY date DESC LIMIT 10";
    } else {
        $query = "SELECT `idArticle`, `title` FROM `$dataBaseName`.`article` WHERE `protected`=0 ORDER BY date DESC LIMIT 10";
    }
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    $articles = [];
    while($row = mysqli_fetch_assoc($result)) {
        $articles[] = ['idArticle' => $row['idArticle'], 'title' => $row['title']];
    }

    mysqli_free_result($result);

    dbDisconnect();

    return $articles;
}

function getArticlesByCategory($idCategory, $login) {
    // Connect to the database
    dbConnect(ConfigFile);

    // Select the database
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    if ($idCategory == 0) {
        if($login) {
            $query = "SELECT `idArticle`, `title` FROM `article` ORDER BY `idArticle` DESC";
        } else {
            $query = "SELECT `idArticle`, `title` FROM `article` WHERE `protected`=0 ORDER BY `idArticle` DESC";
        }
    } else {
        if($login) {
            $query = "SELECT `idArticle`, `title` FROM `article` WHERE `idCategory` = $idCategory ORDER BY `idArticle` DESC";
        } else {
            $query = "SELECT `idArticle`, `title` FROM `article` WHERE `idCategory` = $idCategory AND `protected`=0 ORDER BY `idArticle` DESC";
        }
    }

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    // Fetch all the articles
    $articles = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $articles[] = ['idArticle' => $row['idArticle'], 'title' => $row['title']];
    }

    // Free the result set
    mysqli_free_result($result);

    // Disconnect from the database
    dbDisconnect();

    // Return the articles
    return $articles;
}

function getArticlesByName($name, $login) {
    // Connect to the database
    dbConnect(ConfigFile);

    // Select the database
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Prepare the query
    if($login) {
        $query = "SELECT `idArticle`, `title` FROM `article` WHERE `name` = ? ORDER BY `idArticle` DESC";
    } else {
        $query = "SELECT `idArticle`, `title` FROM `article` WHERE `name` = ? AND `protected`=0 ORDER BY `idArticle` DESC";
    }

    // Prepare the statement
    $stmt = mysqli_prepare($GLOBALS['ligacao'], $query);

    // Bind the parameters
    mysqli_stmt_bind_param($stmt, "s", $name);

    // Execute the statement
    mysqli_stmt_execute($stmt);

    // Get the result
    $result = mysqli_stmt_get_result($stmt);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    // Fetch all the articles
    $articles = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $articles[] = ['idArticle' => $row['idArticle'], 'title' => $row['title']];
    }

    // Free the result set
    mysqli_free_result($result);

    // Close the statement
    mysqli_stmt_close($stmt);

    // Disconnect from the database
    dbDisconnect();

    // Return the articles
    return $articles;
}

function getAllArticleTitles() {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT `title` FROM `$dataBaseName`.`article` ORDER BY date DESC ";
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    $articles = [];
    while($row = mysqli_fetch_assoc($result)) {
        $articles[] = $row['title']; // Push the title directly into the array
    }

    mysqli_free_result($result);

    dbDisconnect();

    return $articles;
}


function getCategories() {
    // Connect to the database
    dbConnect(ConfigFile);

    // Select the database
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Define the query
    $query = "SELECT `category` FROM `category` ORDER BY `idCategory` ASC";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    // Fetch all the categories
    $categories = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $categories[] = $row['category'];
    }

    // Free the result set
    mysqli_free_result($result);

    // Disconnect from the database
    dbDisconnect();

    // Return the categories
    return $categories;
}

function getCategoryId($categoryName) {
    // Connect to the database
    dbConnect(ConfigFile);

    // Select the database
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Define the query
    $query = "SELECT `idCategory` FROM `category` WHERE `category` = '$categoryName'";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    // Fetch the idCategory
    $idCategory = mysqli_fetch_assoc($result)['idCategory'];

    // Free the result set
    mysqli_free_result($result);

    // Disconnect from the database
    dbDisconnect();

    // Return the idCategory
    return $idCategory;
}

function getCategoryName($id) {
    // Connect to the database
    dbConnect(ConfigFile);

    // Select the database
    $dataBaseName = $GLOBALS['configDataBase']->db;
    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    // Define the query
    $query = "SELECT `category` FROM `category` WHERE `idCategory` = $id";

    // Execute the query
    $result = mysqli_query($GLOBALS['ligacao'], $query);

    // Check if the query was successful
    if (!$result) {
        echo "Error: " . mysqli_error($GLOBALS['ligacao']);
        return;
    }

    // Fetch the category
    $category = mysqli_fetch_assoc($result)['category'];

    // Free the result set
    mysqli_free_result($result);

    // Disconnect from the database
    dbDisconnect();

    // Return the category
    return $category;
}

function logout($authType, $realm, $location) {
    unset($_SERVER['PHP_AUTH_USER']);
    unset($_SERVER['PHP_AUTH_PW']);
    unset($_SERVER['HTTP_AUTHORIZATION']);

    header("WWW-Authenticate: $authType realm=\"$realm\"");
    header("HTTP/1.0 401 Unauthorized");

    header("Location: $location");
}

function getArticle($titles) {
    $isFirst = true;
    $whereClause = "";

    if (is_array($titles)) {
        foreach ($titles as $title) {
            if ($isFirst == false) {
                $whereClause .= " OR `title`='$title'";
            } else {
                $whereClause .= "`title`='$title'";
                $isFirst = false;
            }
        }
    } else {
        $whereClause = "`title`='$titles'";
    }

    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`article` WHERE " . $whereClause;

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $fileData = array();
    while (($fileDataRecord = mysqli_fetch_array($result)) != false) {
        $fileData[] = $fileDataRecord;
    }

    mysqli_free_result($result);
    dbDisconnect();

    if ( !is_array($titles)) {
        return $fileData[0];
    } else {
        return $fileData;
    }
}

function getUser($userName) {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT `idUser`, `name`, `email`, `registerDate` FROM `$dataBaseName`.`perfil` WHERE `name` = '$userName'";

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $userRecord = mysqli_fetch_array($result);

    mysqli_free_result($result);
    dbDisconnect();

    return $userRecord;
}

function getOtherUsers($userName) {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT p.`idUser`, p.`name`, p.`email`, p.`registerDate`, pp.`idRole`
              FROM `$dataBaseName`.`perfil` p
              JOIN `$dataBaseName`.`perfil-permissions` pp ON p.`idUser` = pp.`idUser`
              WHERE p.`name` != '$userName'
              ORDER BY pp.`idRole` ASC, p.`registerDate` ASC";

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $userData = array();
    while (($userRecord = mysqli_fetch_array($result)) != false) {
        $userData[] = $userRecord;
    }

    mysqli_free_result($result);
    dbDisconnect();

    return $userData;
}

function getUserArticleCount($userName) {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT COUNT(*) as articleCount FROM `$dataBaseName`.`article` WHERE `name` = '$userName'";

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $articleCount = 0;
    if ($row = mysqli_fetch_assoc($result)) {
        $articleCount = $row['articleCount'];
    }

    mysqli_free_result($result);
    dbDisconnect();

    return $articleCount;
}

function parseContent($content) {
    // Replace '''text''' with <strong>text</strong>
    $content = preg_replace("/'''(.*?)'''/", '<strong>$1</strong>', $content);

    // Replace ''text'' with <em>text</em>
    $content = preg_replace("/''(.*?)''/", '<em>$1</em>', $content);

    // Replace [[link]] and [[link|text]] with HTML anchor tags
    $content = preg_replace_callback("/\[\[([^\|\]]+)(?:\|([^\]]+))?\]\]/", function($matches) {
        $title = $matches[1];
        $text = isset($matches[2]) ? $matches[2] : $title;
        return '<a href="showArticle.php?title=' . urlencode($title) . '">' . htmlspecialchars($text) . '</a>';
    }, $content);

    return $content;
}

function getImageDetails($ids) {
    $isFirst = true;
    $whereClause = "";

    if (is_array($ids)) {
        foreach ($ids as $id) {
            if ($isFirst == false) {
                $whereClause .= " OR `idArticle`='$id'";
            } else {
                $whereClause .= "`idArticle`='$id'";
                $isFirst = false;
            }
        }
    } else {
        $whereClause = "`idArticle`='$ids'";
    }

    dbConnect(ConfigFile);
    
    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`article-images` WHERE " . $whereClause;

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $fileData = array();
    while (($fileDataRecord = mysqli_fetch_array($result)) != false) {
        $fileData[] = $fileDataRecord;
    }

    mysqli_free_result($result);
    dbDisconnect();

    if ( !is_array($ids)) {
        return $fileData[0];
    } else {
        return $fileData;
    }
}

function getConfiguration() {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    $query = "SELECT * FROM `$dataBaseName`.`images-config`";

    $result = mysqli_query($GLOBALS['ligacao'], $query);

    $configuration = mysqli_fetch_array($result);

    mysqli_free_result($result);

    dbDisconnect();

    return $configuration;
}

function getStats() {
    dbConnect(ConfigFile);

    $dataBaseName = $GLOBALS['configDataBase']->db;

    mysqli_select_db($GLOBALS['ligacao'], $dataBaseName );

    "SELECT COUNT(DISTINCT `mimeFileName`) FROM `$dataBaseName`.`images-details`;";
    "SELECT DISTINCT `mimeFileName` FROM `$dataBaseName`.`images-details`;";

    $queryTotal = "SELECT count(*) AS totalFiles FROM `$dataBaseName`.`images-details`";
    $queryImages = "SELECT count(*) AS totalImages FROM `$dataBaseName`.`images-details` WHERE `mimeFileName`='image'";
    $queryVideos = "SELECT count(*) AS totalVideos FROM `$dataBaseName`.`images-details` WHERE `mimeFileName`='video'";
    $queryAudios = "SELECT count(*) AS totalAudios FROM `$dataBaseName`.`images-details` WHERE `mimeFileName`='audio'";

    // Total files
    $resultTotal = mysqli_query($GLOBALS['ligacao'], $queryTotal);
    $totalData = mysqli_fetch_array($resultTotal);
    $stats['numFiles'] = $totalData['totalFiles'];
    mysqli_free_result($resultTotal);
  
    if ( $stats['numFiles']==0 ) {
        $stats['numImages'] = 0;
        $stats['numVideos'] = 0;
        $stats['numAudios'] = 0;

        dbDisconnect();

        return $stats;
    }

    // Image files
    $resultImages = mysqli_query($GLOBALS['ligacao'], $queryImages);
    $totalImages = mysqli_fetch_array($resultImages);
    $stats['numImages'] = $totalImages['totalImages'];
    mysqli_free_result($resultImages);

    // Video files
    $resultVideos = mysqli_query($GLOBALS['ligacao'], $queryVideos);
    $totalVideos = mysqli_fetch_array($resultVideos);
    $stats['numVideos'] = $totalVideos['totalVideos'];
    mysqli_free_result($resultVideos);

    // Audio files
    $resultAudios = mysqli_query($GLOBALS['ligacao'], $queryAudios);
    $totaltAudios = mysqli_fetch_array($resultAudios);
    $stats['numAudios'] = $totaltAudios['totalAudios'];
    mysqli_free_result($resultAudios);

    dbDisconnect();

    return $stats;
}

function showUploadFileError($errorCode) {
    switch ($errorCode) {
        case UPLOAD_ERR_OK:
            $errorMessage = "($errorCode) There is no error, the file uploaded with success.";
            break;

        case UPLOAD_ERR_INI_SIZE:
            $errorMessage = "($errorCode) The uploaded file exceeds the upload_max_filesize directive in php.ini file.";
            break;

        case UPLOAD_ERR_FORM_SIZE:
            $errorMessage = "($errorCode) The uploaded file exceeds the MAX_FILE_SIZE directive that was specified in the HTML form.";
            break;

        case UPLOAD_ERR_PARTIAL:
            $errorMessage = "($errorCode) The uploaded file was only partially uploaded.";
            break;

        case UPLOAD_ERR_NO_FILE:
            $errorMessage = "($errorCode) No file was uploaded.";
            break;

        case UPLOAD_ERR_NO_TMP_DIR:
            $errorMessage = "($errorCode) Missing a temporary folder. Introduced in PHP 4.3.10 and PHP 5.0.3.";
            break;

        case UPLOAD_ERR_CANT_WRITE:
            $errorMessage = "($errorCode) Failed to write file to disk. Introduced in PHP 5.1.0.";
            break;

        case UPLOAD_ERR_EXTENSION:
            $errorMessage = "($errorCode) A PHP extension stopped the file upload.";
            break;

        default:
            $errorMessage = "($errorCode) No description available.";
            break;
    }

    return $errorMessage;
}

function getXdebugArg() {
  $method = $_SERVER['REQUEST_METHOD'];
  
  if ($method == 'POST') {
    $args = $_POST;
  } elseif ($method == 'GET') {
    $args = $_GET;
  }

 foreach ($args as $key => $value) {
    if ( $key==="XDEBUG_SESSION_START" ) {
      return "XDEBUG_SESSION_START=$value";
    }
  }
  
  return null;
}

function getXdebugArgAsArray() {
  $method = $_SERVER['REQUEST_METHOD'];
  
  if ($method == 'POST') {
    $args = $_POST;
  } elseif ($method == 'GET') {
    $args = $_GET;
  }

 foreach ($args as $key => $value) {
    if ( $key==="XDEBUG_SESSION_START" ) {
      return array( "key" => $key, "value" => $value);
    }
  }
  
  return null;
}

?>