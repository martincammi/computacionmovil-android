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

public class OleoServerRestaurantService extends RestaurantService {

	private JsonService jsonService = new JsonService();
	
	@Override
	public List<Restaurant> search(String searchField, Location location) {
		System.out.println("OleoServerRestaurantService");
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
	
	//String url = "http://www.guiaoleo.com.ar/interface/json/services.phtml?usuario=VuenosZOleo&clave=OleoVZ85962011&mensaje=0012&inicio=0&numero=5&miLongitud=-58.3731620&miLatitud=-34.6151598&udid=a32bf73d44
	private String buildUrl(){
		
		String localhost = "www.guiaoleo.com.ar";
		String baseUrl = "http://" + localhost + "/interface/json/services.phtml";
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		parameters.put("usuario", "VuenosZOleo");
		parameters.put("clave", "OleoVZ85962011");
		parameters.put("mensaje", "0012");
		parameters.put("inicio", "0");
		parameters.put("numero", "5");
		parameters.put("miLongitud", "-58.3731620");
		parameters.put("miLatitud", "-34.6151598");
		parameters.put("udid", "a32bf73d44");
		
		String url = jsonService.buildUrl(baseUrl, parameters); 
		
		System.out.println("Generated url: " + url);
		
		return url; 
	}
	
	protected List<Restaurant> parseJsonResponse(String response) throws JSONException {
		
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		final JSONObject jsonObject = new JSONObject(response);
		
		String resultados = jsonObject.getString("resultados");
		
		final JSONArray jsonArray = new JSONArray(resultados);
		
		for(int i = 0; i < jsonArray.length(); i++){
			
            JSONObject jsonRestaurant = new JSONObject(jsonArray.getString(i));
            String id = jsonRestaurant.getString("ID");
            String dto = jsonRestaurant.getString("DTO");
            String nombre = jsonRestaurant.getString("NOMBRE");
            String direccion1 = jsonRestaurant.getString("DIRECCION1");
            String direccion2 = jsonRestaurant.getString("DIRECCION2");
            String cocina = jsonRestaurant.getString("COCINA");
            String telefono1 = jsonRestaurant.getString("TEL1");
            String telefono2 = jsonRestaurant.getString("TEL2");
            Double latitud = jsonRestaurant.getDouble("LAT");
            Double longitud = jsonRestaurant.getDouble("LONG");
            String distancia = jsonRestaurant.getString("DISTANCIA");
            String comida = jsonRestaurant.getString("Comida");
            String ambiente = jsonRestaurant.getString("Ambiente");
            String servicio = jsonRestaurant.getString("Servicio");
            String precioMediodia = jsonRestaurant.getString("PrecioMediodia");
            String precioNoche = jsonRestaurant.getString("PrecioNoche");
            String votos = jsonRestaurant.getString("VOTOS");
            String url = jsonRestaurant.getString("URL");
            String horario = jsonRestaurant.getString("HORARIO");
            
            cocina = filterAccents(cocina);
            FoodType foodType;            
            try {
				foodType = FoodType.valueOf(cocina.toUpperCase());
			} catch (Exception e) {
				foodType = FoodType.OTRA;
			}
 
            
            Restaurant restaurant = new Restaurant(nombre, direccion1, telefono1, latitud, longitud, foodType);
            restaurants.add(restaurant);
        }
		
		return restaurants;
	}

	private String filterAccents(String foodType) {
		foodType = foodType.replace("á", "a");
		foodType = foodType.replace("é", "e");
		foodType = foodType.replace("í", "i");
		foodType = foodType.replace("ó", "o");
		foodType = foodType.replace("ú", "u");
		return foodType;
	}
	
}
