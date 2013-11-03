package com.where2eat.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.where2eat.R;
import com.where2eat.controllers.ButtonsController;
import com.where2eat.controllers.RestaurantListController;
import com.where2eat.model.Restaurant;
import com.where2eat.services.AmazonServerRestaurantService;
import com.where2eat.services.ExampleRestaurantService;
import com.where2eat.services.LocalServerRestaurantService;
import com.where2eat.services.OleoServerRestaurantService;

public class RestaurantListActivity extends ActionBarActivity {

	public final static String RESTAURANT_SELECTED = "com.where2eat.restaurantListActivity.RESTAURANT";
	public final static String CURRENT_LATITUD = "com.where2eat.restaurantListActivity.CURRENT_LATITUD";
	public final static String CURRENT_LONGITUDE = "com.where2eat.restaurantListActivity.CURRENT_LONGITUDE";
	public final static String EXTRA_MESSAGE = "com.where2eat.restaurantListActivity.MESSAGE";
	
	//Model View Controller
	private List<Restaurant> restaurants = new ArrayList<Restaurant>();
	private ListView listView;
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
        // Inflate the menu; this adds items to the action bar if it is present.
    	 MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            
            @Override
            public boolean onQueryTextSubmit(String query) {
            	
            	//controller.updateRestaurants(query);
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
	
    public void httpClientService(){
    	try {
    	avoidConnectionRestriction();
    		
    	HttpClient httpclient = new DefaultHttpClient();
    	//HttpPost httppost = new HttpPost("http://searchit.no-ip.info:8080/david/");
    	
    	//String url = "http://searchit.no-ip.info:8080/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
    	String url = "http://localhost:8081/androidServices/login?webservice=true&service=restaurantServlet&name=hola&cooking=parrilla&username=USER&password=123";
    	 
    	HttpGet httpget = new HttpGet(url);
    	
    	//httppost.setEntity(new StringEntity(postData));
    	//httppost.setHeader("host", "http://searchit.no-ip.info:8080/david/");
		//HttpResponse response = httpclient.execute(httppost);
    	 HttpResponse response = httpclient.execute(httpget);
    	 HttpEntity entity = response.getEntity();
    	 
    	 if (entity != null) {

             // A Simple JSON Response Read
             InputStream instream = entity.getContent();
             String result = convertStreamToString(instream);
             // now you have the string representation of the HTML request
             instream.close();
             
             generateNewRestaurants(result);
             
         }

    	} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			generateNewRestaurants(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			generateNewRestaurants(e.getMessage());
		}
    }
    
    public void AndoridHttpClientService(){
//    	try {
//    	AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
//	    HttpGet request = new HttpGet("http://searchit.no-ip.info:8080/david/");   
//			HttpResponse response = client.execute(request);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
	    //response.getEntity().writeTo(new FileOutputStream(f));
    }
    
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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
    
    private void generateNewRestaurants(String value){
    	String[] array = {value};
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
  	
  	  	ListView listView = (ListView) findViewById(R.id.rest_list_view);
  	  	listView.setAdapter(adapter);
    }
    
}

