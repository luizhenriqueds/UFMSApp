<?php

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "UPDATE app_rating_disciplina SET app_rating = ? WHERE (app_aluno_key = ?) AND (app_disciplina_key = ?)";

$query = $connection->prepare($sql);

$disciplinaRating = $_REQUEST['disciplinaRating'];
$alunoKey = $_REQUEST['alunoKey'];
$disciplinaKey = $_REQUEST['disciplinaKey'];

$query->execute(array($disciplinaRating, $alunoKey, $disciplinaKey));

$returnJson = array("result" => $query->rowCount());

header("Content-type: application/json");

echo json_encode($returnJson);




