package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import edu.northeastern.numad23sp_team7.databinding.FragmentProfileBinding;
import edu.northeastern.numad23sp_team7.huskymarket.utils.PreferenceManager;


public class ProfileFragment extends Fragment {

   private FragmentProfileBinding binding;
   private FirebaseAuth mAuth;
   private PreferenceManager preferenceManager;

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(requireContext());
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
//        binding.buttonSignUp.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), HuskySignupActivity.class);
//            startActivity(i);
//        });
//
//        binding.buttonLogin.setOnClickListener(v -> {
//            Intent i = new Intent(getActivity(), HuskyLoginActivity.class);
//            startActivity(i);
//        });

        binding.imageLogout.setOnClickListener(v -> {
            if(mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                preferenceManager.clear();
                Intent intent = new Intent(getActivity(), HuskyLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
}