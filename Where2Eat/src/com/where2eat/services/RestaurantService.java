package com.where2eat.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.where2eat.model.FoodType;
import com.where2eat.model.Restaurant;


public class RestaurantService {

	private List<Restaurant> restaurants;
	
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

	public List<Restaurant> getRestaurantsByName(String searchField, Location currentLocation){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantName = restaurant.getName().toLowerCase();
		
			if(restaurantName.contains(searchField.toLowerCase())){
				result.add(restaurant);
			}
		}
		
		return result;
	}
	
	public List<Restaurant> getRestaurantsBySpeciality(String searchField){
		
		List<Restaurant> result = new ArrayList<Restaurant>();
		searchField = searchField.replace(" ", ""); 
		
		for (Restaurant restaurant : restaurants) {
			
			String restaurantFoodType = restaurant.getFoodType().name().toLowerCase();
		
			if(restaurantFoodType.contains(searchField.toLowerCase())){
				result.add(restaurant);
			}
		}
		
		return restaurants;
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
	
}
