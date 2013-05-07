<!DOCTYPE html>
<html>
<head>
<title>Wifi Project</title>
<script src = "jquery.js"></script>
<link rel = "stylesheet" type = "text/css" href = "wifiindex.css">
</head>
<body id = "bodyWrapper">

<div id = "findCurrentLocation" onclick = "getAjax();">
	<div id= "textDiv1"> Get Location </div>
	<div id = "allitems2"> Should be Text here </div>
	
	<!--
	<div data-role="content" class="APList">        
		<ul data-role="listview" data-inset="true" id="allitems" ></ul>
	</div>
	-->

</div>

<div id = "addPoint" onclick = "postAjax();">
	<div id = "textDiv2">Add Another Location</div>
</div>

<script>

	function getAjax(){
		$.ajax({
			type:"GET",
            dataType:"json",
            url:"getAccessPoint2.php",
            success:function(items) {
						for (var i = 0; i < items.length; i++) {
							var item = items[i];
							addItem(item.pointID, item.BSSID, item.latitude, item.longitude, item.altitude, item.additionalInfo);
						}
						alert("GET successful");
					},
			error:function(a,b,c) {
					alert("ERROR: "+a);
				  }
		});
	}
	
	function addItem(pointID, BSSID, latitude, longitude, altitude, additionalInfo){
			var pointIDVar = document.createTextNode(pointID);
			var BSSIDVar = document.createTextNode(BSSID);
			var latitudeVar = document.createTextNode(latitude);
			var longitudeVar = document.createTextNode(longitude);
			var altitudeVar = document.createTextNode(altitude);
			var additionalInfoVar = document.createTextNode(additionalInfo);
			
			document.getElementById("allitems2").appendChild(pointIDVar);
			document.getElementById("allitems2").appendChild(BSSIDVar);
			document.getElementById("allitems2").appendChild(latitudeVar);
			document.getElementById("allitems2").appendChild(longitudeVar);
			document.getElementById("allitems2").appendChild(altitudeVar);
			document.getElementById("allitems2").appendChild(additionalInfoVar);
    }
	
	function postAjax(){

		var jsonData = {"BSSID" : "33333.A", "latitude" : 1.1111, "longitude" : 1.1111, "altitude" : 1.1111, "additionalInfo" : "test"};
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
</script>

</body>
</html>