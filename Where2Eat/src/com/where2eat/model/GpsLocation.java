package com.where2eat.model;

import com.where2eat.services.PositionsService;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GpsLocation implements LocationListener {
	
	LocationManager locationManager;
	Location gpsLastLocation;
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
	public void startProcessingLocation(int updateLapseTimeInMilliseconds){
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updateLapseTimeInMilliseconds, 0, this);
		gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	
	public Location getLocation()
	{
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
		//Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		//return gpsLocation;
		
		if(gpsLastLocation == null){
			return defaultLocation;
		}
		return gpsLastLocation;
	}
	
	public Location getDefaultLocation(){
		return defaultLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
		gpsLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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
