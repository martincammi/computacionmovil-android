package com.where2eat.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.where2eat.model.GpsLocator;
import com.where2eat.model.Restaurant;
import com.where2eat.model.RestaurantListAdapter;
import com.where2eat.services.AmazonServerRestaurantService;
import com.where2eat.services.AsyncTaskService;
import com.where2eat.services.RestaurantService;

public class RestaurantListController implements Observer, Controller {

	//Model View Activity
	
	//Model
	private List<Restaurant> restaurants;
	private Location currentLocation;
	
	//View
	private ListView listView;
	
	//Activity
	private final Activity activity;
	
	//Others
	private RestaurantService restaurantService;
	private final 	GpsLocator gpsAdmin; //TODO rename to GPSLocator
	List<String> restaurantAsString = new ArrayList<String>();

	
	public RestaurantListController(List<Restaurant> restaurants, ListView listView, Activity activity){
		this.restaurants = restaurants;
		this.listView = listView;
		this.activity = activity;
		
		this.restaurantService = new AmazonServerRestaurantService();
		//this.restaurantService = new LocalServerRestaurantService();
		this.gpsAdmin = createGps(); 
	}
	
	public void setRestaurantService(RestaurantService restaurantService){
		this.restaurantService = restaurantService;
	}
	
	private GpsLocator createGps(){
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		GpsLocator gpsLocator = new GpsLocator(locationManager);
		gpsLocator.addObserver(this);
		return gpsLocator;
	}
	
	public void initialize(){
		
		List<String> providers = gpsAdmin.getCurrentProviders();
		
		Toast.makeText( activity.getApplicationContext(), providers.toString(), Toast.LENGTH_SHORT ).show();
		
        RestaurantListAdapter adapter = new RestaurantListAdapter(activity, R.layout.resto_row, restaurants, gpsAdmin.getLocation());
        listView.setAdapter(adapter);
        
        OnItemClickListener onRestaurantClickHandler = new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
//		if(gpsLocation == null){
//			gpsLocation = GoogleMapsService.getLocation(PositionsService.CIUDAD_UNIVERSITARIA);
//		}

		
		if(restaurant != null){
			intent.putExtra(RestaurantListActivity.RESTAURANT_SELECTED, restaurant);
			intent.putExtra(RestaurantListActivity.CURRENT_LATITUD, gpsLocation.getLatitude());
			intent.putExtra(RestaurantListActivity.CURRENT_LONGITUDE, gpsLocation.getLongitude());
		}
		
    	activity.startActivity(intent);
	 }
	 
	 public void startSearch(String query){
		 gpsAdmin.startSearch();
		 updateRestaurants(query);
	 }
	 
	 public void startScan(){
		 gpsAdmin.startProcessingLocation(5000);
	 }
	 
	 public void stopScan(){
		 gpsAdmin.stopProcessingLocation(); 
	 }
	 
	 private Restaurant getRestaurantSelected(CharSequence text) {
	    	
    	for (Restaurant restaurant : restaurants) {
			if(restaurant.getName().equals(text)){
				return restaurant;
			}
		} 
    	
    	return null;

	}
	 
	public void updateRestaurants(final String query) {
			
		Location gpsLocation = gpsAdmin.getLocation();
		
		AsyncTaskService asyncTaskService = new AsyncTaskService(query, gpsLocation, restaurantService){

			@Override
			public void doAfterSearch(List<Restaurant> xRestaurants) {
				
				if(query != null && !"".equals(query)){
					if(!restaurantService.anyResultsWithFilter()){
						Toast.makeText( activity.getApplicationContext(), "No hubo coincidencias. Mostrando más cercanas", Toast.LENGTH_SHORT ).show();
					}
				}
				
				restaurants = xRestaurants;
				restaurantAsString.clear();
				restaurantAsString.addAll(RestaurantService.getRestaurantsAsString(restaurants, "name"));

				ListView listView = (ListView) activity.findViewById(R.id.rest_list_view);
		        listView.invalidateViews();
		        RestaurantListAdapter adapter = new RestaurantListAdapter(activity, R.layout.resto_row, restaurants, gpsAdmin.getLocation());
		        listView.setAdapter(adapter);				
			}

		
		};
		asyncTaskService.execute();
		
	}

	@Override
	public void update(Observable observable, Object data) {
		System.out.println("Getting notified by observer");
		// A new position was detected by the GPS update it on the listView.
		Location newLocation = gpsAdmin.getLocation();
		
		
		String locationStr = "No location found";
		
		if(newLocation != null){
			if(currentLocation != null && (newLocation.getLatitude() == currentLocation.getLatitude() && newLocation.getLongitude() == currentLocation.getLongitude())){
				Toast.makeText( activity.getApplicationContext(), "Misma ubicación, no se actualiza", Toast.LENGTH_SHORT ).show();
			}else{
				Toast.makeText( activity.getApplicationContext(), "Nueva ubicación encontrada de " +  newLocation.getProvider() + ", actualizando restaurants", Toast.LENGTH_SHORT ).show();
				updateRestaurants("");
				currentLocation = newLocation;
			}
			locationStr = "(" + newLocation.getLatitude() + "," + newLocation.getLongitude() + ")";
		}
		
		if(currentLocation == null){
			currentLocation = newLocation;
		}
		
		System.out.println("GPS Location update: " + locationStr);
		
		
	}
	
}
