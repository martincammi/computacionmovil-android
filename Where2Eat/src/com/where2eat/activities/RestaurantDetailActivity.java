package com.where2eat.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.where2eat.R;
import com.where2eat.model.Restaurant;
import com.where2eat.services.GoogleMapsService;
import com.where2eat.services.JsonService;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestaurantDetailActivity extends FragmentActivity {
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static final LatLng CIUDAD_UNIVERSITARIA = new LatLng(-34.541672, -58.442189);
	private Restaurant restaurantSelected;
	//LatLng restoLocation;
	Location gpsLocation;
	Marker currentMarker, restoMarker;
	GoogleMapsService googleMapService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detalle);
		googleMapService = new GoogleMapsService(getBaseContext(), this, getSupportFragmentManager(), R.id.map);
		
		
		loadRestaurant();
		updateRestaurantView();
		
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
			 //googleMapService.addPolyLine(new PolylineOptions().add(currentPosition,position).geodesic(true));
		}else{
	    	Toast.makeText( getApplicationContext(), "Map not available", Toast.LENGTH_SHORT ).show();
	    }
		 
	}

	public String makeUrl(double startLatitude, double startLongitude, double endLatitude, double endLongitude){
	    StringBuilder urlString = new StringBuilder();
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	    urlString.append("?origin="); //start positie
	    urlString.append(startLatitude);
	    urlString.append(",");
	    urlString.append(startLongitude);
	    urlString.append("&destination="); //eind positie
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

	        LatLng last = null;
	        for (int i = 0; i < list.size()-1; i++) {
	            LatLng src = list.get(i);
	            LatLng dest = list.get(i+1);
	            last = dest;
	            //Log.d("Last latLng:", last.latitude + ", " + last.longitude );
	            googleMapService.addPolyLine(new PolylineOptions().add( 
	                    new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
	                    .width(6)
	                    .color(Color.BLACK));
	        }

	        //Log.d("Last latLng:", last.latitude + ", " + last.longitude );
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
	
	private void loadRestaurant() {
		Intent intent = getIntent();
		restaurantSelected = (Restaurant) intent.getSerializableExtra(RestaurantListActivity.RESTAURANT_SELECTED);
		gpsLocation = new Location("");
		gpsLocation.setLatitude((Double) intent.getSerializableExtra(RestaurantListActivity.CURRENT_LATITUD));
		gpsLocation.setLongitude((Double) intent.getSerializableExtra(RestaurantListActivity.CURRENT_LONGITUDE));
	}

	private void updateRestaurantView() {
		TextView restaurantNameTextView = (TextView) findViewById(R.id.valueRestaurantName);
		restaurantNameTextView.setText(restaurantSelected.getName());
		
		TextView restaurantAddressTextView = (TextView) findViewById(R.id.valueRestaurantAddress);
		restaurantAddressTextView.setText(restaurantSelected.getAddress());
		
		TextView restaurantPhoneTextView = (TextView) findViewById(R.id.valueRestaurantPhone);
		restaurantPhoneTextView.setText(restaurantSelected.getPhone());
		
		/*TextView restaurantLocationTextView = (TextView) findViewById(R.id.valueRestaurantLocation);
		if (gpsLocation != null)
			restaurantLocationTextView.setText(Double.toString(gpsLocation.getLatitude()));
		else
			restaurantLocationTextView.setText("Ubicacion Nula");*/
	}

    /** Called when the user clicks the Call button */
    public void callButton(View view) {
     
    	TextView restaurantPhoneTextView = (TextView) findViewById(R.id.valueRestaurantPhone);
    	String phone = (String) restaurantPhoneTextView.getText();
    	Intent intent = new Intent(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse("tel:" + phone));

    	startActivity(intent);    	
    }


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Agendar").setIcon(R.drawable.schedule);
	    menu.add(1, 2, 1, "Enviar Mail").setIcon(R.drawable.gmail);
	    
		return true;
	}
	
	@SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     //TextView txt=(TextView)findViewById(R.id.txt);
     switch(item.getItemId())
     {
     case 1:
    	 //Agendar
    	Intent intent = new Intent(Intent.ACTION_INSERT);
     	intent.setData(CalendarContract.Events.CONTENT_URI);
     	intent.putExtra(Events.TITLE, restaurantSelected.getName());
     	intent.putExtra(Events.EVENT_LOCATION, restaurantSelected.getAddress());
     	intent.putExtra(Events.DESCRIPTION, "Tel: " +  restaurantSelected.getPhone());
     	startActivity(intent);
      //txt.setText("you clicked on item "+item.getTitle());
      return true;
     case 2:
    	 //Enviar Mail
     	Intent intentMail = new Intent(Intent.ACTION_SEND);
     	intentMail.setType("text/html");
     	intentMail.putExtra(intentMail.EXTRA_SUBJECT, "Reserva en " + restaurantSelected.getName());
     	intentMail.putExtra(intentMail.EXTRA_TEXT, "Reserve en el restaurant " + restaurantSelected.getName() + ", que queda en " + restaurantSelected.getAddress());
     	startActivity(intentMail);
      //txt.setText("you clicked on item "+item.getTitle());
      return true;

     }
     return super.onOptionsItemSelected(item);

    }

}
