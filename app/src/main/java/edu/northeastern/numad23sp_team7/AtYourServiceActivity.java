package edu.northeastern.numad23sp_team7;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad23sp_team7.model.Restaurant;
import edu.northeastern.numad23sp_team7.service.YelpApiClient;


public class AtYourServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";


    private Button searchButton;
    private EditText inputText;
    private Spinner spinnerLocation;
    private List<String> locations = new ArrayList<>();
    private YelpApiClient apiClient;
    private List<Restaurant> restaurants = new ArrayList<>();
    private Handler handler;


    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        searchButton = findViewById(R.id.search_button);
        apiClient = new YelpApiClient();
        spinnerLocation = findViewById(R.id.spinner);
        locations.add("Seattle");
        locations.add("New York City");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,locations);
        spinnerLocation.setAdapter(adapter);

        String searchTerm = ""; // need to change to real input
        String location = "NYC";  // need to change to real input

        recyclerView = findViewById(R.id.recycler_view);
        rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);
        restaurantAdapter = new RestaurantAdapter(restaurants, this);
        recyclerView.setAdapter(restaurantAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "term:" + searchTerm);
                NetworkThread networkThread = new NetworkThread(searchTerm, location);
                new Thread(networkThread).start();
            }
        });


    }


    private class NetworkThread implements Runnable {
        private String searchTerm;
        private String location;

        public NetworkThread(String searchTerm, String location) {
            this.searchTerm = searchTerm;
            this.location = location;
        }

        @Override
        public void run() {
            // change searchTerm and location to inputs from EditText and Spinner
            restaurants = apiClient.getRestaurants(searchTerm, location);

            Log.d(TAG, "location:" + location);
            Log.d(TAG, "r size:" + restaurants.size());
            EditText inputText = findViewById(R.id.search_plate);
            inputText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }
            });


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    restaurantAdapter.setRestaurantList(restaurants);
                    restaurantAdapter.notifyDataSetChanged();
                }
            });

        }


    }
    private void filter(String text) {
        ArrayList<Restaurant> filteredList = new ArrayList<>();

        for (Restaurant restaurantItem : restaurants) {
            if (restaurantItem.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(restaurantItem);
            }
        }

    }
}
