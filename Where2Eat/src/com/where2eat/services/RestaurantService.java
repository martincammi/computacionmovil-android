package com.where2eat.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.where2eat.model.Restaurant;

public class RestaurantService {

	
	public List<Restaurant> getRestaurantsByName(String name){
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(new Restaurant(name, "Cabildo 2900", "4794-3452"));
		restaurants.add(new Restaurant("Romario", "Libertador 2900", "4833-1422"));
		restaurants.add(new Restaurant("TGI Friday", "Alvarez Thomas 1900", "4345-8482"));
		
		return restaurants;
	}
	
	public List<Restaurant> getRestaurantsBySpeciality(String culinarySpecialty){
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		restaurants.add(new Restaurant("Miranda", "Costa Rica 5602, Palermo", "4794-3452"));
		restaurants.add(new Restaurant("Rolaso", "Julián Alvarez 600, Villa Crespo", "4794-3452"));
		restaurants.add(new Restaurant("Parrilla Marucha", "11 de Septiembre 3702, Nuñez", "4345-8482"));
		
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
