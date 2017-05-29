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
	if(isset($_POST['Day']) && isset($_POST['Month']) && isset($_POST['Year']) && isset($_POST['Plant']) && isset($_POST['Amount'])){
		$day = $_POST['Day'];
		$month = $_POST['Month'];
		$year = $_POST['Year'];
		$plant = $_POST['Plant'];
		$amount = $_POST['Amount'];
		
		if( $day == '' || $month == '' || $year == '' || $plant == '' || $amount == ''){
			echo "Filling all the fields is mandatory";
		}
		else{
			$qry = "INSERT INTO CoalStorage(Day,Month,Year,Plant,Amount) VALUES('$day','$month','$year','$plant','$amount')";
			$res = mysqli_query($con,$qry);
			
			if($result){
				echo "Could not execute query";
				exit;
			}
			else{
				echo "Data Successfully saved";
			}
		}
	}
?>