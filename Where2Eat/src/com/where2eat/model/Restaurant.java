package com.where2eat.model;

import java.io.Serializable;

public class Restaurant implements Serializable {

	private static final long serialVersionUID = 141386736817914856L;
	
	/* Essential collaborators */
	private final String name;
	private final String address;
	private final String phone;
	
	public Restaurant(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
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
	
}
