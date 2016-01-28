<?php

header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "UPDATE app_rating_disciplina SET app_rating = ? WHERE (app_aluno_key = ?) AND (app_disciplina_key = ?)";

$query = $connection->prepare($sql);

$disciplinaRating = $_POST['disciplinaRating'];
$alunoKey = $_POST['alunoKey'];
$disciplinaKey = $_POST['disciplinaKey'];

$query->execute(array($disciplinaRating, $alunoKey, $disciplinaKey));

$returnJson = array("updated" => $query->rowCount());

echo json_encode($returnJson);




