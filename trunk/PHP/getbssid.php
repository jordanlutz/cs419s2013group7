<?php
include "sqliConnect.php";

$mysqli = connect();


	if(isset($_GET['checkBSSID'])){
		$sql = "SELECT BSSID FROM ACCESSPOINT";
		$result = $mysqli->query($sql);
		$rows = array();
		while($r = mysqli_fetch_assoc($result)) {
            print strval($r['BSSID']) . " ";
			$rows[] = $r; 
		}
	}
	
   $mysqli->close();

?>