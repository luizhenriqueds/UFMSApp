<?php

$acao = $_POST['acao'];

include("../webservice/connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

//if ($acao == "registrar") {
//
//    $stmt = $connection->prepare("INSERT INTO app_dispositivo (registration_id, aluno_id) VALUES (?, ?)");
//    $json = json_decode(file_get_contents('php://input'));
//    $registrationId = $_POST['regId'];
//    $alunoId = $_POST['alunoId'];
//    $stmt->execute(array($registrationId, $alunoId));
//
//} else

if ($acao == "enviar") {

    $jsonArray = array();
    $row = array();
    $sql = $connection->prepare("SELECT app_dispositivo.registration_id FROM henriqueweb.app_dispositivo");

    $sql->execute();

    if ($sql->rowCount() > 0) {
        while ($tmp = $sql->fetch()) {
            $jsonArray[] = $tmp["registration_id"];
        }
    }

    $mensagem = $_POST["mensagem"];
    $eventoId = $_POST["eventoId"];

    $url = "https://gcm-http.googleapis.com/gcm/send";
    $apiKey = "AIzaSyD4-EStQ8w7G8FP2plyIkIOJ10LljchUpw";
    $ch = curl_init($url);
    $jsonData = array(
        "registration_ids" => $jsonArray,
        "data" => array(
            "type" => "evento",
            "eventoId" => $eventoId,
            "mensagem" => $mensagem
        )
    );
    $jsonDataEncoded = json_encode($jsonData);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
    curl_setopt($ch, CURLOPT_HTTPHEADER,
        array("Content-Type: application/json",
            "Authorization: key=" . $apiKey));
    $result = curl_exec($ch);

}
?>