package edu.northeastern.numad23sp_team7.huskymarket.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad23sp_team7.databinding.ItemReceivedMessageBinding;
import edu.northeastern.numad23sp_team7.databinding.ItemSentMessageBinding;
import edu.northeastern.numad23sp_team7.huskymarket.model.ChatMessage;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMessage> chatMessages;
    private final Bitmap senderProfileImage;
    private final Bitmap receiverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 0;
    public static final int VIEW_TYPE_RECEIVED = 1;


    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap senderProfileImage, Bitmap receiverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.senderProfileImage = senderProfileImage;
        this.receiverProfileImage = receiverProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            return new SentMessageViewHolder(ItemSentMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new ReceivedMessageViewHolder(ItemReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).setData(chatMessages.get(position), senderProfileImage);
        } else {
            ((ReceivedMessageViewHolder) holder).setData(chatMessages.get(position), receiverProfileImage);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).getSenderId().equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }


    public static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemSentMessageBinding binding;

        public SentMessageViewHolder(ItemSentMessageBinding itemSentMessageBinding) {
            super(itemSentMessageBinding.getRoot());
            binding = itemSentMessageBinding;
        }

        public void setData(ChatMessage chatMessage, Bitmap senderProfileImage) {
            binding.textMessage.setText(chatMessage.getMessage());
            binding.textDateTime.setText(getDateTimeText(chatMessage.getDateObject()));
            binding.imageProfile.setImageBitmap(senderProfileImage);
        }

        private String getDateTimeText(Date date) {
            return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
        }

    }

    public static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemReceivedMessageBinding binding;

        public ReceivedMessageViewHolder(ItemReceivedMessageBinding itemReceivedMessageBinding) {
            super(itemReceivedMessageBinding.getRoot());
            binding = itemReceivedMessageBinding;
        }

        public void setData(ChatMessage chatMessage, Bitmap receiverProfileImage) {
            binding.textMessage.setText(chatMessage.getMessage());
            binding.textDateTime.setText(getDateTimeText(chatMessage.getDateObject()));
            binding.imageProfile.setImageBitmap(receiverProfileImage);
        }

        private String getDateTimeText(Date date) {
            return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
        }
    }
}
