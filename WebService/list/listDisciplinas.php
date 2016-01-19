<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_disciplina, app_codigo_disciplina, app_titulo_disciplina, app_descricao_disciplina, app_disciplina_carga_horaria,	 app_disciplina_score, app_tipo_disciplina_fk, app_professor_fk
        FROM app_disciplina, app_disciplina_tipo, app_professor WHERE (app_tipo_disciplina_fk = app_id_disciplina_tipo) AND (app_disciplina.app_professor_fk = app_id_professor);";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_disciplina" => $result["app_id_disciplina"],
            "app_codigo_disciplina" => $result["app_codigo_disciplina"],
            "app_titulo_disciplina" => utf8_encode($result["app_titulo_disciplina"]),
            "app_descricao_disciplina" => utf8_encode($result["app_descricao_disciplina"]),
            "app_disciplina_carga_horaria" => $result["app_disciplina_carga_horaria"],
            "app_disciplina_score" => $result["app_disciplina_score"],
            "app_tipo_disciplina_fk" => $result["app_tipo_disciplina_fk"],
            "app_professor_fk" => $result["app_professor_fk"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("disciplinas" => $jsonArray));



