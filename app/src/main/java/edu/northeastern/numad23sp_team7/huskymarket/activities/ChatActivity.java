package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import edu.northeastern.numad23sp_team7.R;
import edu.northeastern.numad23sp_team7.databinding.ActivityChatBinding;
import edu.northeastern.numad23sp_team7.huskymarket.model.ChatMessage;
import edu.northeastern.numad23sp_team7.huskymarket.model.User;
import edu.northeastern.numad23sp_team7.huskymarket.utils.PreferenceManager;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private User receiverUser;
    private List<ChatMessage> chatMessages;
    private PreferenceManager preferenceManager;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageBack.setOnClickListener(v -> onBackPressed());


    }
}