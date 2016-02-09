<?php

class Connect
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

    function connect()
    {
        $databaseConnection = new PDO(self::$DB_DRIVER . ":dbname=" . self::$DB_DATABASE . ";host=" . self::$DB_SERVER, self::$DB_USER, self::$DB_PASSWORD);
        $databaseConnection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        return $databaseConnection;
    }

    function disconnect()
    {
        self::$databaseConnection = null;
    }
}

?>