<?php
  include("../connection/Conn.php");

      $name= $_REQUEST["user_name"];
      $surname= $_REQUEST["user_surname"];
      $email= $_REQUEST["user_email"];
      $course = $_REQUEST["user_course"];
       if ($name!="" || $surname!="" || $email!=""){
           echo $sql="insert into user(user_name, user_surname, user_email, user_course) 
           values('$name','$surname',$email, '$course')";
         $result=mysql_query($sql);
         echo $result;
      }else{
         echo "-1";
       }
?>