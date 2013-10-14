package com.where2eat.controllers;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.where2eat.R;
import com.where2eat.activities.RestaurantDetailActivity;
import com.where2eat.activities.RestaurantListActivity;
import com.where2eat.model.GpsLocation;
import com.where2eat.model.Restaurant;
import com.where2eat.model.RestaurantListAdapter;
import com.where2eat.services.GoogleMapsService;
import com.where2eat.services.PositionsService;
import com.where2eat.services.RestaurantService;

public class RestaurantListController {

	//Model View Activity
	private List<Restaurant> restaurants;
	private ListView listView;
	private final Activity activity;
	
	//Others
	private final RestaurantService restaurantService;
	private final 	GpsLocation gpsAdmin; //TODO rename to GPSLocator
	List<String> restaurantAsString = new ArrayList<String>();

	
	public RestaurantListController(List<Restaurant> restaurants, ListView listView, Activity activity){
		this.restaurants = restaurants;
		this.listView = listView;
		this.activity = activity;
		
		this.restaurantService = new RestaurantService();
		this.gpsAdmin = createGps(); 
	}
	
	private GpsLocation createGps(){
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		return new GpsLocation(locationManager);
	}
	
	public void loadRestaurantList(){
		
		gpsAdmin.updateLocation();
		if ( gpsAdmin.getLocation().equals(gpsAdmin.getDefaultLocation()) ){
			//startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			Toast.makeText( activity.getApplicationContext(), "There is no GPS connection available, setting last known location", Toast.LENGTH_LONG ).show();
		}else{
			Toast.makeText( activity.getApplicationContext(), "GPS available", Toast.LENGTH_LONG ).show();
		}
		
        RestaurantListAdapter adapter = new RestaurantListAdapter(activity, R.layout.resto_row, restaurants, gpsAdmin.getLocation());
        listView.setAdapter(adapter);
        
        OnItemClickListener onRestaurantClickHandler = new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
            	goToRestaurantDetalleActivity(view);
            }
        };

        listView.setOnItemClickListener(onRestaurantClickHandler);
		
	}
	
	 public void goToRestaurantDetalleActivity(View view) {

		Intent intent = new Intent(activity, RestaurantDetailActivity.class);
		RelativeLayout relativeView = (RelativeLayout) view;
		TextView textView = (TextView) relativeView.getChildAt(1);
		
		Restaurant restaurant = getRestaurantSelected(textView.getText());
		Location gpsLocation = gpsAdmin.getLocation();
		if(gpsLocation == null){
			gpsLocation = GoogleMapsService.getLocation(PositionsService.CIUDAD_UNIVERSITARIA);
		}

		
		if(restaurant != null){
			intent.putExtra(RestaurantListActivity.RESTAURANT_SELECTED, restaurant);
			intent.putExtra(RestaurantListActivity.CURRENT_LATITUD, gpsLocation.getLatitude());
			intent.putExtra(RestaurantListActivity.CURRENT_LONGITUDE, gpsLocation.getLongitude());
		}
		
    	activity.startActivity(intent);
	 }
	 
	 private Restaurant getRestaurantSelected(CharSequence text) {
	    	
    	for (Restaurant restaurant : restaurants) {
			if(restaurant.getName().equals(text)){
				return restaurant;
			}
		} 
    	
    	return null;

	}
	 
	public ListView updateRestaurants(String query) {
			
		//RestaurantListActivity algo = new RestaurantListActivity();
		//GoogleMapsService googleMapService;
		//googleMapService = new GoogleMapsService(getBaseContext(), algo, getSupportFragmentManager(), R.id.map);
		Location gpsLocation = gpsAdmin.getLocation();
		
		restaurants = restaurantService.searchRestaurants(query, gpsLocation);
		//restaurants = restaurantService.searchRestaurantOnLocalServer(gpsLocation);
		
		restaurantAsString.clear();
		restaurantAsString.addAll(RestaurantService.getRestaurantsAsString(restaurants, "name"));

		ListView listView = (ListView) activity.findViewById(R.id.rest_list_view);
        listView.invalidateViews();
        RestaurantListAdapter adapter = new RestaurantListAdapter(activity, R.layout.resto_row, restaurants, gpsAdmin.getLocation());
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantAsString);
        listView.setAdapter(adapter);

		return listView;
	}
}
