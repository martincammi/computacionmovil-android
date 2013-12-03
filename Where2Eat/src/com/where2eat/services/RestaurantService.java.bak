package com.where2eat.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

import com.where2eat.model.FoodType;
import com.where2eat.model.Restaurant;
import com.where2eat.model.SortBasedOnDistance;


public class RestaurantService {

	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	public RestaurantService(){
		initializeRestaurants();
	}
	
	private void initializeRestaurants() {
		
		restaurants.add(new Restaurant("Family", "Cabildo 2900", "4794-3452", -34.555240, -58.462556, FoodType.PARRILLA));
		restaurants.add(new Restaurant("Romario", "Libertador 2900", "4833-1422", -34.576603, -58.410950, FoodType.PIZZERIA));
		restaurants.add(new Restaurant("TGI Friday", "Alvarez Thomas 1900", "4345-8482", -34.578643, -58.468296, FoodType.AMERICANA));
		restaurants.add(new Restaurant("Miranda", "Costa Rica 5602, Palermo", "4794-3452", -34.582530, -58.434597, FoodType.PARRILLA));
		restaurants.add(new Restaurant("Rolaso", "Julián Alvarez 600, Villa Crespo", "4794-3452", -34.599205, -58.434049, FoodType.ITALIANA));
		restaurants.add(new Restaurant("Parrilla Marucha", "11 de Septiembre 3702, Nuñez", "4345-8482", -34.544450, -58.462095, FoodType.PARRILLA));
	}
	
	public List<Restaurant> searchRestaurantOnLocalServer(){
		
		return getRestaurantsByJason();
		
	}

	public List<Restaurant> searchRestaurants(String searchField, Location location){
		
		Set<Restaurant> result = new HashSet<Restaurant>();
		
		result.addAll(getRestaurantsByName(searchField, location));
		result.addAll(getRestaurantsBySpeciality(searchField, location));
		
		List<Restaurant> sortedRestaurants = new ArrayList<Restaurant>(result);
		
		Collections.sort(restaurants, new SortBasedOnDistance(location));
		
		return sortedRestaurants;
	}
	
	private List<Restaurant> getRestaurantsByName(String searchField, Location location){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantName = restaurant.getName().toLowerCase();
		
			if(restaurantName.contains(searchField.toLowerCase())){
				result.add(restaurant);
			}
		}
		
		return result;
	}
	
	private List<Restaurant> getRestaurantsBySpeciality(String searchField, Location location){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		searchField = searchField.replace(" ", ""); 
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantFoodType = restaurant.getFoodType().name().toLowerCase();
		
			if(restaurantFoodType.contains(searchField.toLowerCase())){
				result.add(restaurant);
			}
		}
		
		return result;
	}
	
	public static List<String> getRestaurantsAsString(List<Restaurant> restaurants, String fieldName){
		
		List<String> restaurantsAsAttribute = new ArrayList<String>();
		
		String getterName = "get"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1, fieldName.length());
		try {
			Method getterMethod = Restaurant.class.getMethod(getterName);
			
			for (Restaurant restaurant : restaurants) {
				Object result = getterMethod.invoke(restaurant);
				restaurantsAsAttribute.add((String)result);
			}
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return restaurantsAsAttribute;
	}
	
	public List<Restaurant> getRestaurantsByJason(){
		//String url = "http://localhost:8081/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		String response = getLocalRestaurantService();
		try {
			final JSONObject json = new JSONObject(response);
			
			String name = (String) json.get("name");
			String address = (String) json.get("address");
			String phone = (String) json.get("phone");
			String foodType = (String) json.get("foodType");
			
			Double latitud = PositionsService.CIUDAD_UNIVERSITARIA.latitude;
			Double longitud = PositionsService.CIUDAD_UNIVERSITARIA.longitude;
			
			Restaurant restaurant = new Restaurant(name, address, phone, latitud, longitud, FoodType.AMERICANA);
			
			restaurants.add(restaurant);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return restaurants;
	}

	private String getLocalRestaurantService() {
		JsonService jsonService = new JsonService();
		
		String localhost = "10.0.2.2";
		
		String baseUrl = "http://" + localhost + ":8080/androidServices/login";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("webservice", "true");
		parameters.put("service", "restaurantServlet");
		parameters.put("name", "hola");
		parameters.put("cooking", "parrilla");
		
		parameters.put("username", "USER");
		parameters.put("password", "123");
		
		String url = jsonService.buildUrl(baseUrl, parameters);
		
		System.out.println("Generated url: " + url);
		
		String response = jsonService.getJSONFromURL(url);
		
		return response;
	}
	
}
