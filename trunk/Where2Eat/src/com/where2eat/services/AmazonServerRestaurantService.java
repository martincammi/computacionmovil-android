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

public class AmazonServerRestaurantService extends RestaurantService {

	private JsonService jsonService = new JsonService();
	
	
	@Override
	public List<Restaurant> search(String searchField, Location location) {
		anyResultsWithFilter = false;
		System.out.println("AmazonServerRestaurantService");
		restaurants.clear();
		List<Restaurant> restaurantsResult = getRestaurantsFromServer(searchField, location);
		
		for (Restaurant restaurant : restaurantsResult) {
			if(!"".equals(restaurant.getName())){
				restaurants.add(restaurant);
			}
		}
		
		if(searchField != null && !"".equals(searchField)){
			List<Restaurant> filteredRestaurants = getRestaurantsByNameAndFoodType(searchField, searchField, location);
			if(!filteredRestaurants.isEmpty()){
				anyResultsWithFilter = true;
				restaurants = filteredRestaurants;
			}
		}
		
		return restaurants; 
	}
	
	public List<Restaurant> getRestaurantsFromServer(String searchField, Location location){
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		String url = buildUrl(searchField, location);
		String response = jsonService.getJSONFromURL(url);
		
		try {
			restaurants = parseJsonResponse(response);
			System.out.println("Getting: " + restaurants.size() + " restaurants.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	//String url = "http://ec2-54-224-13-12.compute-1.amazonaws.com:8080/w2server/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
	private String buildUrl(String searchField, Location location){
		
		String localhost = "ec2-54-224-13-12.compute-1.amazonaws.com";
		String baseUrl = "http://" + localhost + ":8080/w2server/login";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("webservice", "true");
		parameters.put("service", "restaurantServlet");
		parameters.put("search", searchField);
		parameters.put("latitude", String.valueOf(location.getLatitude()));
		parameters.put("longitude", String.valueOf(location.getLongitude()));
		
		parameters.put("username", "USER");
		parameters.put("password", "123");

		String url = jsonService.buildUrl(baseUrl, parameters); 
		
		System.out.println("Generated url: " + url);
		
		return url; 
	}
	
}
