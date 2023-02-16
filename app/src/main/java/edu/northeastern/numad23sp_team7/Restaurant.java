package edu.northeastern.numad23sp_team7;

public class Restaurant {

    private String name;
    private String imgUrl;
    private String category;
    private String rating;

    public Restaurant(String name, String imgUrl, String category, String rating) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.rating = rating;
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
}
