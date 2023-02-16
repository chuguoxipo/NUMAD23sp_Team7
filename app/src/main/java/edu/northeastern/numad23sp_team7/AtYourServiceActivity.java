package edu.northeastern.numad23sp_team7;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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
        });
    }

}
