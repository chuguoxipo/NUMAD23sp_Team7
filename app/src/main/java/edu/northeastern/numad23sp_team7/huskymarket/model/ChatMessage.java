package edu.northeastern.numad23sp_team7.huskymarket.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {

    private String senderId;
    private String receiverId;
    private String message;
    private Date dateObject;


    public ChatMessage() {
    }

    public ChatMessage(String senderId, String receiverId, String message, Date dateObject) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.dateObject = dateObject;
    }


    public Date getDateObject() {
        return dateObject;
    }

    public void setDateObject(Date dateObject) {
        this.dateObject = dateObject;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
