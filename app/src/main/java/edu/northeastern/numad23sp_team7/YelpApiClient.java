package edu.northeastern.numad23sp_team7;

import java.util.ArrayList;
import java.util.List;

public class YelpApiClient {

    public List<Restaurant> getRestaurants(String searchTerm, String location) {

        // Fake return restaurants
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant(
                "Levain Bakery - New York",
                "https://s3-media3.fl.yelpcdn.com/bphoto/DH29qeTmPotJbCSzkjYJwg/o.jpg",
                "Bakeries",
                "4.5");

        Restaurant restaurant2 = new Restaurant(
                "Katz's Delicatessen",
                "https://s3-media1.fl.yelpcdn.com/bphoto/1_2gtvgqMyuSgVJoCP6BQw/o.jpg",
                "Delis",
                "4.0");

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        return restaurants;
    }
}
