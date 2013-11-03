package com.where2eat.model;

import java.util.List;
import java.util.Observable;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.where2eat.services.PositionsService;

public class GpsLocation extends Observable implements LocationListener {
	
	LocationManager locationManager;
	Location gpsLastLocation;
	Location networkLastLocation;
	Location defaultLocation;
	
	
	public GpsLocation(LocationManager locationManager)
	{
		this.locationManager = locationManager;
		this.defaultLocation = new Location("");
		defaultLocation.setLongitude(PositionsService.CABILDO_Y_JURAMENTO.longitude);
		defaultLocation.setLatitude(PositionsService.CABILDO_Y_JURAMENTO.latitude);
	}
	
	public Boolean isEnabled()
	{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}
	
	public List<String> getCurrentProviders(){
		return locationManager.getAllProviders();
	}
	
	public Boolean foundNewLocation(){
		return !getDefaultLocation().equals(getLocation());
	}
	
	public void startProcessingLocation2(int updateLapseTimeInMilliseconds){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateLapseTimeInMilliseconds, 0, this);
		gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
	}
	
	public void startSearch(){
		
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			networkLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
	}
	
	public void startProcessingLocation(int updateLapseTimeInMilliseconds){
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateLapseTimeInMilliseconds, 0, this);
			gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, updateLapseTimeInMilliseconds, 0, this);
			networkLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
	}
	
	public void stopProcessingLocation(){
		locationManager.removeUpdates(this);
	}
	
	/**
	 * Gets the last known location from the best provider.
	 */
	public Location updateLocation() {
     // Get the location manager
		
     Criteria criteria = new Criteria();
     String bestProvider = locationManager.getBestProvider(criteria, false);
     Location location = locationManager.getLastKnownLocation(bestProvider);
     if(location == null){
    	 gpsLastLocation = defaultLocation;
     }else{
    	 gpsLastLocation = location;
     }
     Double lat,lon;
     
     try {
       lat = location.getLatitude ();
       lon = location.getLongitude ();
       new LatLng(lat, lon);
     }
     catch (NullPointerException e){
         e.printStackTrace();
       return null;
     }
     
     return gpsLastLocation;
    		 
    }
	
	public Location getLocation()
	{
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
		//Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//return gpsLocation;
		
		if(gpsLastLocation == null && networkLastLocation == null){
			return defaultLocation;
		} else if(networkLastLocation == null ){
			return gpsLastLocation;
		}
		return networkLastLocation;
	}
	
	public Location getDefaultLocation(){
		return defaultLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
		
		if(location != null){
			
			if(LocationManager.GPS_PROVIDER.equals(location.getProvider())){
				gpsLastLocation = location;
			}
			
			if(LocationManager.NETWORK_PROVIDER.equals(location.getProvider())){
				networkLastLocation = location;
			}
		
		//gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			setChanged();
			notifyObservers();
			System.out.println("Notifying observer");
		}
		//System.out.println("Nueva ubicación: " + gpsLastLocation == null ? "" : gpsLastLocation.getLongitude() );
		
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
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

}
