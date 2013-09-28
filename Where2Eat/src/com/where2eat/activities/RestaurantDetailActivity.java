package com.where2eat.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.where2eat.R;
import com.where2eat.model.Restaurant;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestaurantDetailActivity extends FragmentActivity {
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static final LatLng CIUDAD_UNIVERSITARIA = new LatLng(-34.541672, -58.442189);
	private Restaurant restaurantSelected;
	private GoogleMap map;
	
	/*@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detalle);
		
		loadRestaurant();
		updateRestaurantView();
		
		View viewLayout = (View) findViewById(R.id.fullscreen_content_controls5);
		
		SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        map = mapFrag.getMap();
		
		//MapFragment aa = (MapFragment) viewLayout.findViewById(R.id.map);
		
//		SupportMapFragment myMapFragment = new SupportMapFragment(){
//		    @Override
//		    public void onActivityCreated(Bundle savedInstanceState){
//		    	getFragmentManager().findFragmentById(R.id.map);
//		    }
//		};
		
		 //map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		 
		 //getFragmentManager().findFragmentByTag(tag)
		 
		 if (map!=null){
		     /* 
			 Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
		      
		      Marker kiel = map.addMarker(new MarkerOptions()
		          .position(KIEL)
		          .title("Kiel")
		          .snippet("Kiel is cool")
		          .icon(BitmapDescriptorFactory
		              .fromResource(R.drawable.ic_launcher)));
		      
		      Marker ciudad = map.addMarker(new MarkerOptions()
	          .position(CIUDAD_UNIVERSITARIA)
	          .title("Ciudad universitaria")
	          .snippet("Exactas is cool")
	          .icon(BitmapDescriptorFactory
	              .fromResource(R.drawable.ic_launcher)));
		      */
			  LatLng position = new LatLng(restaurantSelected.getLatitude(), restaurantSelected.getLongitude());
		      Marker restaurant = map.addMarker(new MarkerOptions()
	          .position(position)
	          .title(restaurantSelected.getName())
	          .snippet("A comeeer")
	          .icon(BitmapDescriptorFactory
	              .fromResource(R.drawable.ic_launcher)));
		      
		      	map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 7));				 
				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
		   }
		 
		 //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
		 
		 //map.moveCamera(CameraUpdateFactory.newLatLngZoom(CIUDAD_UNIVERSITARIA, 7));		 
		  
		
	}

	private void updateRestaurantView() {
		TextView restaurantNameTextView = (TextView) findViewById(R.id.valueRestaurantName);
		restaurantNameTextView.setText(restaurantSelected.getName());
		
		TextView restaurantAddressTextView = (TextView) findViewById(R.id.valueRestaurantAddress);
		restaurantAddressTextView.setText(restaurantSelected.getAddress());
		
		TextView restaurantPhoneTextView = (TextView) findViewById(R.id.valueRestaurantPhone);
		restaurantPhoneTextView.setText(restaurantSelected.getPhone());
	}

	private void loadRestaurant() {
		Intent intent = getIntent();
		restaurantSelected = (Restaurant) intent.getSerializableExtra(RestaurantListActivity.RESTAURANT_SELECTED);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.restaurant_detalle, menu);
		menu.add(1, 1, 0, "Agendar");//.setIcon(R.drawable.bluray);
	    menu.add(1, 2, 1, "Enviar Mail");//.setIcon(R.drawable.dvd);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
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
     	startActivity(intent);
      //txt.setText("you clicked on item "+item.getTitle());
      return true;
     case 2:
    	 //Enviar Mail
      //txt.setText("you clicked on item "+item.getTitle());
      return true;

     }
     return super.onOptionsItemSelected(item);

    }
	
}
