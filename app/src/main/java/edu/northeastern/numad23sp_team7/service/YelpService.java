package edu.northeastern.numad23sp_team7.service;

import edu.northeastern.numad23sp_team7.model.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpService {
    @GET("/v3/businesses/search")
    public Call<ResponseData> getData(
            @Query("location") String location,
            @Query("term") String term);
}
