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
		System.out.println("AmazonServerRestaurantService");
		this.restaurants = getRestaurantsFromServer(searchField);
		return getRestaurantsByNameAndFoodType(searchField, searchField, location);
	}
	
	public List<Restaurant> getRestaurantsFromServer(String searchField){
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		String url = buildUrl();
		String response = jsonService.getJSONFromURL(url);
		
		try {
			restaurants = parseJsonResponse(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}

	//String url = "http://ec2-54-224-13-12.compute-1.amazonaws.com:8080/w2server/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
	private String buildUrl(){
		
		String localhost = "ec2-54-224-13-12.compute-1.amazonaws.com";
		String baseUrl = "http://" + localhost + ":8080/w2server/login";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("webservice", "true");
		parameters.put("service", "restaurantServlet");
		parameters.put("name", "hola");
		parameters.put("cooking", "parrilla");
		
		parameters.put("username", "USER");
		parameters.put("password", "123");

		String url = jsonService.buildUrl(baseUrl, parameters); 
		
		System.out.println("Generated url: " + url);
		
		return url; 
	}
	
}
