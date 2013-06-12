<?php
	include "getAccessPoint.php";
	
	$raw_array = $HTTP_RAW_POST_DATA;
	$json_array = json_decode($raw_array, true);

	if ($json_array != null) {
		
		if ((int) $json_array[0]['addFlag'] == 1) {
			$ap_array = array();

			$j = 0;
			for ($i = 1; $i < count($json_array); $i++) {
				$BSSID = $json_array[$i]['BSSID'];
				$result = getAccessPoint($BSSID);
				if ($result == null) {
					$ap_array[$j] = array();
					$ap_array[$j]['BSSID'] = $BSSID;
					$ap_array[$j]['SSID'] = $json_array[$i]['SSID'];
					$ap_array[$j]['latitude'] = $json_array[$i]['latitude'];
					$ap_array[$j]['longitude'] = $json_array[$i]['longitude'];
					$j++;
				}
			}
			print_r($ap_array);
			if ($ap_array != null) {
				$success = insertAccessPoint($ap_array[0]);
				if ($success) {
					print_r("AP added: " + $ap_array[0]);
				} else {
					print_r("Failed to add AP!");
				}
			} else {
				print_r("No AP to add!");
			}		
		} 
		else {
			$ap_array = array();

			$j = 0;
			for ($i = 1; $i < count($json_array); $i++) {
				$BSSID = $json_array[$i]["BSSID"];
				$result = getAccessPoint($BSSID);
				if ($result != null) {
					$ap_array[$j] = array();
					$ap_array[$j]['BSSID'] = $BSSID;
					$RSSI = $json_array[$i]['RSSI'];
					$normRSSI = normalizeRSSI($RSSI);
					$ap_array[$j]['RSSI'] = $normRSSI;
					$lat = $result['latitude'];
					$ap_array[$j]['latitude'] = $lat;
					$long = $result['longitude'];
					$ap_array[$j]['longitude'] = $long;
					$j++;
				}
			}
			//print_r($ap_array);
			$geolocation = triangulate($ap_array);
			print_r($geolocation);
		}
	} else {
		print_r("Error! No data!");
	}

	function normalizeRSSI($RSSI) {
		$MIN_RSSI = -100;
		$MAX_RSSI = -55;
		$range = $MAX_RSSI - $MIN_RSSI;

		return ((100 - ((int) $RSSI * -1)) / $range) * 100;
	}

	function triangulate($data) {
		$estLat = 0;
		$estLong = 0;
		$latSum = 0;
		$longSum = 0;
		$totalWeight = 0;

		for ($i = 0; $i < count($data); $i++) {
			$RSSI = (int) $data[$i]['RSSI'];
			if ($RSSI > 33) {
				$weight = ($data[$i]['RSSI'] / 100);
				$latSum = $latSum + ((float) $data[$i]['latitude'] * $weight);
				$longSum = $longSum + ((float) $data[$i]['longitude'] * $weight);
				$totalWeight = $totalWeight + $weight;
			}
		}
	
		$estLat = $latSum / $totalWeight;
		$estLong = $longSum / $totalWeight;
	
		$location = array();
		$location[0]['latitude'] = $estLat;
		$location[0]['longitude'] = $estLong;

		return json_encode($location);
	}
	
?>