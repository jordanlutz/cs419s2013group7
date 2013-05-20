function triangulate(data) {
	var point1X = data[0].latitude;
	var point1Y = data[0].longitude;
	var point2X = data[1].latitude;
	var point2Y = data[1].longitude;
	var point3X = data[2].latitude;
	var point3Y = data[2].longitude;

	var centroidX = (point1X + point2X + point3X) / 3;
	var centroidY = (point1Y + point2Y + point3Y) / 3;

	var location = { "latitude":centroidX, "longitude":centroidY };

	return location;
}
