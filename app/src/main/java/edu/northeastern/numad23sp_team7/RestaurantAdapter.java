package edu.northeastern.numad23sp_team7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad23sp_team7.model.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {


    private List<Restaurant> restaurantList;

    private Context context;


    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(context).inflate(R.layout.restaurant_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        Restaurant currentRestaurant = restaurantList.get(position);

        holder.restaurantName.setText(currentRestaurant.getName());
        holder.restaurantCategory.setText(currentRestaurant.getCategory());
        holder.restaurantRating.setText(currentRestaurant.getRating());
        Picasso.get().load(currentRestaurant.getImgUrl()).into(holder.restaurantImage);

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void setRestaurantList(List<Restaurant> restaurants) {
        this.restaurantList = restaurants;
    }

}
