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
			
		$sql = "INSERT INTO ACCESSPOINT (BSSID, latitude, longitude) VALUES ('".$data['BSSID']."', '".$data['latitude']."', '".$data['longitude']."')";

		$result = $mysqli->query($sql);
	
		$mysqli->close();

		return $result;
	}

?>