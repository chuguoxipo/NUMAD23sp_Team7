package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad23sp_team7.R;
import edu.northeastern.numad23sp_team7.databinding.ActivityChatBinding;
import edu.northeastern.numad23sp_team7.huskymarket.adapter.ChatAdapter;
import edu.northeastern.numad23sp_team7.huskymarket.model.ChatMessage;
import edu.northeastern.numad23sp_team7.huskymarket.model.User;
import edu.northeastern.numad23sp_team7.huskymarket.utils.Constants;
import edu.northeastern.numad23sp_team7.huskymarket.utils.PreferenceManager;


// 从上一个activity 拿到receiver/ receiverId
// 上一个activity 可能是message fragment, 也可能是product detail
//        receiver = (User) getIntent().getSerializableExtra("user");
// or receiverID = getIntent().getString("userID")
public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private User receiver;

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;
    private PreferenceManager preferenceManager;

    private FirebaseFirestore database;

    private static final String TAG = "chat-activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());

        chatMessages = new ArrayList<>();

        loadReceiverName();




        String senderId = preferenceManager.getString(Constants.KEY_USER_ID);
        String senderProfileImage = preferenceManager.getString(Constants.KEY_PROFILE_IMAGE);
        chatAdapter = new ChatAdapter(
                chatMessages,
                decodeProfileImageString(senderProfileImage),
                decodeProfileImageString(receiver.getProfileImage()),
                senderId);
        binding.chatRecyclerView.setAdapter(chatAdapter);


        binding.imageBack.setOnClickListener(v -> onBackPressed());
//        binding.layoutSend.setOnClickListener(v -> sendMessage());



    }

    private void sendMessage() {

    }




    // string -> bitmap
    private Bitmap decodeProfileImageString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String getDateTimeText(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }





    private void loadReceiverName(){
        receiver = (User) getIntent().getSerializableExtra("user");
        binding.textReceiverName.setText(receiver.getUsername());
    }

}