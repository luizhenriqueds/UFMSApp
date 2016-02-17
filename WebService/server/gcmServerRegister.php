<?php

$acao = $_POST['acao'];

include("../webservice/connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

if ($acao == "registrar") {

    $stmt = $connection->prepare("INSERT INTO app_dispositivo (registration_id, aluno_id) VALUES (?, ?)");
    $json = json_decode(file_get_contents('php://input'));
    $registrationId = $_POST['regId'];
    $alunoId = $_POST['alunoId'];
    $stmt->execute(array($registrationId, $alunoId));

}
?>