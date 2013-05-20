<?php

	$obj = json_decode($HTTP_RAW_POST_DATA);
	$BSSID = $obj->{'BSSID'};
	$RSSI = $obj->{'RSSI'};

	echo "BSSID: ".$BSSID.", ";
	echo "RSSI: ".$RSSI;

?>