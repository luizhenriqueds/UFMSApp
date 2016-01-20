<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_rating_disciplina, app_disciplina_key, app_aluno_key, app_rating FROM app_rating_disciplina, app_disciplina, app_aluno WHERE (app_disciplina_key = app_id_disciplina) AND (app_aluno_key = app_id_aluno)";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_rating_disciplina" => $result["app_id_rating_disciplina"],
            "app_disciplina_key" => $result["app_disciplina_key"],
            "app_aluno_key" => $result["app_aluno_key"],
            "app_rating" => $result["app_rating"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("ratingDisciplinas" => $jsonArray));



