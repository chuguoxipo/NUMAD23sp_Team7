package edu.northeastern.numad23sp_team7.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Restaurant {

    @SerializedName("image_url")
    private String imgUrl;

    private String name;
    private String rating;
    private String category;
    private List<Category> categories;

    public Restaurant(String imgUrl, String name, String rating, String category, List<Category> categories) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.rating = rating;
        this.category = category;
        this.categories = categories;
    }

    public Restaurant(String imgUrl, String name, String rating, String category) {
        this(imgUrl, name, rating, category, Arrays.asList(new Category(category)));
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getRating() {
        return rating;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
