<?php
	echo "test";

	include "getAccessPoint.php";
	
	$raw_array = $HTTP_RAW_POST_DATA;
	$json_array = json_decode($raw_array, true);

	if ($json_array != null) {
		
		if ($json_array["addFlag"] == "1") {
			echo "test2";//TODO
		} 
		else {
			$ap_array = array();

			$j = 0;
			for ($i = 0; $i <= 10; $i++) {	
				echo "test"+$i;
				$BSSID = $json_array["BSSID"];
				$result = getAccessPoint($BSSID);
				if ($result != null) {
					$ap_array[$j] = array();
					$ap_array[$j]['BSSID'] = $BSSID;
					$ap_array[$j]['RSSI'] = $json_array["RSSI"];
					$lat = $result["latitude"];
					$ap_array[$j]['Lat'] = $lat;
					$long = $result['longitude'];
					$ap_array[$j]['Long'] = $long;
					$j++;
				}
			}
			print_r($ap_array);
		}
	} else {
		print_r("Error! No data!");
	}
	

?>