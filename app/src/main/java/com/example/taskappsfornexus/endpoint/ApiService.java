package com.example.taskappsfornexus.endpoint;

import com.example.taskappsfornexus.dataclass.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("https://newsapi.org")
    Call<ApiResponse> fetchData(@Query("apiKey") String apiKey);
}
