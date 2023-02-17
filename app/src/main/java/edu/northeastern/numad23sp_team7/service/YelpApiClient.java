package edu.northeastern.numad23sp_team7.service;

import java.io.IOException;
import java.util.List;

import edu.northeastern.numad23sp_team7.model.ResponseData;
import edu.northeastern.numad23sp_team7.model.Restaurant;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpApiClient {

    private static final String BASE_URL = "https://api.yelp.com/";
    private static final String API_KEY = "aMPILublEQoOSBjwnQkt44bSQZXmFmE0rKLns0Tp1bMIciil5geu7tGAi3Z1Ee-rBiU-4cX-uy1e89Tz3fW9XioreLjiLsPDiYH1PYRxg8R5YlyCzxF_JVmksMe3YXYx";
    private Retrofit retrofit;
    private static YelpService service;

    public YelpApiClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + API_KEY)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(YelpService.class);
    }

    public List<Restaurant> getRestaurants(String searchTerm, String location) {
        Call<ResponseData> callSync = service.getData(location, searchTerm);

        try {
            Response<ResponseData> response = callSync.execute();
            ResponseData responseData = response.body();
            List<Restaurant> restaurants = responseData.getBusinesses();
            this.populateCategory(restaurants);
            return restaurants;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void populateCategory(List<Restaurant> restaurants) {
        for (Restaurant restaurant: restaurants) {
            if (!restaurant.getCategories().isEmpty()) {
                restaurant.setCategory(restaurant.getCategories().get(0).getTitle());
            }
        }
    }
}
