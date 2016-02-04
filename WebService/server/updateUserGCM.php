<?php

$acao = $_POST['acao'];

include("../webservice/connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

if ($acao == "updateUser") {

    $stmt = $connection->prepare("UPDATE app_dispositivo SET aluno_id = ? WHERE app_dispositivo.registration_id = ?;");
    $json = json_decode(file_get_contents('php://input'));
    $registrationId = $_POST['regId'];
    $alunoId = $_POST['alunoId'];
    $stmt->execute(array($alunoId, $registrationId));

}
?>