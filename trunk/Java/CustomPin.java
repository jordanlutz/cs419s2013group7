package edu.oregonstate.mapit;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class CustomPin extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> pinpoint = new ArrayList<OverlayItem>();
	private Context con;
	
	public CustomPin(Drawable defaultMarker) {
		super(boundCenter(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public CustomPin(Drawable m, Context context) {
		this(m);
		con = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return pinpoint.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return pinpoint.size();
	}
	
	public void insertPinpoint(OverlayItem item){
		pinpoint.add(item);
		this.populate();
	}

}
