package edu.northeastern.numad23sp_team7.huskymarket.database;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.function.Consumer;
import edu.northeastern.numad23sp_team7.huskymarket.model.Product;

public class DatabaseClient {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference productsRef = db.collection("products");
    private final static String TAG = "Database Client";

    public void getAllProducts(final Consumer<ArrayList<Product>> callback) {
        ArrayList<Product> products = new ArrayList<>();
        productsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        products.add(product);
                    }

                    callback.accept(products);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void addProducts(ArrayList<Product> products) {
        for (Product product : products) {
            productsRef.add(product)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "Product added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding product", e));
        }
    }
}
