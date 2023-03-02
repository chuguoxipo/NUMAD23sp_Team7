package edu.northeastern.numad23sp_team7.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class History {

    private String stickerId;
    private String username;
    private long timestamp;

    public History() {
    }

    public History(String stickerId, String username) {
        this.stickerId = stickerId;
        this.username = username;
        this.timestamp = System.currentTimeMillis();
    }


    public long getTimestamp() {
        return timestamp;
    }

    public String getCurrentTime() {
        Date currentDate = new Date(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(currentDate);
        return formattedDate;
    }


    public String getUsername() {
        return username;
    }

    public String getStickerId() {
        return stickerId;
    }
}
