    <?php 
	
	include "sqliConnect.php";
	
	$mysqli = connect();
	printf ( "Successfully connected to database!\n");
	$sql = "create table ACCESSPOINT( 
		pointID int not null auto_increment, 
		BSSID varchar(255) not null, 
		latitude Double not null, 
		longitude Double not null, 
		altitude Double not null, 
		additionalInfo varchar(255) not null, 
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