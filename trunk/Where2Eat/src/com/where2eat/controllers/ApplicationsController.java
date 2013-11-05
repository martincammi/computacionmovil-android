package com.where2eat.controllers;

import com.where2eat.model.Restaurant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.MenuItem;

public class ApplicationsController {

	//Model View Activity

	//Model 
	private Restaurant restaurantSelected;
	
	//View 
	
	//Activity
	private Activity activity;
	
	public ApplicationsController(Restaurant restaurantSelected, Activity activity){
		this.activity = activity;
		this.restaurantSelected = restaurantSelected;
	}
	
	@SuppressLint("NewApi")
	public Boolean selectItem(MenuItem item){
		
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
	     	activity.startActivity(intent);
	      //txt.setText("you clicked on item "+item.getTitle());
	      return true;
	     case 2:
	    	 //Enviar Mail
	     	Intent intentMail = new Intent(Intent.ACTION_SEND);
	     	intentMail.setType("text/html");
	     	intentMail.putExtra(Intent.EXTRA_SUBJECT, "Reserva en " + restaurantSelected.getName());
	     	intentMail.putExtra(Intent.EXTRA_TEXT, "Reserve en el restaurant " + restaurantSelected.getName() + ", que queda en " + restaurantSelected.getAddress());
	     	activity.startActivity(intentMail);
	      //txt.setText("you clicked on item "+item.getTitle());
	      return true;

	     }
	     
	     return false;
	}
}
