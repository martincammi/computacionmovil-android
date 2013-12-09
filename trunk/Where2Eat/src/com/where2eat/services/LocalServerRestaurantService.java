package com.where2eat.services;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.location.Location;

import com.where2eat.model.Restaurant;

/**
 * Retrieves the Restaurant from local server
 *
 */
public class LocalServerRestaurantService extends RestaurantService {

	private JsonService jsonService = new JsonService();
	
	@Override
	public List<Restaurant> search(String searchField, Location location) {
		anyResultsWithFilter = false;
		restaurants.clear();
		System.out.println("LocalServerRestaurantService");
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
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}
	
	//http://localhost:8080/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123
	//http://localhost:8080/androidServices/login?webservice=true&service=restaurantServlet&search=parrilla&username=USER&password=123&latitude=-34.555240&longitude=-58.462556
	private String buildUrl(String searchField, Location location){
		
		String localhost = "192.168.1.100";
		String baseUrl = "http://" + localhost + ":8080/androidServices/login";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("webservice", "true");
		parameters.put("service", "restaurantServlet");
		searchField = URLEncoder.encode(searchField);
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
