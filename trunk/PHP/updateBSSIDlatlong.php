<?PHP
	$mysqli = new mysqli("mysql.cs.orst.edu", "cs419_beverlya", "qXHYN3NmNXGZcTQQ", "cs419_beverlya");
	if ($mysqli->connect_errno) {
		echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
		exit();
	}
	
    if (isset($_POST['BSSID'])) {
    	$BSSID = $_POST['BSSID'];
		$latitude = $_POST['latitude'];
		$longitude = $_POST['longitude'];
    	$result = $mysqli->query( "UPDATE ACCESSPOINT SET latitude = ".$latitude.", longitude=".$longitude."WHERE BSSID = '".$BSSID."'");
    	
    }
	$BSSIDquery = $mysqli->query("SELECT DISTINCT BSSID FROM ACCESSPOINT");
    
    $sectionnumdropdown = '<select name="BSSID">';
    while($BSSID = $BSSIDquery->fetch_object()) {
    	$sectionnumdropdown .= '<option value="'.$BSSID->BSSID.'">'.$BSSID->BSSID.'</option>';
    }
    $sectionnumdropdown .= '</select>';
	
    mysqli_close($mysqli);
    ?>
<html>
    <head>
        <title> Triangulator </title>
    </head>
    <body align="center" bgcolor="000000">
    
    	<br><br><br><br><br>
    	<table align="center" width="75%" border="1" length="55%" bgcolor="cccccc"><tr><td align="right"><br>Form For Testing<br><br>

	    <form enctype = "multipart/form-data"  action="updateBSSIDlatlong.php" method="POST">
	        <table  align="center" border="7" bgcolor="F88017">
                   <tr>
       		         <td colspan ="2">
                		<center>Select BSSID to Update
	                </td>
        	    </tr>
		    <tr>
        		<td>BSSID</td>
                <td>
					<div align = "center">
					<?php
						echo $sectionnumdropdown;
					?>
				<br><input type='submit' value='submit'  />
			</td>	
	      </tr>
		</table>
	 </form>
	 <form enctype = "multipart/form-data"  action="updateBSSIDlatlong.php" method="POST">
	        <table  align="center" border="7" bgcolor="F88017">
	             <tr>
	                <td>BSSID</td>
	                <td><?php echo $_POST['BSSID']; ?></td>
					<input type="hidden" name="BSSID" value="<?php echo $_POST['BSSID']; ?>" />
	            </tr>
				 <tr>
	                <td>Latitude</td>
	                <td><input type="text" name="latitude"></td>
	            </tr>
	             <tr>
	                <td>Longitude</td>
	                <td><input type="text" name="longitude"></td>
	            </tr>
	        </table>
	        <br>
	        <center>
	        <input type='submit' value='submit'  />
	        <input type= "reset" value="clear">
	        <br>
		</center>
		<br>
		</form>
	    </td></tr></table>
    </body>
</html>