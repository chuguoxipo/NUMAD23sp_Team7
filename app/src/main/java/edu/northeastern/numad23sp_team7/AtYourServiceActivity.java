package edu.northeastern.numad23sp_team7;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        recyclerView = findViewById(R.id.recycler_view);
        rLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rLayoutManager);
        restaurantAdapter = new RestaurantAdapter(restaurants, this);
        recyclerView.setAdapter(restaurantAdapter);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkThread networkThread = new NetworkThread();
                new Thread(networkThread).start();
            }
        });



    }


    private class NetworkThread implements Runnable {
        @Override
        public void run() {
            // change searchTerm and location to inputs form editText and spinner
           restaurants = apiClient.getRestaurants("cake", "NYC");
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   restaurantAdapter.setRestaurantList(restaurants);
                   restaurantAdapter.notifyDataSetChanged();
               }
           });

        }


    }

}
