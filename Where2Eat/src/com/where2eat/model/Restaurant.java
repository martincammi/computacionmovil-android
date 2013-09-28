package com.where2eat.model;

import java.io.Serializable;

public class Restaurant implements Serializable {

	private static final long serialVersionUID = 141386736817914856L;
	
	/* Essential collaborators */
	private final String name;
	private final String address;
	private final String phone;
	private double latitude;
	private double longitude;
	
	public Restaurant(String name, String address, String phone, double latitude, double longitude) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}
	
	public double getLatitude()	{
		return latitude;
	}
	
	public double getLongitude()	{
		return longitude;
	}
}
