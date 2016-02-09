<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_professor, app_nome_professor, app_email_professor, app_ano_ingresso_professor, app_formacao_professor, app_titulo_professor_fk
        FROM app_professor, app_titulo_professor
        WHERE (app_professor.app_titulo_professor_fk = app_titulo_professor.app_id_titulo_professor);";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_professor" => $result["app_id_professor"],
            "app_nome_professor" => utf8_encode($result["app_nome_professor"]),
            "app_email_professor" => utf8_encode($result["app_email_professor"]),
            "app_ano_ingresso_professor" => $result["app_ano_ingresso_professor"],
            "app_formacao_professor" => utf8_encode($result["app_formacao_professor"]),
            "app_titulo_professor_fk" => $result["app_titulo_professor_fk"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("professores" => $jsonArray));



