package edu.northeastern.numad23sp_team7;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    public ImageView restaurantImage;
    public TextView restaurantName;
    public TextView restaurantCategory;
    public TextView restaurantRating;

    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);

        restaurantImage = itemView.findViewById(R.id.restaurant_img);
        restaurantName = itemView.findViewById(R.id.restaurant_name);
        restaurantCategory = itemView.findViewById(R.id.restaurant_category);
        restaurantRating = itemView.findViewById(R.id.restaurant_rating);
    }
}
