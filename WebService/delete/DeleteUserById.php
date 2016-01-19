<?php
  include("../connection/Conn.php");
  
  $user_id= $_REQUEST["user_id"];
  
  if(user_id != ""){
	  $sql="DELETE FROM user where user_id = " .$user_id;
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