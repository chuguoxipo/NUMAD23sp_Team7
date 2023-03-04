package edu.northeastern.numad23sp_team7;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.northeastern.numad23sp_team7.model.History;
import edu.northeastern.numad23sp_team7.model.User;

public class StickerHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCategory;
    private StickerHistoryCategoryAdapter categoryAdapter;

    private RecyclerView recyclerViewRecord;
    private StickerHistoryItemAdapter itemAdapter;

    private String loggedInUser;
    private String sendOrReceiveFlag;
    private ArrayList<History> records = new ArrayList<>();
    private static final String LOG_KEY = "Sticker history activity";
    private static final String TITLE_SEND = "Send History";
    private static final String TITLE_RECEIVE = "Receive History";
    private Map<String, Integer> counter = new HashMap<>();
    private TextView textTitle;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_history_main);

        // Get ∂ata from intent
        Intent intent = getIntent();
        loggedInUser = intent.getStringExtra(SendStickerActivity.HISTORY_USERNAME_KEY);
        sendOrReceiveFlag = intent.getStringExtra(SendStickerActivity.HISTORY_SEND_OR_RECEIVE_FLAG);

        // Restore data from bundle
        if (savedInstanceState != null) {
            loggedInUser = savedInstanceState.getString(SendStickerActivity.HISTORY_USERNAME_KEY);
            sendOrReceiveFlag = savedInstanceState.getString(SendStickerActivity.HISTORY_SEND_OR_RECEIVE_FLAG);
        }

        // Set title
        String title = sendOrReceiveFlag.equals(SendStickerActivity.HISTORY_SEND_VALUE) ? TITLE_SEND : TITLE_RECEIVE;
        textTitle = findViewById(R.id.textViewStickerHistoryTitle);
        textTitle.setText(title);

        // Set up recycler view
        recyclerViewCategory = findViewById(R.id.recyclerViewStickerHistoryCategories);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRecord = findViewById(R.id.recyclerViewStickerHistoryItem);
        recyclerViewRecord.setLayoutManager(new LinearLayoutManager(StickerHistoryActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerViewRecord.addItemDecoration(new DividerItemDecoration(StickerHistoryActivity.this, LinearLayoutManager.VERTICAL));

        // Get history records from firebase
        ref = db.getReference().child(User.class.getSimpleName()).child(loggedInUser);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    if (sendOrReceiveFlag.equals(SendStickerActivity.HISTORY_RECEIVE_VALUE)) {
                        records = user.getReceivedRecords();
                    } else if (sendOrReceiveFlag.equals(SendStickerActivity.HISTORY_SEND_VALUE)) {
                        records = user.getSentRecords();
                    }
                }

                // Update category recyclerview
                for (History record: records) {
                    counter.put(record.getCategory(), counter.getOrDefault(record.getCategory(), 0) + 1);
                }

                ArrayList<String> categoryItems = new ArrayList<>();
                ArrayList<Integer> counts = new ArrayList<>();
                for (Map.Entry<String, Integer> entry: counter.entrySet()) {
                    categoryItems.add(entry.getKey());
                    counts.add(entry.getValue());
                }
                categoryAdapter = new StickerHistoryCategoryAdapter(categoryItems, counts, StickerHistoryActivity.this);
                recyclerViewCategory.setAdapter(categoryAdapter);

                // Record history recycler view
                itemAdapter = new StickerHistoryItemAdapter(records, sendOrReceiveFlag, StickerHistoryActivity.this);
                recyclerViewRecord.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Get user data error", Toast.LENGTH_LONG);
                Log.d(LOG_KEY, "onCancelled" + error);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SendStickerActivity.HISTORY_USERNAME_KEY, loggedInUser);
        outState.putString(SendStickerActivity.HISTORY_SEND_OR_RECEIVE_FLAG, sendOrReceiveFlag);
    }
}