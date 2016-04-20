<html>
<head>
    <title>GCM Server Form</title>
</head>
<body>
<form method="post" action="GCMHandler.php">
    <input type="hidden" name="acao" value="enviar">
    <input type="hidden" name="eventoId" value="1">

    <?php require('GCMHandler.php'); ?>
    
    Evento:<input type="text" id="mensagem" name="mensagem"><br>
    <input type="submit" value="OK" onclick="GCMHandler::sendGCMMessage('teste', substr('teste2', 100), 1);">

</form>
</body>
</html>