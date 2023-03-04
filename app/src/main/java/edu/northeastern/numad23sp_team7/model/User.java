package edu.northeastern.numad23sp_team7.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    //    public boolean isSignedIn;
    private ArrayList<History> sentRecords;
    private ArrayList<History> receivedRecords;

    public User(String username) {
        this.username = username;
        this.sentRecords = new ArrayList<>();
        this.receivedRecords = new ArrayList<>();
    }

    public User() {
        this.sentRecords = new ArrayList<>();
        this.receivedRecords = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }

    public ArrayList<History> getSentRecords() {
        return sentRecords == null ? new ArrayList<>() : sentRecords;
    }


    public ArrayList<History> getReceivedRecords() {
        return receivedRecords == null ? new ArrayList<>() : receivedRecords;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("setRecords", sentRecords);
        result.put("receivedRecords", receivedRecords);

        return result;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSentRecords(ArrayList<History> sentRecords) {
        this.sentRecords = sentRecords;
    }

    public void setReceivedRecords(ArrayList<History> receivedRecords) {
        this.receivedRecords = receivedRecords;
    }
}

