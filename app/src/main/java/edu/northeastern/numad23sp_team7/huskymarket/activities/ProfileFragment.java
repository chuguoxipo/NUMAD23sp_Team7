package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.northeastern.numad23sp_team7.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

   private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        binding.buttonSignUp.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), HuskySignupActivity.class);
            startActivity(i);
        });

        binding.buttonLogin.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), HuskyLoginActivity.class);
            startActivity(i);
        });
        return binding.getRoot();
    }
}