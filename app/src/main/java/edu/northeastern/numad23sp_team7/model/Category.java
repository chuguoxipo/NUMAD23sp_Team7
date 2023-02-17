package edu.northeastern.numad23sp_team7.model;

public class Category {
    private String alias;
    private String title;

    public Category(String title) {
        this.title = title;
        this.alias = title;
    }

    public Category(String alias, String title) {
        this.alias = alias;
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
