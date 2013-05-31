package com.example.wifiproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddAccessPoint extends Activity implements OnClickListener {

	JSONArray jsonArrayGet = new JSONArray();
	JSONArray jsonArrayPost = new JSONArray();
	
	LocationManager lm;

	Button addPoint;
	Button checkLocation;
	Button testButton;
	
	TextView currentLocation;
	TextView strongestBSSID;
	TextView checkDatabase;
	TextView testText;

	String rssi = "";
	String bssid = "";
	String ssid = "";
	String latString = "";
	String lonString = "";

	String getRequestString = "http://web.engr.oregonstate.edu/~cooneyj/CS419/testget.php?checkBSSID"; //This works
	String postRequestString = "http://web.engr.oregonstate.edu/~cooneyj/CS419/testpost.php"; //Needs to be tested

	double latitude = 0.0;
	double longitude = 0.0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addaccesspoint);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		addPoint = (Button) findViewById(R.id.addPoint);
		checkLocation = (Button) findViewById(R.id.checkLocation);
		testButton = (Button) findViewById(R.id.testButton);

		currentLocation = (TextView) findViewById(R.id.currentLocation);
		strongestBSSID = (TextView) findViewById(R.id.strongestBSSID);
		checkDatabase = (TextView) findViewById(R.id.checkDatabase);
		testText = (TextView) findViewById(R.id.testText);

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		addPoint.setOnClickListener(this);
		checkLocation.setOnClickListener(this);
		testButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		int viewId = v.getId();
		String location;

		//This will add the current location to the database
		if (viewId == R.id.addPoint) {
			location = getLocation(); //Finds current location returns string of current location
			currentLocation.setText(location); //Sets UI text to current location coordinates
			postStrongestRSSI(true); //Set to true so that it will be put in database if able
		}
		else if (viewId == R.id.currentLocation){
			location = getLocation();
			currentLocation.setText(location);
			postStrongestRSSI(false);
		}
		else if(viewId == R.id.testButton){
			String result = getRequest();
			testText.setText(result);
		}
	}
	
	private void postStrongestRSSI(boolean putInDatabase) {
		JSONObject json = new JSONObject();
		int rssiInt = -1000;
		int size;
		List<ScanResult> results;
		WifiManager wifi = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
		results = wifi.getScanResults();
		size = results.size() - 1;
		try {
			while (size >= 0) {
				rssi = String.valueOf(results.get(size).level);
				if (results.get(size).level > rssiInt) {
					rssiInt = results.get(size).level;
					ssid = results.get(size).SSID;
					bssid = results.get(size).BSSID;
				}
				size--;
			}
			
			if(checkBSSID() == true && putInDatabase == false){
				strongestBSSID.setText("BSSID: " + bssid + " RSSI: " + rssi);
				checkDatabase.setText("Not in Database");
			}
			else if(checkBSSID() == false && putInDatabase == false){
				strongestBSSID.setText("BSSID: " + bssid + " RSSI: " + rssi);
				checkDatabase.setText("Already In Database");
			}
			else if(checkBSSID() == false && putInDatabase == true){
				strongestBSSID.setText("BSSID: " + bssid + " RSSI: " + rssi);
				checkDatabase.setText("Could Not Add - Already In Database");
			}
			else if (checkBSSID() == true && putInDatabase == true) {
				rssi = String.valueOf(rssiInt);
				latString = String.valueOf(latitude);
				lonString = String.valueOf(longitude);

				//These are the five variables to be added to the database
				json.put("SSID", ssid);
				json.put("BSSID", bssid);
				json.put("RSSI", rssi);
				json.put("latitude", latString);
				json.put("longitude", lonString);
				jsonArrayPost.put(json);
				httpPostWithJson(); //Post Function called here
				
				strongestBSSID.setText("BSSID: " + bssid + " RSSI: " + rssi);
				checkDatabase.setText("Added to Database");
			}
		}
		catch (Exception e) {
		}
	}
	
	private String httpPostWithJson() {
		String result = null;

		try {
			HttpPost httppost = new HttpPost(postRequestString);
			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 60000);
			HttpConnectionParams.setTcpNoDelay(myParams, true);

			httppost.setHeader("Content-type", "application/json");
			HttpClient httpclient = new DefaultHttpClient(myParams);

			StringEntity se = new StringEntity(jsonArrayPost.toString(), HTTP.UTF_8);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
			httppost.setEntity(se);
			HttpResponse response = httpclient.execute(httppost);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}
		}
		catch (Exception e) {}
		return result;
	}
	
	//Tested-works
	private String getRequest() {
		URL url;
		HttpURLConnection connection;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			url = new URL(getRequestString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			rd.close();
		} catch (Exception e) {}
		return result;
	}
	
	//Returns true if not in database (true that it is unique)
	private boolean checkBSSID() {
		List<ScanResult> results;
		String getResult;
		int rssiInt = -1000;
		int size;
		
		WifiManager wifi = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
		results = wifi.getScanResults();
		size = results.size() - 1;

		while (size >= 0) {
			if (results.get(size).level >= rssiInt) {
				rssiInt = results.get(size).level;
				bssid = results.get(size).BSSID;
			}
			size--;
		}

		rssi = String.valueOf(rssiInt);
		getResult = getRequest();
		System.out.println(getResult);

		if (getResult.contains(bssid)) {
			return false;
		} 
		else {
			return true;
		}
	}
	
	//Currently Set to this method to return location
	public String getLocation() {
		String provider = LocationManager.GPS_PROVIDER;
		Location location = lm.getLastKnownLocation(provider); // uses get last known location
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		String writeString = "Latitude:" + latitude + "Longitude: " + longitude;
		return writeString;
	}

	//If method above is too inaccurate/buggy, we can try this as well
	public String getLocation2() {
		Geocoder geocoder;
		String bestProvider;
		List<Address> user = null;

		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);

		if (location == null) {
			Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG).show();
			return " ";
		}
		else {
			geocoder = new Geocoder(this);
			try {
				user = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
				latitude = (double) user.get(0).getLatitude();
				latitude = (double) user.get(0).getLongitude();
				System.out.println("latitude: " + latitude + ",  longitude: "+ longitude);
				return "latitude: " + latitude + ",  longitude: " + longitude;
			} 
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return " ";

	}
}
