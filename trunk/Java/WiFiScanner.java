package edu.oregonstate.eecs.cs419.geolocationsampleapp;

import java.util.List;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.content.Context;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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

public class WiFiScanner extends Activity {

	JSONArray jsonArray = new JSONArray();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Bypass thread policy for network activities
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		getWifiInfo();
			
		String result = httpPostWithJson("http://web.engr.oregonstate.edu/~lutzjo/cs419/echoBSSID_RSSI.php", jsonArray);
		Log.d("Server Result: ", result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getWifiInfo() {
		
		List<ScanResult> results;
		int size;

		WifiManager wifi = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);
		
		results = wifi.getScanResults();
		size = results.size() - 1;
		
		while (size >= 0) {
			String ssid = results.get(size).SSID;
			String bssid = results.get(size).BSSID;
			String rssi = String.valueOf(results.get(size).level);
			
			System.out.println("----------");
			System.out.println("AP #" + size + ": ");
			Log.d("SSID: ", ssid);
			Log.d("BSSID: ", bssid);
			Log.d("RSSI: ", rssi);
			
			JSONObject json = new JSONObject();
			
			try {
				json.put("SSID", ssid);
				json.put("BSSID", bssid);
				json.put("RSSI", rssi);
				jsonArray.put(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			size--;
		}

		return;
	}

	private String httpPostWithJson(String url, JSONArray jsonArray) {
		String result = null;
		
		try {
			HttpPost httppost = new HttpPost(url);

			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 60000);
			HttpConnectionParams.setTcpNoDelay(myParams, true);

			httppost.setHeader("Content-type", "application/json");
			HttpClient httpclient = new DefaultHttpClient(myParams);

			StringEntity se = new StringEntity(jsonArray.toString(), HTTP.UTF_8); 
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httppost.setEntity(se); 

			HttpResponse response = httpclient.execute(httppost);
			//Log.e("","response code = "+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}
		}
		catch (Exception e) {
			Log.e(result, e.toString());
		}

		return result;
	}
}
