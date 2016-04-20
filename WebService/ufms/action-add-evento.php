<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $name = $_FILES['doc-anexo']['name'];
    $tmpName = $_FILES['doc-anexo']['tmp_name'];
    $error = $_FILES['doc-anexo']['error'];
    $size = $_FILES['doc-anexo']['size'];
    $ext = strtolower(pathinfo($name, PATHINFO_EXTENSION));

    if ($error == UPLOAD_ERR_OK) {
        $valid = true;
        //validate file extensions
        if (!in_array($ext, array('pdf'))) {
            $valid = false;
            $response = 'Invalid file extension.';
        }
        //validate file size
        if ($size / 1024 / 1024 > 10) {
            $valid = false;
            $response = 'File size is exceeding maximum allowed size.';
        }
        //upload file
        if ($valid) {

            $nomeEvento = $_POST['evento-name'];
            $descricaoEvento = $_POST['descricao-evento'];
            $eventoTimeStamp = date("Y-m-d", time());
            $dataLimiteEvento = $_POST['data-limite'];
            $selectTipoEvento = $_POST['tipo-evento'];
            $selectDisciplina = $_POST['disciplina'];
            $dataLimiteFormatted = date("Y-m-d", strtotime($dataLimiteEvento));

            $anexoNameFormatted = str_replace(' ', '_', $name);

            $targetPath = '../uploads' . DIRECTORY_SEPARATOR . $anexoNameFormatted;

            $dbPath = '/uploads' . DIRECTORY_SEPARATOR . $anexoNameFormatted;
            session_start();

            //$dbPathFormatted = str_replace(' ', '_', $dbPath);


            if (isset($nomeEvento) && isset($descricaoEvento) && isset($dataLimiteFormatted) && isset($selectTipoEvento) && isset($selectDisciplina)) {
                require("UfmsDAO.php");
                require("../server/GCMHandler.php");

                $inserted = UfmsDAO::adicionarEvento(utf8_decode($nomeEvento), utf8_decode($descricaoEvento), $eventoTimeStamp, $dataLimiteFormatted, utf8_decode($selectTipoEvento), utf8_decode($selectDisciplina));

                if ($inserted > 0) {
                    move_uploaded_file($tmpName, $targetPath);
                    UfmsDAO::adicionarAnexo($dbPath, $inserted);
                    GCMHandler::sendGCMMessage($nomeEvento, $descricaoEvento, $inserted, $selectDisciplina);

                    echo "<script>
                     location.href='index.php';

                     </script>";

                    $_SESSION['evento-added'] = 1;

                }


            }

        }

    }


}
?>