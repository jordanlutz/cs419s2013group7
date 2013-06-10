  <?php 
	/****
	* Function creates a connect to database.
	****/
	function connect(){
		$mysqli = new mysqli("oniddb.cws.oregonstate.edu", "cooneyj-db", "YTmGNf8eDwxFyGy5", "cooneyj-db");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			exit();
		}
		return $mysqli;
	}
	?>