package edu.northeastern.numad23sp_team7.huskymarket.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad23sp_team7.databinding.ItemRecentMessageBinding;
import edu.northeastern.numad23sp_team7.huskymarket.listeners.RecentMessageItemClickListener;
import edu.northeastern.numad23sp_team7.huskymarket.model.ChatMessage;
import edu.northeastern.numad23sp_team7.huskymarket.model.User;

public class RecentMessagesAdapter {
    private final List<ChatMessage> chatMessages;
    private final List<User> users;
    private final RecentMessageItemClickListener recentMessageItemClickListener;


    public RecentMessagesAdapter(List<ChatMessage> chatMessages, List<User> users, RecentMessageItemClickListener recentMessageItemClickListener) {
        this.chatMessages = chatMessages;
        this.users = users;
        this.recentMessageItemClickListener = recentMessageItemClickListener;
    }


    class RecentMessageViewHolder extends RecyclerView.ViewHolder {
        ItemRecentMessageBinding binding;

        public RecentMessageViewHolder(ItemRecentMessageBinding itemRecentMessageBinding) {
            super(itemRecentMessageBinding.getRoot());
            binding = itemRecentMessageBinding;
        }

        public void setData(ChatMessage chatMessage) {



        }
    }


    // string -> bitmap
    private Bitmap decodeProfileImageString(String encodedImage) {
        byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    // TODO
//    private String getItemProfileString(ChatMessage chatMessage) {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        String curUserId = mAuth.getCurrentUser().getUid();
//        String targetUserId = chatMessage.getSenderId().equals(curUserId) ? chatMessage.getReceiverId() : chatMessage.getSenderId();
//
//
//    }
}
