package com.where2eat.model;

import java.util.Comparator;



import android.location.Location;

public class SortBasedOnDistance implements Comparator
{
	private Location currentLocation;
	public SortBasedOnDistance(Location currentLocation)
	{
		this.currentLocation = currentLocation;
	}
public int compare(Object o1, Object o2) 
{

    Restaurant dd1 = (Restaurant)o1;
    Restaurant dd2 = (Restaurant)o2;
	//GoogleMapsService googleMapService;
	//googleMapService = new GoogleMapsService(getBaseContext(), this, getSupportFragmentManager(), R.id.map);

    Location resto1 = new Location("");
    resto1.setLongitude(dd1.getLongitude());
    resto1.setLatitude(dd1.getLatitude());
    
    Location resto2 = new Location("");
    resto2.setLongitude(dd2.getLongitude());
    resto2.setLatitude(dd2.getLatitude());
    
    float distance1 = currentLocation.distanceTo(resto1);
    float distance2 = currentLocation.distanceTo(resto2);
    
    return Double.compare(distance1, distance2);
}

}