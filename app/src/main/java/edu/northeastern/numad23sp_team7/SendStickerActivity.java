package edu.northeastern.numad23sp_team7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SendStickerActivity extends AppCompatActivity {

    private static final String TAG = SendStickerActivity.class.getSimpleName();


    private DatabaseReference mDatabase;
    private TextView user;

    private ImageView sticker1;
    private ImageView sticker2;
    private ImageView sticker3;
    private ImageView sticker4;
    private ImageView currentClickedSticker = null;
    private Button sendButton;
    private Button sendHistoryButton;
    private Button receiveHistoryButton;


    List<ImageView> stickerList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sticker);

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


    }


}