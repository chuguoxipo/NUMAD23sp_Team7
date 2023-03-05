package edu.northeastern.numad23sp_team7.FireBaseModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import edu.northeastern.numad23sp_team7.R;
import edu.northeastern.numad23sp_team7.SendStickerActivity;
import edu.northeastern.numad23sp_team7.model.User;

public class LogInActivity extends AppCompatActivity {

    static final String HISTORY_USERNAME_KEY = "logged-in-user";
    public String currentUser;
    EditText usernameEdit;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameEdit = findViewById(R.id.username);
        loginBtn = findViewById(R.id.loginbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
                currentUser = usernameEdit.getText().toString().trim();
                Log.d("currentUser", currentUser);
                gotoSendActivity();
            }
        });
    }

    private void userLogin() {
        String username = usernameEdit.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        usersRef.child(username).addListenerForSingleValueEvent(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        usersRef.child(username).setValue(new User(username));
                    }
                    currentUser = new StringBuilder(username).toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            }
        );


    }

    public void gotoSendActivity() {
        Intent intent = new Intent(this, SendStickerActivity.class);
        intent.putExtra(HISTORY_USERNAME_KEY, currentUser);
        startActivity(intent);
    }
}