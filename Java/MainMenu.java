package com.example.projectinterface;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {

	Button findPosition;
	Button addAccessPoint;
	Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		findPosition = (Button) findViewById(R.id.button1);
		addAccessPoint = (Button) findViewById(R.id.button2);
		findPosition.setOnClickListener(this);
		addAccessPoint.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		// If findPosition button clicked
		case R.id.button1:
			intent = new Intent("com.example.projectinterface.FINDPOSITION");
			startActivity(intent);
			
		// If newAccessPoint button clicked
		case R.id.button2:
			intent = new Intent("com.example.projectinterface.ADDACCESSPOINT");
			startActivity(intent);
		
		}
	}
	
}
