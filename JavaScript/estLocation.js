function estLoc(data) {
	var estLat = 0;
	var estLong = 0;
	var latSum = 0;
	var longSum = 0;
	var totalWeight = 0;

	var i;

	for (i = 0; i < data.length; i++) {
	
		if (data[i].sigStrength > 33) {
			var weight;
			weight = (data[i].sigStrength / 100);
			latSum = latSum + (data[i].latitude * weight);
			longSum = longSum + (data[i].longitude * weight);
			totalWeight = totalWeight + weight;
		}
	}
	
	estLat = latSum / totalWeight;
	estLong = longSum / totalWeight;
	var location = { "latitude": estLat, "longitude": estLong };

	return location;
}