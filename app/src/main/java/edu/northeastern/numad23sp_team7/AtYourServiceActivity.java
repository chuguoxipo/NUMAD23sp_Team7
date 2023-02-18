package edu.northeastern.numad23sp_team7;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


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
    private Handler handler = new Handler();
    private String location;
    private ProgressBar progressBar;


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
        locations.add("San Francisco");
        locations.add("Boston");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,locations);
        spinnerLocation.setAdapter(adapter);

        recyclerView = findViewById(R.id.recycler_view);
        rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);
        restaurantAdapter = new RestaurantAdapter(restaurants, this);
        recyclerView.setAdapter(restaurantAdapter);
        inputText = findViewById(R.id.search_plate);
        progressBar = findViewById(R.id.progressBar);

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }

                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected location from the spinner
                String selectedLocation = parent.getItemAtPosition(position).toString();

                // Set the location variable to the selected location
                location = selectedLocation;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void performSearch() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
        String searchTerm = inputText.getText().toString();
        Log.d(TAG, "term:" + searchTerm);
        NetworkThread networkThread = new NetworkThread(searchTerm, location);
        new Thread(networkThread).start();
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
            handler.post(() -> {
                recyclerView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            });
            restaurants = apiClient.getRestaurants(searchTerm, location);
            handler.post(() -> {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            });

            Log.d(TAG, "location:" + location);
            Log.d(TAG, "r size:" + restaurants.size());




            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String query = searchTerm.toLowerCase();
                    ArrayList<Restaurant> filteredList = new ArrayList<>();
                    for (Restaurant restaurantItem : restaurants) {
                        Log.d(TAG, "restaurantItem:" + restaurantItem.getName());
                        if (restaurantItem.getName().toLowerCase().contains(query.toLowerCase())) {
                            filteredList.add(restaurantItem);
                        }
                    }
                    restaurantAdapter.setRestaurantList(filteredList);
                    restaurantAdapter.notifyDataSetChanged();
                }
            });

        }

    }
}