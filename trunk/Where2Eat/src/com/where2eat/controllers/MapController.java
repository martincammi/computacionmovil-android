package com.where2eat.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.where2eat.R;
import com.where2eat.model.Restaurant;
import com.where2eat.services.GoogleMapsService;
import com.where2eat.services.JsonService;

public class MapController {

	//Model View Activity
	
	//Model 
	private Restaurant restaurantSelected;
	Location gpsLocation;
	
	//View 
	Marker currentMarker, restoMarker;
	
	//Activity
	private final FragmentActivity activity;
	
	//Others
	GoogleMapsService googleMapService;
	
	public MapController(Restaurant restaurantSelected, Location gpsLocation, FragmentActivity activity){
		this.restaurantSelected = restaurantSelected;
		this.gpsLocation = gpsLocation;
		this.activity = activity;
	}
	
	public void initialize(){
		googleMapService = new GoogleMapsService(activity.getBaseContext(), activity, activity.getSupportFragmentManager(), R.id.map);
		
		 if(googleMapService.servicesConnected()){
		     
			 
        	LatLng currentPosition = new LatLng(gpsLocation.getLatitude(), gpsLocation.getLongitude());
			 currentMarker = googleMapService.drawMarker( currentPosition, "", getUserIcon(), "Mi ubicaci�n");
			 googleMapService.moveToPositionInGoogleMap(currentMarker);
        	
        	
			 LatLng position = getRestaurantSelectedPosition();
			 restoMarker = googleMapService.drawMarker( position, "A comeeer", getWhere2EatIcon(), restaurantSelected.getName());
			 googleMapService.moveToPositionInGoogleMap(restoMarker);
			 String url = makeUrl(currentPosition.latitude, currentPosition.longitude, position.latitude, position.longitude);
			 JsonService parser = new JsonService();
			 String result;
				 result = parser.getJSONFromURL(url);
				 drawPath(result);
		}else{
	    	Toast.makeText( activity.getApplicationContext(), "Map not available", Toast.LENGTH_SHORT ).show();
	    }
	}

	public String makeUrl(double startLatitude, double startLongitude, double endLatitude, double endLongitude){
	    StringBuilder urlString = new StringBuilder();
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	    urlString.append("?origin=");
	    urlString.append(startLatitude);
	    urlString.append(",");
	    urlString.append(startLongitude);
	    urlString.append("&destination="); 
	    urlString.append(endLatitude);
	    urlString.append(",");
	    urlString.append(endLongitude);
	    urlString.append("&sensor=false&mode=driving");

	    return urlString.toString();
	}
	
	public void drawPath(String result){
	    try{
	        final JSONObject json = new JSONObject(result);
	        JSONArray routeArray = json.getJSONArray("routes");
	        JSONObject routes = routeArray.getJSONObject(0);

	        JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
	        String encodedString = overviewPolylines.getString("points");
	        //Log.d("test: ", encodedString);
	        List<LatLng> list = decodePoly(encodedString);

	        for (int i = 0; i < list.size()-1; i++) {
	            LatLng src = list.get(i);
	            LatLng dest = list.get(i+1);
	            googleMapService.addPolyLine(new PolylineOptions().add( 
	                    new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
	                    .width(6)
	                    .color(Color.BLACK));
	        }

	    }catch (JSONException e){
	        e.printStackTrace();
	    }
	}
	
	private List<LatLng> decodePoly(String encoded){

	    List<LatLng> poly = new ArrayList<LatLng>();
	    int index = 0;
	    int length = encoded.length();
	    int latitude = 0;
	    int longitude = 0;

	    while(index < length){
	        int b;
	        int shift = 0;
	        int result = 0;

	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);

	        int destLat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        latitude += destLat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);

	        int destLong = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        longitude += destLong;

	        poly.add(new LatLng((latitude / 1E5),(longitude / 1E5) ));
	    }
	    return poly;
	}
	
	private LatLng getRestaurantSelectedPosition() {
		return new LatLng(restaurantSelected.getLatitude(), restaurantSelected.getLongitude());
	}

	private BitmapDescriptor getWhere2EatIcon() {
		return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
	}
	
	private BitmapDescriptor getUserIcon() {
		return BitmapDescriptorFactory.fromResource(R.drawable.user);
	}
}
