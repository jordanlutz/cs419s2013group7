<?php 
	include "sqliConnect.php";
	function getAccessPoints($BSSID){
		$mysqli = connect();
			
		$sql = "SELECT * FROM ACCESSPOINT WHERE BSSID=".$BSSID."";

		$result = $mysqli->query($sql);

		$rows = array();

		while($r = mysqli_fetch_assoc($result)) {
			$rows[] = $r;
		}

		$data = json_encode($rows);
		
		return $data;
		
		$mysqli->close();
	}

?>