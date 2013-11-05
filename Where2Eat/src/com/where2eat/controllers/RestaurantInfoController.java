package com.where2eat.controllers;

import com.where2eat.R;
import com.where2eat.model.Restaurant;

import android.app.Activity;
import android.widget.TextView;

public class RestaurantInfoController {

	//Model View Activity
	
	//Model 
	private Restaurant restaurantSelected;
	
	//View
	//Directly taken from the Controller.
	
	//Activity
	private Activity activity;
	
	public RestaurantInfoController(Restaurant restaurantSelected, Activity activity){
		this.activity = activity;
		this.restaurantSelected = restaurantSelected;
	}
	
	public void initialize(){
		updateRestaurantView();
	}
	
	private void updateRestaurantView() {
		TextView restaurantNameTextView = (TextView) activity.findViewById(R.id.valueRestaurantName);
		restaurantNameTextView.setText(restaurantSelected.getName());
		
		TextView restaurantAddressTextView = (TextView) activity.findViewById(R.id.valueRestaurantAddress);
		restaurantAddressTextView.setText(restaurantSelected.getAddress());
		
		TextView restaurantPhoneTextView = (TextView) activity.findViewById(R.id.valueRestaurantPhone);
		restaurantPhoneTextView.setText(restaurantSelected.getPhone());
	}
}
