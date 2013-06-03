<?php 
	include "sqliConnect.php";

	function getAccessPoint($BSSID) {
		$mysqli = connect();
			
		$sql = "SELECT * FROM ACCESSPOINT WHERE BSSID='$BSSID'";

		$result = $mysqli->query($sql);

		$data = mysqli_fetch_assoc($result);
			
		$mysqli->close();	
	
		return $data;
	}

	function insertAccessPoint($data) {
		$mysqli = connect();
			
		$sql = "INSERT INTO ACCESSPOINT (BSSID) VALUES ('".$data['BSSID']."')";

		$result = $mysqli->query($sql);
	
		$mysqli->close();

		return $result;
	}

?>