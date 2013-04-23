    <?php 
    $mysqli = new mysqli("mysql.eecs.oregonstate.edu", "cs419_beverlya", "fdqNpFaqnfmzKaGK", "cs419_beverlya");
    if ($mysqli->connect_errno) {
    	echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
    }
	
	?>