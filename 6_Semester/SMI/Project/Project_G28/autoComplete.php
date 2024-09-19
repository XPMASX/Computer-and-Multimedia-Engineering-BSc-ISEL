<?php

require_once("lib/db.php");

if (!isset($_SESSION)) {
    session_start();
}

if (isset($_SESSION['id']))
    $login = true;
else
    $login = false;

$q = $_GET['q'];

// Connect to the database
dbConnect(ConfigFile);

// Select the database
$dataBaseName = $GLOBALS['configDataBase']->db;
mysqli_select_db($GLOBALS['ligacao'], $dataBaseName);

// Define the query with a limit of 5 results
if ($login)
    $query = "SELECT `title` FROM `article` WHERE `title` LIKE '%" . $q . "%' ORDER BY INSTR(`title`, '" . $q . "') LIMIT 5";
else
    $query = "SELECT `title` FROM `article` WHERE `title` LIKE '%" . $q . "%' AND `protected` = 0 ORDER BY INSTR(`title`, '" . $q . "') LIMIT 5";

// Execute the query
$result = mysqli_query($GLOBALS['ligacao'], $query);

// Check if the query was successful
if (!$result) {
    echo "Error: " . mysqli_error($GLOBALS['ligacao']);
    return;
}

// Fetch the results
while ($row = mysqli_fetch_assoc($result)) {
    echo "<div class='autocomplete-suggestion' data-title='" . $row['title'] . "'>" . $row['title'] . "</div>";
}

// Free the result set
mysqli_free_result($result);

// Disconnect from the database
dbDisconnect();
?>
