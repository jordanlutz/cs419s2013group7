    <?php 
	
	include "sqliConnect.php";
	
	$mysqli = connect();
	echo "Successfully connected to database!";
	echo "<br />";
	$sql = "SELECT * FROM ACCESSPOINT";

	$result = $mysqli->query($sql);

	while($row = $result->fetch_array()) {
  		echo "<br />";	
		echo $row['pointID'];
 		echo "<br />";
		echo $row['BSSID'];
 		echo "<br />";
		echo $row['latitude'];
 		echo "<br />";
		echo $row['longitude'];
 		echo "<br />";
		echo $row['altitude'];
 		echo "<br />";
		echo $row['additionalInfo'];
 		echo "<br />";
 		echo "<br />";
  	}

	$mysqli->close();

	echo "<br />";
	echo "Connection closed!";
	echo "<br />";
	
	?>