package edu.northeastern.numad23sp_team7.huskymarket.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import edu.northeastern.numad23sp_team7.databinding.ActivityChatBinding;
import edu.northeastern.numad23sp_team7.huskymarket.adapter.ChatAdapter;
import edu.northeastern.numad23sp_team7.huskymarket.model.ChatMessage;
import edu.northeastern.numad23sp_team7.huskymarket.model.User;
import edu.northeastern.numad23sp_team7.huskymarket.utils.Constants;
import edu.northeastern.numad23sp_team7.huskymarket.utils.PreferenceManager;


public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private User receiver;
    private String senderId;
    private String senderProfileImage;

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


        // TODO connect with other fragments here: message fragment, as well as product detail
        // get receiver from former activity
        receiver = (User) getIntent().getSerializableExtra("user");
        binding.textReceiverName.setText(receiver.getUsername());

        // init
        database = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        chatMessages = new ArrayList<>();
        senderId = preferenceManager.getString(Constants.KEY_USER_ID);
        senderProfileImage = preferenceManager.getString(Constants.KEY_PROFILE_IMAGE);
        chatAdapter = new ChatAdapter(
                chatMessages,
                decodeProfileImageString(senderProfileImage),
                decodeProfileImageString(receiver.getProfileImage()),
                senderId);
        binding.chatRecyclerView.setAdapter(chatAdapter);




        // click back and go back to message fragment
        binding.imageBack.setOnClickListener(v -> onBackPressed());

        // click send and write new message to database
        binding.layoutSend.setOnClickListener(v -> sendMessage());

        // listen to chat collection change -> view
        // current user as sender
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, senderId)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, receiver.getId())
                .addSnapshotListener(eventListener);
        // current user as receiver
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_SENDER_ID, receiver.getId())
                .whereEqualTo(Constants.KEY_RECEIVER_ID, senderId)
                .addSnapshotListener(eventListener);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(senderId, receiver.getId(), binding.inputMessage.getText().toString(), new Date());
        database.collection(Constants.KEY_COLLECTION_CHAT).add(chatMessage);
        binding.inputMessage.setText(null);
    }


    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            int count = chatMessages.size();
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(documentChange.getDocument().getString(Constants.KEY_SENDER_ID));
                    chatMessage.setReceiverId(documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID));
                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.KEY_MESSAGE));
                    chatMessage.setTimestamp(documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));
                    chatMessages.add(chatMessage);
                }
            }
            Collections.sort(chatMessages, (c1, c2) -> c1.getTimestamp().compareTo(c2.getTimestamp()));
            if (count == 0) {
                chatAdapter.notifyDataSetChanged();
            } else {
                chatAdapter.notifyItemRangeInserted(chatMessages.size(), chatMessages.size());
                binding.chatRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
            }

            binding.chatRecyclerView.setVisibility(View.VISIBLE);
        }
        binding.progressBar.setVisibility(View.GONE);

    };


    // string -> bitmap
    private Bitmap decodeProfileImageString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


}