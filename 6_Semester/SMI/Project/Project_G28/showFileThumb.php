<?php
    require_once("lib/lib.php");
    require_once("lib/db.php");

    // TODO validate input data
    $id = $_GET['id'];

    // Read from the data base details about the file
    $fileDetails = getImageDetails( $id );

    $thumbFileNameAux = $fileDetails[ 'thumbFileName' ];
    $thumbMimeFileName = $fileDetails[ 'thumbMimeFileName' ];
    $thumbTypeFileName = $fileDetails[ 'thumbTypeFileName' ];

    header( "Content-type: $thumbMimeFileName/$thumbTypeFileName");
    header( "Content-Length: " . filesize($thumbFileNameAux) );

    $thumbFileHandler = fopen( $thumbFileNameAux, 'rb' );
    fpassthru( $thumbFileHandler );

    fclose( $thumbFileHandler );
?>