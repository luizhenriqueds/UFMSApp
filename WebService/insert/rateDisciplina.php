<?php

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "INSERT INTO app_rating_disciplina (app_aluno_key, app_disciplina_key, app_rating) VALUES (?, ?, ?)";

$query = $connection->prepare($sql);

/*$json = json_decode(file_get_contents('php://input'));
$alunoKey = $json->{'alunoKey'};
$disciplinaKey = $json->{'disciplinaKey'};
$disciplinaRating = $json->{'disciplinaRating'};*/

$alunoKey = $_REQUEST['alunoKey'];
$disciplinaKey = $_REQUEST['disciplinaKey'];
$disciplinaRating = $_REQUEST['disciplinaRating'];

$query->execute(array($alunoKey, $disciplinaKey, $disciplinaRating));

$id = $connection->lastInsertId("app_id_rating_disciplina");

$returnJson = array("id" => $id);

header("Content-type: application/json");

echo json_encode($returnJson);




