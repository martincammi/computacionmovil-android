package com.where2eat.services;

import java.util.List;

import android.location.Location;

import com.where2eat.model.FoodType;
import com.where2eat.model.Restaurant;

/**
 * Retrieves the Restaurant from hard-coded source  
 *
 */
public class ExampleRestaurantService extends RestaurantService {
	
	public ExampleRestaurantService(){
		initializeRestaurants();
	}
	
	private void initializeRestaurants() {
		
		restaurants.add(new Restaurant("Family", "Cabildo 2900", "4794-3452", -34.555240, -58.462556, FoodType.PARRILLA));
		restaurants.add(new Restaurant("Romario", "Libertador 2900", "4833-1422", -34.576603, -58.410950, FoodType.PIZZERIA));
		restaurants.add(new Restaurant("TGI Friday", "Alvarez Thomas 1900", "4345-8482", -34.578643, -58.468296, FoodType.AMERICANA));
		restaurants.add(new Restaurant("Miranda", "Costa Rica 5602, Palermo", "4794-3452", -34.582530, -58.434597, FoodType.PARRILLA));
		restaurants.add(new Restaurant("Rolaso", "Julián Alvarez 600, Villa Crespo", "4794-3452", -34.599205, -58.434049, FoodType.ITALIANA));
		restaurants.add(new Restaurant("Parrilla Marucha", "11 de Septiembre 3702, Nuñez", "4345-8482", -34.544450, -58.462095, FoodType.PARRILLA));
		restaurants.add(new Restaurant("Sushi Haiku", "Avenida Congreso 1618, Belgrano", "4789-0911", -34.551104, -58.454224, FoodType.SUSHI));
		restaurants.add(new Restaurant("Rodizio Costanera", "Av Rafael Obligado Costanera", "4449-0911", -34.548453, -58.430339, FoodType.TENEDORLIBRE));
		restaurants.add(new Restaurant("Mc Donald´s", "Avenida Cabildo 742", "4321-0911", -34.570330, -58.444312, FoodType.COMIDARAPIDA));
		restaurants.add(new Restaurant("Aquí me quedo", "Chile 346, San Telmo", "4331-0331", -34.615965, -58.371189, FoodType.MEXICANA));
		restaurants.add(new Restaurant("Dragon Porteño", "Arribeños 2137, Belgrano", "4784-6464", -34.557715, -58.450334, FoodType.CHINA));
		restaurants.add(new Restaurant("Casa de Pastas Milena", "Avenida Callao 1301", "4816-0918", -34.593831, -58.393099, FoodType.PASTA));
		restaurants.add(new Restaurant("Primavera Trujillana", "Franklin D. Roosevelt 1601, Belgrano", "4789-0941", -34.552866, -58.453145, FoodType.PERUANA));
	}
	
	public List<Restaurant> search(String searchField, Location location){
		System.out.println("ExampleRestaurantService");
		return getRestaurantsByNameAndFoodType(searchField, searchField, location);
	}
	
}
