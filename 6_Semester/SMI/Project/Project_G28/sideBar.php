<?php
if (!isset($_SESSION)) {
    session_start();
}
require_once("lib/lib.php");
require_once("lib/db.php");

if (isset($_SESSION['username'])) {
    $sideID = $_SESSION['id'];
    $sideRole = getRole($sideID);
}

?>

<!-- side_panel.php -->
<div class="side-panel">
    <img src="images/icon.png" alt="Panel Image" class="panel-image">
    <h4 class="center-text">VELESPEDIA</h4>
    <h5 class="center-text">The Content-Free Encyclopedia</h5>
    <nav>
        <ul class="nav-list">
            <li><a href="index.php">Main page</a></li>
            <li><a href="contents.php">Contents</a></li>
            <?php
            if (isset($sideID)) {
                echo '<li><a href="profile.php?profile=' . $_SESSION['username'] . '">Profile</a></li>';
            }
            if (isset($sideID) && $sideRole == "[Administrador]") {
                echo '<li><a href="admin.php">Administration</a></li>';
            }
            ?>
            <li><a href="about.php">About VelesPedia</a></li>
        </ul>
    </nav>
</div>

<!-- Add some basic styling -->
<style>
    .side-panel {
        width: 100%;
        height: 100%;
        background-color: #f4f4f4;
        border-right: 1px solid #ccc;
        text-align: center;
        padding-top: 20px;
        box-sizing: border-box;
    }

    .panel-image {
        width: 80%;
        height: auto;
        margin: 0 auto;
        display: block;
    }

    .center-text {
        text-align: center;
    }

    nav ul {
        list-style-type: none;
        padding: 0;
    }

    nav ul.nav-list {
        padding-left: 0;
        text-align: center;
    }

    nav ul.nav-list li {
        margin-bottom: 10px;
    }

    nav ul.nav-list li a {
        text-decoration: none;
        color: #333;
        display: inline-block;
    }

    nav ul.nav-list li a:hover {
        text-decoration: underline;
    }

    /* Responsive Design */
    @media (max-width: 768px) {
        .panel-image {
            width: 60%;
        }

        .side-panel {
            padding-top: 10px;
        }

        h4 {
            font-size: 0.7em;
        }

        nav ul.nav-list li {
            margin-bottom: 5px;
        }
    }

    @media (max-width: 480px) {
        .panel-image {
            width: 50%;
        }

        h4 {
            font-size: 0.2em;
        }

        nav ul.nav-list li {
            margin-bottom: 3px;
        }
    }
</style>
