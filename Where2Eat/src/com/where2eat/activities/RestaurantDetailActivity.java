package com.where2eat.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.where2eat.R;
import com.where2eat.model.Restaurant;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestaurantDetailActivity extends FragmentActivity {
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static final LatLng CIUDAD_UNIVERSITARIA = new LatLng(-34.541672, -58.442189);
	private Restaurant restaurantSelected;
	private GoogleMap googleMap;
	LatLng currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detalle);
		
		loadRestaurant();
		updateRestaurantView();
		
	    googleMap = getGooleMap();

	    	//Location location = getCurrentLocation();
            //drawMarker(location);
            //updateCurrentLocation();
		
		 if (googleMap != null){
		     
			 //drawBlueMarker(HAMBURG, "Hamburg");
			 //drawMarker(KIEL, "Kiel is cool", getWhere2EatIcon(), "Kiel");
			 //drawMarker(CIUDAD_UNIVERSITARIA, "Exactas is cool", getWhere2EatIcon(), "Ciudad universitaria");
			 
			 LatLng position = getRestaurantSelectedPosition();
			 Marker restaurantSelectedMarker = drawMarker(position, "A comeeer", getWhere2EatIcon() ,restaurantSelected.getName());
			 moveToPositionInGoogleMap(restaurantSelectedMarker);
		   }
		 
	}

	private LatLng getRestaurantSelectedPosition() {
		return new LatLng(restaurantSelected.getLatitude(), restaurantSelected.getLongitude());
	}

	private BitmapDescriptor getWhere2EatIcon() {
		return BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
	}

	/**
	 * Updates the current location every 20 seconds.
	 */
	private void updateCurrentLocation(){
	
	LocationManager locationManager = getLocationManager(); 
	Criteria criteria = new Criteria();
	String provider = locationManager.getBestProvider(criteria, true);
  	LocationListener locationListener = new LocationListener() {
    		
            public void onLocationChanged(Location location) {
            	// redraw the marker when get location update.
            	LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            	drawBlueMarker(latLng, "You are here!");
            }

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
    	};
    	
		locationManager.requestLocationUpdates(provider, 20000, 0, locationListener);
	}
	
	private LocationManager getLocationManager(){
		return (LocationManager) getSystemService(LOCATION_SERVICE);
	}
	
	private Location getCurrentLocation(){
		
		googleMap.setMyLocationEnabled(true);
		
		LocationManager locationManager = getLocationManager();
    	
    	Criteria criteria = new Criteria();
    	
    	String provider = locationManager.getBestProvider(criteria, true);
    	
    	Location location = locationManager.getLastKnownLocation(provider);
    	
    	return location;
	}
	
	private GoogleMap getGooleMap() {
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
		 // Showing status
	    if(status != ConnectionResult.SUCCESS){ // Google Play Services are not available

	        int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	        dialog.show();
	        return null;
	    }else{
	    	SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
	    	
	    	return mapFrag.getMap();
	    }
	}

	private void clearGoogleMaps(){
		googleMap.clear();
	}
	
	private void drawBlueMarker(LatLng latlong, String iconTitle){
		String snippet = "Lat:" + latlong.latitude + "Lng:"+ latlong.longitude;
		BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		drawMarker(latlong, snippet, icon, iconTitle);
	}
	
	private Marker drawMarker(LatLng latlng, String snippet, BitmapDescriptor icon, String iconTitle){
		
		if(latlng != null){
			
			//LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
			//Marker hamburg = googleMap.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
			
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(latlng);
			markerOptions.snippet(snippet);
			markerOptions.icon(icon).title(iconTitle);
			
			Marker marker = googleMap.addMarker(markerOptions);
			
	        return marker;
		}
		
		return null;
	 }
	
	private void moveToPositionInGoogleMap(Marker marker){
		
		LatLng position = marker.getPosition();
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 7));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
        //TextView moveLocation = (TextView) findViewById(R.id.valueRestaurantDirection);
        //moveLocation.setText("Latitude:" +  location.getLatitude() + ", Longitude:"+ location.getLongitude() );
	}
	
	private void updateRestaurantView() {
		TextView restaurantNameTextView = (TextView) findViewById(R.id.valueRestaurantName);
		restaurantNameTextView.setText(restaurantSelected.getName());
		
		TextView restaurantAddressTextView = (TextView) findViewById(R.id.valueRestaurantAddress);
		restaurantAddressTextView.setText(restaurantSelected.getAddress());
		
		TextView restaurantPhoneTextView = (TextView) findViewById(R.id.valueRestaurantPhone);
		restaurantPhoneTextView.setText(restaurantSelected.getPhone());
	}

	private void loadRestaurant() {
		Intent intent = getIntent();
		restaurantSelected = (Restaurant) intent.getSerializableExtra(RestaurantListActivity.RESTAURANT_SELECTED);
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
      //txt.setText("you clicked on item "+item.getTitle());
      return true;

     }
     return super.onOptionsItemSelected(item);

    }


	public void onLocationChanged2(Location location) {
		
		TextView moveLocation = (TextView) findViewById(R.id.valueRestaurantDirection);
		
        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
        moveLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
		
	}



}
