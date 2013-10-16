package com.where2eat.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.where2eat.model.FoodType;
import com.where2eat.model.Restaurant;

public class GooglePlacesAPIRestaurantService extends RestaurantService {

	@Override
	public List<Restaurant> search(String searchField, Location location) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Restaurant> getRestaurantstByGoogle(Location location){

		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		String response = getGoogleRestaurantService(location);
		try {
			final JSONArray jsonArray = new JSONArray(response);
			
			for(int i = 0; i < jsonArray.length(); i++){
				
                JSONObject jsonRestaurant = new JSONObject(jsonArray.getString(i));
                String name = jsonRestaurant.getString("name");
                String address = jsonRestaurant.getString("address");
                String phone = jsonRestaurant.getString("phone");
                String foodType = jsonRestaurant.getString("foodType");
                Double latitud = jsonRestaurant.getDouble("latitud");
                Double longitud = jsonRestaurant.getDouble("longitud");
                
                Restaurant restaurant = new Restaurant(name, address, phone, latitud, longitud, FoodType.valueOf(foodType));
                restaurants.add(restaurant);
            }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	
	private String getGoogleRestaurantService(Location location){
		//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&name=harbour&sensor=false&key=AddYourOwnKeyHere
			
		JsonService jsonService = new JsonService();
		
		String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("location", location.getLatitude() + "," + location.getLongitude());
		parameters.put("radius", "500");
		parameters.put("types", "parrilla");
		//parameters.put("name", "");
		parameters.put("sensor", "true");
		parameters.put("key", "AIzaSyBUYOLO73Xc5B60ZRZIwHUuWovc9SUVHHU");
		
		String url = jsonService.buildUrl(baseUrl, parameters);
		
		System.out.println("Generated url: " + url);
		
		String response = jsonService.getJSONFromURL(url);
		
		return response;
	}

}
