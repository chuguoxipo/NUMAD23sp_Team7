package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.northeastern.numad23sp_team7.R;
import edu.northeastern.numad23sp_team7.databinding.FragmentMessagesBinding;
import edu.northeastern.numad23sp_team7.databinding.FragmentProfileBinding;
import edu.northeastern.numad23sp_team7.huskymarket.model.User;
import edu.northeastern.numad23sp_team7.huskymarket.utils.Constants;
import edu.northeastern.numad23sp_team7.huskymarket.utils.PreferenceManager;

public class MessagesFragment extends Fragment {

    private FragmentMessagesBinding binding;
    private PreferenceManager preferenceManager;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(requireContext());
        // Inflate the layout for this fragment
        binding = FragmentMessagesBinding.inflate(getLayoutInflater());

        binding.buttonChat.setOnClickListener(v -> {

            DocumentReference docRef = database
                    .collection(Constants.KEY_COLLECTION_USERS)
                    .document("D9gtlUubrMYR9UZyCQlc18uAr7r2"); // change to receiverID
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            User receiver = document.toObject(User.class);
                            assert receiver != null;
                            Log.d("message", "onComplete: "+ receiver.getUsername());
                            Intent intent = new Intent(getActivity(), ChatActivity.class);
                            intent.putExtra(Constants.KEY_USER, receiver);
                            startActivity(intent);

                        } else {
                            Log.d("TAG", "No such document");
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });

        });

        return binding.getRoot();
    }
}