<?php 
	include "sqliConnect.php";
	function getAccessPoint($BSSID){
		$mysqli = connect();
			
		$sql = "SELECT * FROM ACCESSPOINT WHERE BSSID=".$BSSID."";

		$result = $mysqli->query($sql);

		$data = mysqli_fetch_assoc($result);
			
		return $data;
		
		$mysqli->close();
	}

?>