    <?php 
	
	include "sqliConnect.php";
	
	$mysqli = connect();
	printf ( "Successfully connected to database!\n");
	$sql = "create table ACCESSPOINT( 
		pointID int not null auto_increment, 
		BSSID varchar(255) not null, 
		latitude varchar(255), 
		longitude varchar(255), 
		altitude varchar(255), 
		additionalInfo varchar(255), 
		primary key (pointID))";
	
	if( $mysqli->query($sql) === TRUE){
		printf("Table Created\n");
	}
	else{
		printf("Table failed to create\n");
	}
	
	printf ("close connection\n");
	$mysqli->close();
	
		
	?>