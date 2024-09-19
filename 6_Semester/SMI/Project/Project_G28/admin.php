<!DOCTYPE html>
<html>
<?php
require_once("lib/lib.php");
require_once("lib/db.php");

if (!isset($_SESSION)) {
    session_start();
}

$userName = $_SESSION['username'];
$id = $_SESSION['id'];
$role = getRole($id);

if ($role != "[Administrador]")
{
    echo "<div style='color: red; font-weight: bold; text-align: center;'>You do not have permission to access this page</div>";
    echo "<div style='text-align: center;'><a href='index.php'>Go Back</a></div>";
    exit(); // Stop execution if article title doesn't exist
}

// Fetch the current user first
$currentUser = getUser($userName);
// Fetch all other users
$otherUsers = getOtherUsers($userName);
// Merge the arrays so that the current user is at the beginning
$users = array_merge(array($currentUser), $otherUsers);
?>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
    <title>Administration</title>

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
                <h1 class="titleClass">Admin</h1>
            </div>
            <div id="categoryDiv" style="margin-top: 50px">
    <?php foreach ($users as $user): ?>
        <div class="userRow">
            <div>Name: <a href="profile.php?profile=<?php echo urlencode($user['name']); ?>"><?php echo $user['name']; ?></a></div>
            <div>Role: <?php
                $role = getRole($user['idUser']);
                $role = trim($role, "[]");
                echo $role;
                if ($role != "Administrador") {
                    echo '<button type="button" style="margin-left: 10px;" onclick="promoteUser(' . $user['idUser'] . ')">Promote</button>';
                }
                if ($role == "Simpatizante") {
                    echo '<button type="button" style="margin-left: 10px;" onclick="demoteUser(' . $user['idUser'] . ')">Demote</button>';
                }
                ?>
            </div>
            <div>Email: <?php echo $user['email']; ?></div>
            <div>Articles Posted: <?php echo getUserArticleCount($user['name']); ?></div>
            <div>Register Date: <?php
                $date = DateTime::createFromFormat('Y-m-d', $user['registerDate']);
                $formattedDate = $date->format('d F Y');
                echo $formattedDate;
                ?>
            </div>
            <hr>
        </div>
    <?php endforeach; ?>
</div>
        </div>
    </div>
</div>

<script>
function promoteUser(userId) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "promoteUser.php", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            alert("User promoted successfully");
            location.reload();
        }
    }
    xhr.send("userId=" + userId);
}

function demoteUser(userId) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "demoteUser.php", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            alert("User demoted successfully");
            location.reload();
        }
    }
    xhr.send("userId=" + userId);
}
</script>

</body>
</html>
