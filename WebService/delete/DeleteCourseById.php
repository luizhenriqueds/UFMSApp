<?php
  include("../connection/Conn.php");
  
  $course_id= $_REQUEST["course_id"];
  
  if(user_id != ""){
	  $sql="DELETE FROM user where course_id = " .$course_id;
	  $data=array();
	  $rs=mysql_query($sql);
	  while($row=mysql_fetch_object($rs)){
		   $data[] = $row;
	  }
		 //if(count($rs) > 0)
		 //{
		 // echo "Entry successfully deleted!";
		 
		 //}else {
		 //	echo "No entries have been deleted!";
		 // }
		 
		echo json_encode($data);
	 }else{
      echo "-1";
   }
?>