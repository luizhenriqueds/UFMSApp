<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_status_aluno, app_descricao_status_aluno FROM app_status_aluno";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_status_aluno" => $result["app_id_status_aluno"],
            "app_descricao_status_aluno" => utf8_encode($result["app_descricao_status_aluno"])
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("statusAlunos" => $jsonArray));



