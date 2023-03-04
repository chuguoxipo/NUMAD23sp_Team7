package edu.northeastern.numad23sp_team7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.numad23sp_team7.model.History;
import edu.northeastern.numad23sp_team7.model.User;

public class SendStickerActivity extends AppCompatActivity {

    private static final String TAG = "SendStickerActivity";
    static final String HISTORY_USERNAME_KEY = "logged-in-user";
    static final String HISTORY_SEND_OR_RECEIVE_FLAG = "send-or-receive";
    static final String HISTORY_SEND_VALUE = "send";
    static final String HISTORY_RECEIVE_VALUE = "receive";
    static final String HISTORY_IMAGE_MAP_KEY = "image-map";

    private DatabaseReference mDatabase;
    private TextView usernameText;

    private ImageView sticker1;
    private ImageView sticker2;
    private ImageView sticker3;
    private ImageView sticker4;
    private ImageView currentClickedSticker = null;
    private List<ImageView> stickerList = new ArrayList<>();

    private Spinner selectReceiverSpinner;
    private List<String> receiverList = new ArrayList<>();
    private ArrayAdapter<String> spinnerAdapter;
    private String receiverUsername;

    private Button sendButton;
    private Button sendHistoryButton;
    private Button receiveHistoryButton;

    private TextView textCategory1;
    private TextView textCategory2;
    private TextView textCategory3;
    private TextView textCategory4;

    private Map<String, String> categoryMap;
    private Map<Integer, String> imageIdToFilenameMap = new HashMap<>();
    private String loggedInUsername = "user1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

        // Connect with firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());


        // TODO
        // change username as the current username
        usernameText = findViewById(R.id.username);

        // TODO
        // get all usernames except for the current user from database
        // change the following adds codes
        receiverList.add("user1");
        receiverList.add("user2");
        receiverList.add("user3");

        selectReceiverSpinner = findViewById(R.id.spinner_receiver);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, receiverList);
        selectReceiverSpinner.setAdapter(spinnerAdapter);

        selectReceiverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get and set the selected receiver username from the spinner
                String selectedReceiver = parent.getItemAtPosition(position).toString();
                receiverUsername = selectedReceiver;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sticker1 = findViewById(R.id.sticker1);
        sticker2 = findViewById(R.id.sticker2);
        sticker3 = findViewById(R.id.sticker3);
        sticker4 = findViewById(R.id.sticker4);

        stickerList.add(sticker1);
        stickerList.add(sticker2);
        stickerList.add(sticker3);
        stickerList.add(sticker4);

        categoryMap = new HashMap<>();
        categoryMap.put("sticker1", "Food");
        categoryMap.put("sticker2", "Food");
        categoryMap.put("sticker3", "Drink");
        categoryMap.put("sticker4", "Food");

        textCategory1 = findViewById(R.id.textViewStickerCategory1);
        textCategory2 = findViewById(R.id.textViewStickerCategory2);
        textCategory3 = findViewById(R.id.textViewStickerCategory3);
        textCategory4 = findViewById(R.id.textViewStickerCategory4);
        textCategory1.setText(categoryMap.get(R.id.sticker1));
        textCategory2.setText(categoryMap.get(R.id.sticker2));
        textCategory3.setText(categoryMap.get(R.id.sticker3));
        textCategory4.setText(categoryMap.get(R.id.sticker4));

        imageIdToFilenameMap.put(sticker1.getId(), "sticker1");
        imageIdToFilenameMap.put(sticker2.getId(), "sticker2");
        imageIdToFilenameMap.put(sticker3.getId(), "sticker3");
        imageIdToFilenameMap.put(sticker4.getId(), "ssticker4");

        for (int i = 0; i < 4; i++) {
            stickerList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentClickedSticker != null) {
                        currentClickedSticker.setBackground(null);
                    }
                    v.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sticker_border));
                    currentClickedSticker = (ImageView) v;

                }
            });
        }

        imageIdToFilenameMap.put(sticker1.getId(), "sticker1");
        imageIdToFilenameMap.put(sticker2.getId(), "sticker2");
        imageIdToFilenameMap.put(sticker3.getId(), "sticker3");
        imageIdToFilenameMap.put(sticker4.getId(), "sticker4");

        sendButton = findViewById(R.id.send_sticker);
        sendButton.setOnClickListener(new View.OnClickListener() {
            // send button
            @Override
            public void onClick(View v) {
                // determine if receiver or stickerId is null
                if (receiverUsername == null || currentClickedSticker == null) {
                    Toast.makeText(getApplicationContext(), "Please choose a receiver and a sticker", Toast.LENGTH_LONG).show();
                } else {
                    int stickerId = currentClickedSticker.getId();
                    String imageFilename = imageIdToFilenameMap.get(stickerId);

                    // update database
                    // TODO
                    // change senderUsername to the current signedIn user's username
                    updateReceiverHistory(mDatabase, imageFilename, loggedInUsername, receiverUsername);
                    updateSenderHistory(mDatabase, imageFilename, loggedInUsername, receiverUsername);
                    Log.d(TAG, "sent");
                    Toast.makeText(getApplicationContext(), "Sticker Sent", Toast.LENGTH_LONG).show();
                }
            }
        });

        sendHistoryButton = findViewById(R.id.buttonStickerSendHistory);
        sendHistoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, StickerHistoryActivity.class);
            intent.putExtra(HISTORY_USERNAME_KEY, loggedInUsername);
            intent.putExtra(HISTORY_SEND_OR_RECEIVE_FLAG, HISTORY_SEND_VALUE);
            intent.putExtra(HISTORY_IMAGE_MAP_KEY, (Serializable) imageIdToFilenameMap);
            startActivity(intent);
        });

        receiveHistoryButton = findViewById(R.id.buttonStickerReceiveHistory);
        receiveHistoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, StickerHistoryActivity.class);
            intent.putExtra(HISTORY_USERNAME_KEY, loggedInUsername);
            intent.putExtra(HISTORY_SEND_OR_RECEIVE_FLAG, HISTORY_RECEIVE_VALUE);
            startActivity(intent);
        });
    }

    // add Sent History to sender's sentRecords
    private void updateSenderHistory(
            DatabaseReference mDatabase,
            String stickerId,
            String senderUsername,
            String receiverUsername) {
        mDatabase.child(senderUsername).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                User user = mutableData.getValue(User.class);
                if (user == null) {
                    user = new User(senderUsername);
                }

                user.getSentRecords().add(new History(stickerId, receiverUsername, categoryMap.get(stickerId)));
                mutableData.setValue(user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError,
                    boolean b,
                    DataSnapshot dataSnapshot) {
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });

    }

    // update Received History to receiver's receivedRecords
    private void updateReceiverHistory(
            DatabaseReference mDatabase,
            String stickerId,
            String senderUsername,
            String receiverUsername) {
        mDatabase.child(receiverUsername).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                User user = mutableData.getValue(User.class);
                if (user == null) {
                    user = new User(receiverUsername);
                }

                user.getReceivedRecords().add(new History(stickerId, senderUsername, categoryMap.get(stickerId)));
                mutableData.setValue(user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError,
                    boolean b,
                    DataSnapshot dataSnapshot) {
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}