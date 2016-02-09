<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_evento_upload_id, app_upload_path, app_evento_key FROM app_evento_uploads, app_evento WHERE (app_evento_uploads.app_evento_key = app_evento.app_id_evento)";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_evento_upload_id" => $result["app_evento_upload_id"],
            "app_upload_path" => utf8_encode($result["app_upload_path"]),
            "app_evento_key" => $result["app_evento_key"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("uploads" => $jsonArray));



