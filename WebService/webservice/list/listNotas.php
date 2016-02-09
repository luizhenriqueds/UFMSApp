<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_nota, app_nota, app_nota_descricao, app_aluno_x_disciplina_fk
        FROM app_nota, app_aluno_x_disciplina
        WHERE (app_aluno_x_disciplina_fk = app_id_aluno_x_disciplina);";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_nota" => $result["app_id_nota"],
            "app_nota" => utf8_encode($result["app_nota"]),
            "app_nota_descricao" => utf8_encode($result["app_nota_descricao"]),
            "app_aluno_x_disciplina_fk" => $result["app_aluno_x_disciplina_fk"],
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("notas" => $jsonArray));



