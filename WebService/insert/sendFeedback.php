<?php

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

$userId = $_REQUEST['userId'];
$feedbackMessage = $_REQUEST['message'];

$stmt = $connection->prepare("INSERT INTO app_feedback_usuario (app_feedback_usuario_key, app_feedback_usuario_message) VALUES (?, ?);");

$json = json_decode(file_get_contents('php://input'));

$stmt->execute(array($userId, $feedbackMessage));

$id = $connection->lastInsertId("app_feedback_usuario_id");

$jsonData = array("sent" => $id);

echo json_encode($jsonData);


