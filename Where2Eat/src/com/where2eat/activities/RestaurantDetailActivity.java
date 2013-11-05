package com.where2eat.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.where2eat.R;
import com.where2eat.controllers.ApplicationsController;
import com.where2eat.controllers.MapController;
import com.where2eat.controllers.RestaurantInfoController;
import com.where2eat.model.Restaurant;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestaurantDetailActivity extends FragmentActivity {

	//Model View Controller
	
	//Model
	private Restaurant restaurantSelected;
	Location gpsLocation;

	//View
	//Directly taken from the Controller.
	
	//Controller
	private MapController mapController;
	private RestaurantInfoController restaurantInfoController;
	private ApplicationsController applicationsController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detalle);
		loadRestaurant();
		
		applicationsController = new ApplicationsController(restaurantSelected, this);
		
		restaurantInfoController = new RestaurantInfoController(restaurantSelected, this);
		restaurantInfoController.initialize();

		mapController = new MapController(restaurantSelected, gpsLocation, this);
		mapController.initialize();
		 
	}
	
	private void loadRestaurant() {
		Intent intent = getIntent();
		restaurantSelected = (Restaurant) intent.getSerializableExtra(RestaurantListActivity.RESTAURANT_SELECTED);
		gpsLocation = new Location("");
		gpsLocation.setLatitude((Double) intent.getSerializableExtra(RestaurantListActivity.CURRENT_LATITUD));
		gpsLocation.setLongitude((Double) intent.getSerializableExtra(RestaurantListActivity.CURRENT_LONGITUDE));
	}

    /** Called when the user clicks the Call button */
    public void callButton(View view) {
    	TextView restaurantPhoneTextView = (TextView) findViewById(R.id.valueRestaurantPhone);
    	String phone = (String) restaurantPhoneTextView.getText();
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse("tel:" + phone));

    	startActivity(intent);    	
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Agendar").setIcon(R.drawable.schedule);
	    menu.add(1, 2, 1, "Enviar Mail").setIcon(R.drawable.gmail);
	    
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item){
     
		if(applicationsController.selectItem(item)){
			return true;
		}
			
		return super.onOptionsItemSelected(item);
	}

}
