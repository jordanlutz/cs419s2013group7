package edu.oregonstate.eecs.cs419.geolocationsampleapp.mapit3;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

public class MainActivity extends Activity  {
	
	//private GoogleMap mMap;
	//private double latitude;
	//private double longitude;
	//public static final String ARG_ITEM_LAT = "item_Lat";
	//public static final String ARG_ITEM_LONG = "item_Long";
	LatLng OSU = new LatLng(44.565951, -123.278909);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Intent intent = getIntent();
        //latitude = Double.parseDouble(intent().getStringExtra(ARG_ITEM_LAT));
        //longitude = Double.parseDouble(intent.getStringExtra(ARG_ITEM_LONG));

		MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		GoogleMap map = mapFrag.getMap();
        CameraUpdate cUpdate = CameraUpdateFactory.newLatLng(OSU);
        map.animateCamera(cUpdate);
        
        map.addMarker(new MarkerOptions().position(new LatLng(44.565951, -123.278909)).title("You Are Here!"));
	
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
