<?php

/**
 * Created by PhpStorm.
 * User: Luiz Henrique
 * Date: 3/6/2016
 * Time: 6:58 PM
 */

if (isset($_GET['logout']) && ($_GET['logout'] == true)) {
    UfmsDAO::userLogout();
}

/*if (isset($_POST['signIn'])) {
    if (UfmsDAO::userAccountSignIn($_POST['email'], $_POST['password'])) {
        echo 'User Logado com sucesso!';
    } else {
        echo 'Alguma coisa deu errado :(';
    }
}*/


class UfmsDAO
{

    private static $DB_SERVER = "mysql01.henriqueweb.hospedagemdesites.ws";
    private static $DB_USER = "henriqueweb";
    private static $DB_PASSWORD = "LuizLuiz10";
    private static $DB_DATABASE = "henriqueweb";
    private static $DB_DRIVER = "mysql";

    private static $databaseConnection = null;

    /*
    * Connects to the database.
    * Returns an instance that represents the connection with the database.
    * */

    public static function connect()
    {
        $databaseConnection = new PDO(self::$DB_DRIVER . ":dbname=" . self::$DB_DATABASE . ";host=" . self::$DB_SERVER, self::$DB_USER, self::$DB_PASSWORD);
        $databaseConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        return $databaseConnection;
    }

    public function disconnect()
    {
        self::$databaseConnection = null;
    }


    public static function userLogout()
    {
        session_start();

        if (isset($_SESSION['admin'])) {
            session_unset();
            session_destroy();
            header("location:login.php");
            exit;
        }

    }

    /* public static function userAccountSignIn($email, $password)
     {
         require("../user/userUtils/StringUtils.php");

         $passwordHash = StringUtils::createPasswordHash($password);

         try {

             $db = AdminDAO::connect();

             if (StringUtils::validate($password, $passwordHash)) {
                 $sql = 'SELECT admin_id, admin_email, admin_password FROM admin WHERE admin_email = ? AND admin_password = ?';
             }

             $stmt = $db->prepare($sql);
             $stmt->execute(array($email, $passwordHash));

             if ($stmt->rowCount() > 0) {
                 session_start();
                 while ($result = $stmt->fetch()) {
                     $_SESSION['admin'] = $result['admin_email'];
                     $_SESSION['adminId'] = $result['admin_id'];
                 }
                 header('Location:index.php');
                 self::disconnect();
                 return true;
             } else {
                 return false;
             }
         } catch (PDOException $e) {
             self::disconnect();
             echo $e->getMessage();
             return false;
         }

     }*/

    public static function getEventosOverview()
    {
        $databaseConnection = self::connect();

        $sql = 'SELECT app_nome_evento, app_descricao_evento, app_evento_timestamp, app_evento_data_limite, app_descricao_tipo_evento, app_titulo_disciplina FROM app_evento, app_disciplina, app_tipo_evento WHERE (app_tipo_evento_fk = app_tipo_evento.app_id_tipo_evento) AND (app_disciplina_fk = app_disciplina.app_id_disciplina) ORDER BY app_evento_timestamp DESC LIMIT 3';

        $query = $databaseConnection->prepare($sql);


        $isQueryOk = $query->execute();

        $results = array();

        if ($isQueryOk) {
            $query->setFetchMode(PDO::FETCH_ASSOC);

            $results = $query->fetchAll();

        } else {
            trigger_error('Error executing statement . ', E_USER_ERROR);
        }

        self::disconnect();

        return $results;
    }


    public static function getAllEventos()
    {
        $databaseConnection = self::connect();

        $sql = 'SELECT app_nome_evento, app_descricao_evento, app_evento_timestamp, app_evento_data_limite, app_descricao_tipo_evento, app_titulo_disciplina FROM app_evento, app_disciplina, app_tipo_evento WHERE (app_tipo_evento_fk = app_tipo_evento.app_id_tipo_evento) AND (app_disciplina_fk = app_disciplina.app_id_disciplina) ORDER BY app_evento_timestamp DESC';

        $query = $databaseConnection->prepare($sql);


        $isQueryOk = $query->execute();

        $results = array();

        if ($isQueryOk) {
            $query->setFetchMode(PDO::FETCH_ASSOC);

            $results = $query->fetchAll();

        } else {
            trigger_error('Error executing statement . ', E_USER_ERROR);
        }

        self::disconnect();

        return $results;
    }


}