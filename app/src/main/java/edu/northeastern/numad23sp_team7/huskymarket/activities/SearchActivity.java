package edu.northeastern.numad23sp_team7.huskymarket.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.northeastern.numad23sp_team7.databinding.ActivitySearchBinding;
import edu.northeastern.numad23sp_team7.huskymarket.adapter.SearchResultAdapter;
import edu.northeastern.numad23sp_team7.huskymarket.database.DatabaseClient;
import edu.northeastern.numad23sp_team7.huskymarket.model.Product;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    private SearchResultAdapter searchResultAdapter;
    private ArrayList<Product> products = new ArrayList<>();
    private FirebaseFirestore db;
    private static final String TAG = "Husky Search";
    private Spinner spinner;
    private static final DatabaseClient dbClient = new DatabaseClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeData();
//        dbClient.addProducts(products);
        dbClient.getAllProducts(products -> {
            searchResultAdapter = new SearchResultAdapter(products);
            binding.recyclerViewHuskySearchResult.setAdapter(searchResultAdapter);
        });

//        binding.editTextHuskySearchBox.setOnEditorActionListener((textView, actionId, keyEvent) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                performSearch();
//                return true;
//            }
//
//            return false;
//        });
    }

    private void initializeData() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setDescription("Product " + i);
            product.setPostUderId("User " + i);
            product.setLocation("Location " + i);
            product.setCondition((float) (Math.random() * 5));
            product.setCategory("Category " + i);
            product.setColor("Color " + i);
            product.setImages(Arrays.asList("image1", "image2", "image3"));
            product.setMaterial("Material " + i);
            product.setStatus("Status " + i);
            product.setPrice((float) (Math.random() * 100));
            products.add(product);
        }
    }
}