package com.where2eat.services;

import java.util.List;

import android.location.Location;
import android.os.AsyncTask;
import com.where2eat.model.Restaurant;

public abstract class AsyncTaskService extends AsyncTask<String, Void,  List<Restaurant>>{

	private String query;
	private Location location;
	private RestaurantService restaurantService;
	
	public AsyncTaskService(String xQuery, Location xLocation, RestaurantService xRestaurantService){
		query = xQuery;
		location = xLocation;		
		restaurantService = xRestaurantService;
	}
	
	protected List<Restaurant> doInBackground(String... params) {		
		return restaurantService.search(query, location);	
	}
	
	public abstract void doAfterSearch(List<Restaurant> result);
	
    protected void onPostExecute(List<Restaurant> result) {
        doAfterSearch(result);
    }

//	public List<Restaurant> search() {
//		return doInBackground();
//	}

}
