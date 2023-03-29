package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import edu.northeastern.numad23sp_team7.databinding.FragmentSearchBinding;
import edu.northeastern.numad23sp_team7.huskymarket.adapter.SearchResultAdapter;
import edu.northeastern.numad23sp_team7.huskymarket.model.Product;

public class SearchFragment extends Fragment {
    private FragmentSearchBinding fragmentSearchBinding;
    private SearchResultAdapter searchResultAdapter;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<String> categoryArray = new ArrayList<>();

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentSearchBinding = FragmentSearchBinding.inflate(getLayoutInflater());
        initializeData();
        searchResultAdapter = new SearchResultAdapter(products);
        fragmentSearchBinding.recyclerViewHuskySearchResult.setAdapter(searchResultAdapter);

        return fragmentSearchBinding.getRoot();
    }

    private void initializeData() {

//        Product product1 = new Product()
        Product product1 = new Product("", "View binding is a feature introduced in Android Studio 3.6 that generates a binding class for each XML layout file in your app. This binding class allows you to access views in your layout files directly in your code, without the need for findViewById() calls. View binding simplifies your code and helps r", "", "", 0.1f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product2 = new Product("", "2", "", "", 0.2f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product3 = new Product("", "3", "", "", 0.3f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product4 = new Product("", "4", "", "", 0.4f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product5 = new Product("", "5", "", "", 0.5f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product6 = new Product("", "6", "", "", 0.6f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product7 = new Product("", "1View binding is a feature introduced in Android Studio 3.6 that generates a binding class for each XML layout file in your app. This binding class allows you to access views in your layout files directly in your code, without the need for findViewById() calls. View binding simplifies your code and helps r", "", "", 0.1f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product8 = new Product("", "2", "", "", 0.2f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product9 = new Product("", "3", "", "", 0.3f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product10 = new Product("", "4", "", "", 0.4f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product11 = new Product("", "5", "", "", 0.5f, "", "", new ArrayList<>(), "", "", 1.0f);
        Product product12 = new Product("", "6", "", "", 0.6f, "", "", new ArrayList<>(), "", "", 1.0f);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        products.add(product11);
        products.add(product12);
    }
}