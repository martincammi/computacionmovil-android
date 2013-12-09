package com.where2eat.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.location.Location;

import com.where2eat.model.FoodType;
import com.where2eat.model.Restaurant;
import com.where2eat.model.SortBasedOnDistance;

public abstract class RestaurantService {
	
	protected List<Restaurant> restaurants = new ArrayList<Restaurant>();
	protected boolean anyResultsWithFilter = false;
	
	public abstract List<Restaurant> search(String searchField, Location location); 
	
	public Boolean anyResultsWithFilter(){
		return anyResultsWithFilter;
	}
	
	protected List<Restaurant> getRestaurantsByNameAndFoodType(String name, String foodType, Location location){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		
		for (Restaurant restaurant : restaurants) {
			String restaurantName = restaurant.getName().toLowerCase();
			String restaurantFoodType = restaurant.getFoodType().name().toLowerCase();
		
			if(restaurantName.contains(name.toLowerCase()) || restaurantFoodType.contains(foodType.toLowerCase())){
				result.add(restaurant);
			}
			
		}

		List<Restaurant> sortedRestaurants = new ArrayList<Restaurant>(result);
		Collections.sort(sortedRestaurants, new SortBasedOnDistance(location));
		
		return sortedRestaurants;
	}
	
	protected List<Restaurant> getRestaurantsByName(String searchField, Location location){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantName = restaurant.getName().toLowerCase();
			
			if(restaurantName.contains(searchField.toLowerCase())){
				result.add(restaurant);
			}
		}
		
		return result;
	}
	
	protected List<Restaurant> getRestaurantsByFoodType(String foodType, Location location){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		foodType = foodType.replace(" ", ""); 
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantFoodType = restaurant.getFoodType().name().toLowerCase();
		
			if(restaurantFoodType.contains(foodType.toLowerCase())){
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
	
	@SuppressLint("NewApi")
	protected List<Restaurant> parseJsonResponse(String response) throws JSONException {
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		if(response == null || response.isEmpty()){
			return new ArrayList<Restaurant>();
		}
		
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
		
		return restaurants;
	}
}
