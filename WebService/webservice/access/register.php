<?php

$acao = $_REQUEST['acao'];

include("../connection/Connect.php");

$db = new Connect();

$connection = $db->connect();

if ($acao == "register") {

    $nomeAluno = $_REQUEST['nome'];
    $emailAluno = $_REQUEST['email'];
    $rgaAluno = $_REQUEST['rga'];
    $statusAluno = $_REQUEST['statusAlunoFk'];

    $stmtEmail = $connection->prepare("SELECT app_email_aluno
                                  FROM app_aluno
                                  WHERE (app_email_aluno = ?)");

    $stmtEmail->execute(array($emailAluno));

    $stmtRga = $connection->prepare("SELECT app_rga_aluno
                                  FROM app_aluno
                                  WHERE (app_rga_aluno = ?)");

    $stmtRga->execute(array($rgaAluno));

    if ($stmtEmail->rowCount() > 0 or $stmtRga->rowCount() > 0) {
        //Já existe
        $jsonData = array("registered" => -1);

        echo json_encode($jsonData);
    } else {
        $stmt = $connection->prepare("INSERT INTO app_aluno (app_nome_aluno, app_email_aluno, app_rga_aluno, app_status_aluno_fk) VALUES (?, ?, ?, ?);");

        $json = json_decode(file_get_contents('php://input'));

        $stmt->execute(array($nomeAluno, $emailAluno, $rgaAluno, $statusAluno));

        $id = $connection->lastInsertId("app_id_aluno");

        $stmt = $connection->prepare("INSERT INTO app_user_access (app_aluno_id, app_aluno_password) VALUES (?, ?);");

        $password = $_REQUEST['password'];

        $stmt->execute(array($id, $password));

        $lastInsertedId = $connection->lastInsertId("app_access_id");

        $jsonData = array("registered" => $lastInsertedId, "userId" => $id);

        echo json_encode($jsonData);
    }


}