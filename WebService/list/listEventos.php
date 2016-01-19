<?php
header('Content-Type: application/json; Charset=UTF-8');

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();


$sql = "SELECT app_id_evento, app_nome_evento, app_descricao_evento, app_evento_timestamp, app_evento_data_limite, app_evento_small_icon, app_evento_big_icon, app_tipo_evento_fk, app_disciplina_fk
		FROM app_evento, app_tipo_evento, app_disciplina
		WHERE (app_evento.app_tipo_evento_fk = app_tipo_evento.app_id_tipo_evento)
		AND (app_evento.app_disciplina_fk = app_disciplina.app_id_disciplina) ORDER BY app_evento_timestamp DESC;";

$query = $connection->prepare($sql);

$isQueryOk = $query->execute();

$jsonArray = array();

if ($isQueryOk) {

    $query->setFetchMode(PDO::FETCH_ASSOC);

    while ($result = $query->fetch()) {

        $jsonRow = array(
            "app_id_evento" => $result["app_id_evento"],
            "app_nome_evento" => utf8_encode($result["app_nome_evento"]),
            "app_descricao_evento" => utf8_encode($result["app_descricao_evento"]),
            "app_evento_timestamp" => $result["app_evento_timestamp"],
            "app_evento_data_limite" => $result["app_evento_data_limite"],
            "app_evento_small_icon" => $result["app_evento_small_icon"],
            "app_evento_big_icon" => $result["app_evento_big_icon"],
            "app_tipo_evento_fk" => $result["app_tipo_evento_fk"],
            "app_disciplina_fk" => $result["app_disciplina_fk"]
        );


        $jsonArray [] = $jsonRow;
    }


} else {
    trigger_error('Error executing statement . ', E_USER_ERROR);
}


$db->disconnect();

echo json_encode(array("eventos" => $jsonArray));



