<?php
  include("../connection/Conn.php");

    $course_name= $_REQUEST["course_name"];
    $course_description= $_REQUEST["course_description"];
      
    if ($course_name!="" || $course_description!=""){
           echo $sql="insert into course(course_name, course_description) 
           values('$course_name','$course_description')";
         $result=mysql_query($sql);
         echo $result;
      }else{
         echo "-1";
       }
?>