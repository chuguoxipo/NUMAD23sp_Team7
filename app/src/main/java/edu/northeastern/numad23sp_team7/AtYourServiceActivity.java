package edu.northeastern.numad23sp_team7;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad23sp_team7.model.Restaurant;
import edu.northeastern.numad23sp_team7.service.YelpApiClient;


public class AtYourServiceActivity extends AppCompatActivity {

    private static final String TAG = "WebServiceActivity";

    private EditText mURLEditText;
    private TextView mTitleTextView;
    private Button button;
    private YelpApiClient apiClient;
    private List<Restaurant> restaurants;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);

        button = findViewById(R.id.search_button);
        apiClient = new YelpApiClient();
        restaurants = new ArrayList<>();
        handler = new Handler();

        button.setOnClickListener(view -> {
            Thread newThread = new Thread(() -> {
                restaurants = apiClient.getRestaurants("", "");
                handler.post(() -> {
                    // Populate the recycler view
                });
            });

            newThread.start();
        });
    }

}
