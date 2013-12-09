package com.where2eat.model;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RestaurantListAdapter extends ArrayAdapter<Restaurant> {

    private final Context context;
    private final List<Restaurant> Ids;
    private final int rowResourceId;
    private Location currentLocation;

    public RestaurantListAdapter(Context context, int textViewResourceId, List<Restaurant> objects, Location currentLocation) {

        super(context, textViewResourceId, objects);

        this.context = context;
        this.Ids = objects;
        this.rowResourceId = textViewResourceId;
        this.currentLocation = currentLocation;
    }
    
    public void setCurrentLocation(Location location){
    	this.currentLocation = location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	View row = convertView;
        RestaurantHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(rowResourceId, parent, false);
            
            holder = new RestaurantHolder();
            holder.restoTypeFoodImg = (ImageView)row.findViewById(com.where2eat.R.id.restoImageView);
            holder.restoName = (TextView)row.findViewById(com.where2eat.R.id.restoNameView);
            holder.restoDistance = (TextView)row.findViewById(com.where2eat.R.id.restoDistanceView);
            
            row.setTag(holder);
        }
        else
        {
            holder = (RestaurantHolder)row.getTag();
        }
        
        Restaurant resto = Ids.get(position);
        holder.restoName.setText(resto.getName());
        switch (resto.getFoodType())
        {
        case AMERICANA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_americana);
        	break;
        case CHINA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_china);
        	break;
        case COMIDARAPIDA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_comidarapida);
        	break;
        case ITALIANA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_italiana);
        	break;
        case MEXICANA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_mexicana);
        	break;
        case PARRILLA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_parrilla);
        	break;
        case PASTA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_pasta);
        	break;
        case PERUANA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_peruana);
        	break;
        case PIZZERIA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_pizza);
        	break;
        case PIZZA:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_pizza);
        	break;
        case SUSHI:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_sushi);
        	break;
        case TENEDORLIBRE:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_tenedorlibre);
        	break;
        default:
        	holder.restoTypeFoodImg.setImageResource(com.where2eat.R.drawable.type_generic);
        } 
        Location restoLocation = new Location("");
        restoLocation.setLatitude(resto.getLatitude());
        restoLocation.setLongitude(resto.getLongitude());
        String distance = Integer.toString((int) currentLocation.distanceTo(restoLocation)) + " m.";
        holder.restoDistance.setText(distance);
        
        return row;
    }
    
    static class RestaurantHolder
    {
        ImageView restoTypeFoodImg;
        TextView restoName;
        TextView restoDistance;
    }

}