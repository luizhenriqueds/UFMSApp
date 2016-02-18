<?php

$acao = $_POST['acao'];

include("../webservice/connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

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
    $disciplinaId = $_POST["disciplinaId"];

    $url = "https://gcm-http.googleapis.com/gcm/send";
    $apiKey = "AIzaSyD4-EStQ8w7G8FP2plyIkIOJ10LljchUpw";
    $ch = curl_init($url);
    $jsonData = array(
        "registration_ids" => $jsonArray,
        "data" => array(
            "type" => "disciplina",
            "disciplinaId" => $disciplinaId,
            "mensagem" => utf8_encode($mensagem)
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