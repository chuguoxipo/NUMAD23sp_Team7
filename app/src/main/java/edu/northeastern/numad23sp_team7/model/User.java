package edu.northeastern.numad23sp_team7.model;

import java.util.ArrayList;

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


    public String getUsername() {
        return username;
    }

    public ArrayList<History> getSentRecords() {
        return sentRecords;
    }

    public ArrayList<History> getReceivedRecords() {
        return receivedRecords;
    }
}

