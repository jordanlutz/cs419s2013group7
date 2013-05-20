<?php 
	include "sqliConnect.php";
	
	$mysqli = connect();
		
	$sql = "SELECT * FROM ACCESSPOINT";

	$result = $mysqli->query($sql);

	$rows = array();

	while($r = mysqli_fetch_assoc($result)) {
		$rows[] = $r;
	}

	$data = json_encode($rows);
	
	print $data;
	
	$mysqli->close();

?>