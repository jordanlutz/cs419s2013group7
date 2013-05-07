<?php
	
	if(isset($_POST["data"])){
		//echo $_POST["data"];
	
		include "sqliConnect.php";
		$mysqli = connect();
	
		$JSONArray = json_decode($_POST["data"], TRUE); //returns null if not decoded
	
		if($JSONArray != null){ 
			$BSSID = $JSONArray["BSSID"];
			$latitude = $JSONArray["latitude"];
			$longitude = $JSONArray["longitude"];
			$altitude = $JSONArray["altitude"];
			$additionalInfo = $JSONArray["additionalInfo"];
			//echo $BSSID;
		}
		else{
			echo "Error in code\n";
		}
		
		$sql = "SELECT * FROM ACCESSPOINT";
		$result = $mysqli->query($sql);
		
		$uniqueBSSID = true;
        while($r = mysqli_fetch_assoc($result)){
			if($r["BSSID"] == $BSSID){
				$uniqueBSSID = false;
				break;
			}
        }
			
		if($uniqueBSSID == true){
			echo "Unique";
				$sql = "INSERT INTO ACCESSPOINT(BSSID, latitude, longitude, altitude, additionalInfo)
				VALUES ('$BSSID', '$latitude', '$longitude', '$altitude', '$additionalInfo')";
				$mysqli->query($sql);
		}
		else{
			echo "Not Unique";
		}
		
		$mysqli->close();
		
	}
?>