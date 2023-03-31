package edu.northeastern.numad23sp_team7.huskymarket.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String profileImage;
    private List<Product> favorites = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String profileImage, List<Product> favorites) {
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.favorites = favorites;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
