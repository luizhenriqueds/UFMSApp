<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_aluno, app_nome_aluno, app_email_aluno, app_rga_aluno, app_status_aluno_fk
        FROM app_aluno, app_status_aluno
        WHERE (app_aluno.app_status_aluno_fk = app_status_aluno.app_id_status_aluno);";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_aluno" => $result["app_id_aluno"],
            "app_nome_aluno" => utf8_encode($result["app_nome_aluno"]),
            "app_email_aluno" => utf8_encode($result["app_email_aluno"]),
            "app_rga_aluno" => $result["app_rga_aluno"],
            "app_status_aluno_fk" => $result["app_status_aluno_fk"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("alunos" => $jsonArray));



