package edu.northeastern.numad23sp_team7.huskymarket.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String email;
    private String password;
    private String profileImage;
    private List<Product> favorites = new ArrayList<>();

    public User() {
    }

    public User(String userId, String email, String password, String profileImage, List<Product> favorites) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.favorites = favorites;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<Product> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Product> favorites) {
        this.favorites = favorites;
    }
}
