<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_aluno_x_disciplina, app_aluno_fk, app_disciplina_fk, app_status_disciplina_fk, app_turma_key
        FROM app_aluno_x_disciplina, app_aluno, app_disciplina, app_status_disciplina, app_turma
        WHERE (app_aluno_fk = app_id_aluno) AND (app_disciplina_fk = app_id_disciplina	) AND (app_status_disciplina_fk = app_id_status_disciplina)
        AND (app_turma_key  = app_turma_id);";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_aluno_x_disciplina" => $result["app_id_aluno_x_disciplina"],
            "app_aluno_fk" => $result["app_aluno_fk"],
            "app_disciplina_fk" => $result["app_disciplina_fk"],
            "app_status_disciplina_fk" => $result["app_status_disciplina_fk"],
            "app_turma_key" => $result["app_turma_key"],
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("matriculas" => $jsonArray));



