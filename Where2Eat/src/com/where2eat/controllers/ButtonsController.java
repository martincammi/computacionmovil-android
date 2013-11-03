package com.where2eat.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.where2eat.R;

public class ButtonsController implements Controller {

	
	//Model View Activity
	private final Activity activity;
	private final RestaurantListController controller;

	
	public ButtonsController(Activity activity, RestaurantListController controller){
		this.activity = activity;
		this.controller = controller;
	}
	
	public void initialize(){
		
	     Button buttonSearch = (Button) activity.findViewById(R.id.button_search);
	     
	     buttonSearch.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText( activity.getApplicationContext(), "Buscando ubicación actual", Toast.LENGTH_SHORT ).show();
	            	controller.startSearch("");
	            }
	     });
	     
	     Button buttonScan = (Button) activity.findViewById(R.id.button_scan);
	     
	     buttonScan.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText( activity.getApplicationContext(), "Actualizando ubicación actual", Toast.LENGTH_SHORT ).show();
	            	controller.startScan();
	            }
	     });
	     
	     Button buttonStop = (Button) activity.findViewById(R.id.button_stop);
	     
	     buttonStop.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	Toast.makeText( activity.getApplicationContext(), "Cancelando escaneo", Toast.LENGTH_SHORT ).show();
	            	controller.stopScan();
	            }
	     });
	}
	
}
