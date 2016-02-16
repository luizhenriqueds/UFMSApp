<?php

header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "UPDATE app_evento SET app_evento_notify_status = ? WHERE app_id_evento = ?";

$query = $connection->prepare($sql);

$notifyStatus = $_REQUEST['notifyStatus'];
$eventoId = $_REQUEST['eventoId'];


$query->execute(array($notifyStatus, $eventoId));

$returnJson = array("updated" => $query->rowCount());

echo json_encode($returnJson);




