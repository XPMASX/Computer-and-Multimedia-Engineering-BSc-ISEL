<?php
    require_once("lib/lib.php");
    require_once("lib/db.php");

    // TODO validate input data
    $id = $_GET['id'];

    // Read from the data base details about the file
    $fileDetails = getImageDetails( $id );

    $imageFileNameAux = $fileDetails[ 'imageFileName' ];
    $imageMimeFileName = $fileDetails[ 'imageMimeFileName' ];
    $imageTypeFileName = $fileDetails[ 'imageTypeFileName' ];

    header( "Content-type: $imageMimeFileName/$imageTypeFileName" );
    header( "Content-Length: " . filesize( $imageFileNameAux ) );

    $thumbFileHandler = fopen( $imageFileNameAux, 'rb' );
    fpassthru( $thumbFileHandler );
    fclose( $thumbFileHandler );
?>
