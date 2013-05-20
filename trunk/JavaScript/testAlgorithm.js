	
	//Array containing the BSSIDs from the phone/device
	var accessPointsFound = new Array();
	
	//This would be an array containing the new Access Point Data
	var newAccessPoint = new Array();
	
	//Arrays that will have the BSSIDs, latitudes, and longitudes taken from database.
	var latArray = new Array();
	var lonArray = new Array();
	
	
	function getAjax(){
		$.ajax({
			type:"GET",
            dataType:"json",
            url:"getAccessPoint2.php",
            success:function(items) {
						for (var i = 0; i < items.length; i++) {
							var item = items[i];
							parsePoints(item.BSSID, item.latitude, item.longitude);
						}
						calcCenter();
						alert("GET successful");
					},
			error:function(a,b,c) {
					alert("ERROR: "+a);
				  }
		});
	}
	
	function parsePoints(BSSID, latitude, longitude){
		var i;
		/*
			Compares the access points being passed from the person's device
			With the access points in the database if they match it will take the longitude and latitude of those
			devices into consideration
		*/
		for( i = 0; i < accessPointsFound.length; i++){
			if(accessPointsFound[i] == BSSID){
				latArray.push(latitude);
				lonArray.push(longitude);
			}
		}
	}
	
	/*
		Calculates the average of the averages of the latitudes and longitudes that are nearby
	*/
	function calcCenter(){
		var i;
		var latAverage = 0;
		for(i = 0; i < latArray.length; i++){
			latAverage = latAverage + latArray[i];
		}
		latAverage = latAverage/latArray.length;
		
		for(i = 0; i < lonArray.length; i++){
			lonAverage = lonAverage + lonArray[i];
		}
		
		lonAverage = lonAverage / lonArray.length;
		
		/*
			Prints the location out on the screen
		*/
		
		document.getElementById("latitudeDiv").innerHTML = latAverage;
		document.getElementById("longitudeDiv").innerHTML = lonAverage;
	}
	
	
	function postAjax(){
		//var jsonData = {"BSSID" : "33333.A", "latitude" : 1.1111, "longitude" : 1.1111, "altitude" : 1.1111, "additionalInfo" : "test"};
		
		var jsonToSend = JSON.stringify(jsonData);
		
		$.ajax({
			type: "POST",
			datatype: "json",
			url: "postAccessPoint.php",
			data: {data : jsonToSend},
			success: function(data){
				alert(data);
			},
			error: function(a, b, c){
				alert("error");
			}
		});
		
	}