<?php

include "sqliConnect.php";
$mysqli = connect();
	 $array = $HTTP_RAW_POST_DATA;
	$JSONArray = json_decode($array, TRUE);
	
	if($JSONArray != null){
		$BSSID = $JSONArray[0]["BSSID"];
        $latitude = $JSONArray[0]["latitude"];
		$longitude = $JSONArray[0]["longitude"];
		echo $JSONArray;
    }
	
	$sql = "INSERT INTO ACCESSPOINT(BSSID, latitude, longitude) 
		VALUES ($BSSID, $latitude, $longitude)";
	$mysqli->query($sql);
	$mysqli->close();

?>