package edu.northeastern.numad23sp_team7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.northeastern.numad23sp_team7.model.History;
import edu.northeastern.numad23sp_team7.model.User;

public class SendStickerActivity extends AppCompatActivity {

    private static final String TAG = "SendStickerActivity";


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

        // Connect with firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(User.class.getSimpleName());

        // todo
        // from database, get all usernames except the current user
        receiverList.add("user2");

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
                    // update database
                    updateSenderHistory(mDatabase, stickerId, "user4", receiverUsername);
                    updateReceiverHistory(mDatabase, stickerId, "user4", receiverUsername);
                }
            }
        });


    }

    // add Sent History to sender's sent sentRecords
    public void addSentHistoryToDB(DatabaseReference mDatabase, int stickerId, String senderUsername, String receiverUsername) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<History> sentRecords = (ArrayList<History>) snapshot.getValue();
                sentRecords.add(new History(stickerId, receiverUsername));
                mDatabase.setValue(sentRecords);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateSenderHistory(
            DatabaseReference mDatabase,
            int stickerId,
            String senderUsername,
            String receiverUsername) {
        mDatabase.child(senderUsername).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                User user = mutableData.getValue(User.class);
                if (user == null) {
                    user = new User(senderUsername);
                }

                user.getSentRecords().add(new History(stickerId, receiverUsername));
                mutableData.setValue(user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(
                    DatabaseError databaseError,
                    boolean b,
                    DataSnapshot dataSnapshot) {
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                Toast.makeText(getApplicationContext(), "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateReceiverHistory(
            DatabaseReference mDatabase,
            int stickerId,
            String senderUsername,
            String receiverUsername) {
        mDatabase.child(receiverUsername).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                User user = mutableData.getValue(User.class);
                if (user == null) {
                    user = new User(receiverUsername);
                }

                user.getReceivedRecords().add(new History(stickerId, senderUsername));
                mutableData.setValue(user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError,
                                   boolean b,
                                   DataSnapshot dataSnapshot) {
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                Toast.makeText(getApplicationContext(), "DBError: " + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }

}