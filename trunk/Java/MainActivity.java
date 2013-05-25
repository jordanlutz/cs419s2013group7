package edu.oregonstate.mapit;

//import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
//import com.google.android.maps.Overlay;

//import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends MapActivity {

	MapView map;
	MapController controller;
	protected int latitude;
	protected int longitude;
	
	public void setlatitude(int setLat){
		latitude = (int) setLat;
	}
	
	public void setlongitude(int setLong){
		longitude = (int) setLong;
	}

	public void setPoints(){
		
		setlatitude(51643234);
		setlongitude(7848593);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		map = (MapView)findViewById(R.id.mvMainActivity);
		map.setBuiltInZoomControls(true);
		
		controller = map.getController();
		GeoPoint point = new GeoPoint(latitude, longitude);
		controller.animateTo(point);
		controller.setZoom(10);
		
		//Drawable d = getResources().getDrawable(R.drawable.icon);
		//CustomPin custom = new CustomPin(d, MainActivity.this);
		
		//List<Overlay> overLayList = map.getOverlays();
		
		//overLayList.add(custom);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
