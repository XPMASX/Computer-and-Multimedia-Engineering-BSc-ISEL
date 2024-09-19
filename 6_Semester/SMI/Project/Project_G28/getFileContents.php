<?php
    require_once("lib/lib.php");
    require_once("lib/db.php");

    // TODO validate input data
    $id = $_GET['id'];

    // Read from the data base details about the file
    $fileDetails = getImageDetails( $id );

    $fileName = $fileDetails['fileName'];
    $mimeFileName = $fileDetails['mimeFileName'];
    $typeFileName = $fileDetails['typeFileName'];

    $pathParts = pathinfo( $fileName );
    $fileNameForDownload = $pathParts[ "basename" ];

    // Pass image contents to the browser
    $fileHandler = fopen($fileName, 'rb');

    header( "Content-Type: $mimeFileName/$typeFileName");
    header( "Content-Length: " . filesize($fileName) );

    header( "Content-Transfer-Encoding: Binary" );
    header( "Content-Disposition: attachment; filename=\"" . $fileNameForDownload . "\""); 
    
    //header( "Content-Disposition: attachment; "); 

    fpassthru( $fileHandler );
    fclose( $fileHandler );
?>