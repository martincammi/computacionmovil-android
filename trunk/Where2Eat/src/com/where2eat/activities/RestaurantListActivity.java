package com.where2eat.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.where2eat.R;
import com.where2eat.controllers.ButtonsController;
import com.where2eat.controllers.RestaurantListController;
import com.where2eat.model.Restaurant;

public class RestaurantListActivity extends ActionBarActivity {

	public final static String RESTAURANT_SELECTED = "com.where2eat.restaurantListActivity.RESTAURANT";
	public final static String CURRENT_LATITUD = "com.where2eat.restaurantListActivity.CURRENT_LATITUD";
	public final static String CURRENT_LONGITUDE = "com.where2eat.restaurantListActivity.CURRENT_LONGITUDE";
	public final static String EXTRA_MESSAGE = "com.where2eat.restaurantListActivity.MESSAGE";
	
	//Model View Controller
	
	//Model
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	
	//View
	private ListView listView;
	
	//Controller
	private RestaurantListController controller;
	private ButtonsController buttonsController;
	
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        
        restaurants = new ArrayList<Restaurant>();
        listView = (ListView) findViewById(R.id.rest_list_view);
        controller = new RestaurantListController(restaurants, listView, this);
        buttonsController = new ButtonsController(this, controller);
        controller.initialize();
        buttonsController.initialize();
    }

    @SuppressLint("NewApi")
	private void goToCreateEventInAgenda() {
		Intent intent = new Intent(Intent.ACTION_INSERT);
    	intent.setData(CalendarContract.Events.CONTENT_URI);
    	intent.putExtra(Events.TITLE, "Cena en Wendy's");
    	intent.putExtra(Events.EVENT_LOCATION, "Cabildo 1900");
    	startActivity(intent);
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            
            @Override
            public boolean onQueryTextSubmit(String query) {
            	
            	controller.startSearch(query);
            	
                return true;
            }
            
        	@Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };
        
        searchView.setOnQueryTextListener(queryTextListener);
        
        return super.onCreateOptionsMenu(menu);
    }
	
    
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void avoidConnectionRestriction(){
    	
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    	      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	      StrictMode.setThreadPolicy(policy);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	
    	 switch (item.getItemId()) {
	         case R.id.action_search:
	             return true;
//	         case R.id.action_example_server:
//	        	 this.controller.setRestaurantService(new ExampleRestaurantService());
//	             return true;
//	         case R.id.action_local_server:
//	        	 this.controller.setRestaurantService(new LocalServerRestaurantService());
//	             return true;
//	         case R.id.action_amazon_server:
//	        	 this.controller.setRestaurantService(new AmazonServerRestaurantService());
//	             return true;
//	         case R.id.action_oleo_server:
//	        	 this.controller.setRestaurantService(new OleoServerRestaurantService());
//	             return true;
    	 }
    	 
    	 return super.onOptionsItemSelected(item);

    }
    
//	TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
//  String n = tm.getLine1Number();
    
}

