<?php

$acao = $_REQUEST['acao'];

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

if ($acao == "login") {

    $stmt = $connection->prepare("SELECT app_aluno_id, app_email_aluno, app_aluno_password
                                  FROM app_user_access, app_aluno
                                  WHERE (app_aluno_id = app_aluno.app_id_aluno) AND (app_email_aluno = ?) AND (app_aluno_password = ?);");

    $json = json_decode(file_get_contents('php://input'));
    $userEmail = $_REQUEST['email'];
    $userPassword = $_REQUEST['password'];
    $stmt->execute(array($userEmail, $userPassword));

    $jsonData = array("result" => $stmt->rowCount());

    echo json_encode($jsonData);

}