package edu.northeastern.numad23sp_team7.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class History {

    private String stickerId;
    private String username;
    private long timestamp;
    private String category;

    public History() {
    }

    public History(String stickerId, String username, String category) {
        this.stickerId = stickerId;
        this.username = username;
        this.timestamp = System.currentTimeMillis();
        this.category = category;
    }


    public long getTimestamp() {
        return timestamp;
    }

//    public String getCurrentTime() {
//        Date currentDate = new Date(timestamp);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(currentDate);
//        return formattedDate;
//    }


    public String getUsername() {
        return username;
    }

    public String getStickerId() {
        return stickerId;
    }

    public String getCategory() { return category; }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
