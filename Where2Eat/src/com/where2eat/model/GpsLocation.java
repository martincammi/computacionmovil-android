package com.where2eat.model;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GpsLocation implements LocationListener {
	
	LocationManager locationManager;
	Location gpsLastLocation;
	
	public GpsLocation(LocationManager locationManager)
	{
		this.locationManager = locationManager;
	}
	
	public Boolean isEnabled()
	{
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

	}
	
	public void startProcessingLocation(){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
	}
	
	public Location getLocation()
	{
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
		Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		return gpsLocation;
		//return gpsLastLocation;

	}

	@Override
	public void onLocationChanged(Location location) {
		gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		System.out.println("Nueva ubicación: " + gpsLastLocation == null ? "" : gpsLastLocation.getLongitude() );
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
