package edu.northeastern.numad23sp_team7.model;

import java.util.List;

public class ResponseData {
    private List<Restaurant> businesses;

    public ResponseData(List<Restaurant> businesses) {
        this.businesses = businesses;
    }

    public List<Restaurant> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(List<Restaurant> businesses) {
        this.businesses = businesses;
    }
}
