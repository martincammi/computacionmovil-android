package com.where2eat.services;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Service to interact with the google maps.
 * @author martincammi
 *
 */
public class GoogleMapsService {
	
	private Context context;
	private FragmentActivity fragmentActivity;
	private FragmentManager fragmentManager;
	private int mapId;
	private GoogleMap googleMap;
	
	
	public GoogleMapsService(Context context, FragmentActivity fragmentActivity, FragmentManager fragmentManager, int mapId) {
		this.context = context;
		this.fragmentActivity = fragmentActivity;
		this.fragmentManager = fragmentManager;
		this.mapId = mapId;
		this.googleMap = getGoogleMap();
	}

	private GoogleMap getGoogleMap() {
		
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
		
		 // Showing status
	    if(status != ConnectionResult.SUCCESS){ // Google Play Services are not available

	        int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, fragmentActivity, requestCode);
	        dialog.show();
	        return null;
	    }else{
	    	Log.d("Location Updates", "Google Play services is available.");
	    	SupportMapFragment mapFrag = (SupportMapFragment) getFragmentManager().findFragmentById(mapId);
	    	return mapFrag.getMap();
	    }
	}
	
	public boolean servicesConnected(){
		  return googleMap != null;
	}
	
	public Location getCurrentLocation(){
		
		googleMap.setMyLocationEnabled(true);
		
		LocationManager locationManager = getLocationManager();
    	
    	Criteria criteria = new Criteria();
    	
    	String provider = locationManager.getBestProvider(criteria, true);
    	
    	Location location = locationManager.getLastKnownLocation(provider);
    	
    	return location;
	}
	
	/**
	 * Updates the current location every 20 seconds.
	 */
	public void updateCurrentLocation(LocationListener locationListener){
	
	LocationManager locationManager = getLocationManager(); 
	Criteria criteria = new Criteria();
	String provider = locationManager.getBestProvider(criteria, true);
//  	LocationListener locationListener = new LocationListener() {
//    		
//            public void onLocationChanged(Location location) {
//            	// redraw the marker when get location update.
//            	LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//            	drawBlueMarker(latLng, "You are here!");
//            }
//
//			@Override
//			public void onProviderDisabled(String provider) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onProviderEnabled(String provider) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onStatusChanged(String provider, int status,
//					Bundle extras) {
//				// TODO Auto-generated method stub
//				
//			}
//    	};
    	
		locationManager.requestLocationUpdates(provider, 10000, 0, locationListener);
	}
	
	public void drawBlueMarker(LatLng latlong, String iconTitle){
		String snippet = "Lat:" + latlong.latitude + "Lng:"+ latlong.longitude;
		BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
		drawMarker(latlong, snippet, icon, iconTitle);
	}
	
	public Marker drawMarker(LatLng latlng, String snippet, BitmapDescriptor icon, String iconTitle){
		
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
	
	private LocationManager getLocationManager(){
		return (LocationManager) fragmentActivity.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public void moveToPositionInGoogleMapWithEffect(Marker marker){
		
		LatLng position = marker.getPosition();
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 7));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); 
        //TextView moveLocation = (TextView) findViewById(R.id.valueRestaurantDirection);
        //moveLocation.setText("Latitude:" +  location.getLatitude() + ", Longitude:"+ location.getLongitude() );
	}
	
	public void moveToPositionInGoogleMap(Marker marker){
		
		LatLng position = marker.getPosition();
		CameraPosition cameraPosition = new CameraPosition.Builder()
	    .target(position)
	    .zoom(13)                   
	    .build();                   
	    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}
	
	public static LatLng getLatLng(Location location){
		double latitude = location.getLatitude();
	    double longitude = location.getLongitude();
	 
	    LatLng coordinates = new LatLng(latitude, longitude);
	    return coordinates;
	}
	
	public static Location getLocation(LatLng latLng){
	 
	    Location location = new Location("");
	    location.setLatitude(latLng.latitude);
	    location.setLongitude(latLng.longitude);
	    
	    return location;
	}
	
	public void addPolyLine(PolylineOptions options){
		googleMap.addPolyline(options);
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public FragmentManager getFragmentManager() {
		return fragmentManager;
	}

	public void setFragmentManager(FragmentManager fragmentManager) {
		this.fragmentManager = fragmentManager;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}
	
	
}
