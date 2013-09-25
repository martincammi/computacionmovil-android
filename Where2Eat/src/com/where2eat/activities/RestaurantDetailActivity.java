package com.where2eat.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.where2eat.R;
import com.where2eat.model.Restaurant;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestaurantDetailActivity extends FragmentActivity {
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	private Restaurant restaurantSelected;
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_detalle);
		
		loadRestaurant();
		updateRestaurantView();
		
		 map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		 
//		 if (map!=null){
//		      Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
//		          .title("Hamburg"));
//		      Marker kiel = map.addMarker(new MarkerOptions()
//		          .position(KIEL)
//		          .title("Kiel")
//		          .snippet("Kiel is cool")
//		          .icon(BitmapDescriptorFactory
//		              .fromResource(R.drawable.ic_launcher)));
//		   }
		
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
		getMenuInflater().inflate(R.menu.restaurant_detalle, menu);
		return true;
	}

}
