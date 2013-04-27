<?

	include "sqliConnect.php";
       
    $mysqli = connect();
	
	printf("Successfully connected to database!\n");
	
	//Turns retrieved JSON object into an associative array
	$post_data = json_decode($_POST['json_post'], TRUE);
	
		$BSSID_post = $post_data['BSSID'];
		$latitude_post = $post_data['latitude'];
		$longitude_post = $post_data['longitude'];
		$altitude_post = $post_data['altitude'];
		$additionalInfo_post = $post_data['additionalInfo'];
	
	//Check to see if BSSID already in database
	if(isInDatabase($BSSID_post) === TRUE){ //This function to be written next week
		echo "BSSID already in database\n";
	}
	else{ 
		$sql = "INSERT INTO ACCESSPOINT(
					pointID,
					BSSID,
					latitude,
					longitude,
					altitude,
					additionalInfo)
					VALUES(
					'$BSSID_post', 
					'$latitude_post',
					'$longitude_post',
					'$altitude_post',
					'$additionalInfo_post'
					)";
		if($mysqli->query($sql) === TRUE){
			printf("Insert Successful\n");
		}
		else{
			printf("Insert Failed\n");
		}
	}
       
        printf ("close connection\n");
        $mysqli->close();

?>