package edu.northeastern.numad23sp_team7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.northeastern.numad23sp_team7.model.History;

public class StickerHistoryItemAdapter extends RecyclerView.Adapter<StickerHistoryItemAdapter.RecordViewHolder> {

    private ArrayList<History> records;
    private Context context;
    private String sendOrReceiveFlag;

    public StickerHistoryItemAdapter(ArrayList<History> records, String sendOrReceiveFlag, Context context) {
        this.records = records;
        this.context = context;
        this.sendOrReceiveFlag = sendOrReceiveFlag;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticker_history_item, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        int resourceId = context.getResources().getIdentifier(records.get(position).getStickerId(), "drawable", context.getPackageName());
        holder.image.setImageResource(resourceId);
        holder.textFlag.setText(sendOrReceiveFlag);
        holder.textUsername.setText(records.get(position).getUsername());
        holder.textTime.setText(this.getFormattedTime(records.get(position).getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView textFlag, textUsername, textTime;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewStickerHistoryImage);
            textFlag = itemView.findViewById(R.id.textViewStickerHistoryFlag);
            textUsername = itemView.findViewById(R.id.textViewStickerHistoryUser);
            textTime = itemView.findViewById(R.id.textViewStickerHistoryTime);
        }
    }

    private String getFormattedTime(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(timestamp);
        return formattedDate;
    }
}
