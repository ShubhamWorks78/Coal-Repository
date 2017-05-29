<?php
	define("hostname","localhost");
	define("username","id912463_coalstorage");
	define("password","CoalStorage");
	$con = mysqli_connect(hostname,username,password) or die("Connection not successfull");
	if(mysqli_connect_errno()){
		printf("Connect failed: %s\n",mysqli_connect_error());
		exit();
	}
	mysqli_select_db($con,username);
	
	$qry = "SELECT * FROM CoalStorage";
	
	$result = mysqli_query($con,$qry);
	
	if(!$result){
		echo "Could not Execute Query";
		exit;
	}
	
	$output = array();
	while($raw = mysqli_fetch_array($result)){
		array_push($output,$raw);
	}
	
	echo json_encode($output);
?>