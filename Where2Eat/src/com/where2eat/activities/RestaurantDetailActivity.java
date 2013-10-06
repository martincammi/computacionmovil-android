package com.where2eat.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
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
			 currentMarker = googleMapService.drawMarker( currentPosition, "", getUserIcon(), "Mi ubicación");
			 googleMapService.moveToPositionInGoogleMap(currentMarker);
        	
        	
			 LatLng position = getRestaurantSelectedPosition();
			 restoMarker = googleMapService.drawMarker( position, "A comeeer", getWhere2EatIcon(), restaurantSelected.getName());
			 googleMapService.moveToPositionInGoogleMap(restoMarker);
			 //googleMapService.addPolyLine(new PolylineOptions().add(currentPosition,position).geodesic(true));
		}else{
	    	Toast.makeText( getApplicationContext(), "Map not available", Toast.LENGTH_SHORT ).show();
	    }
		 
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.restaurant_detalle, menu);
		menu.add(1, 1, 0, "Agendar");//.setIcon(R.drawable.bluray);
	    menu.add(1, 2, 1, "Enviar Mail");//.setIcon(R.drawable.dvd);
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
