package com.example.journeyapplicationfyp.api;

import com.example.journeyapplicationfyp.object.Luas;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface Api {

    @GET("/luas-api.php")
    void getStopForecast(
            @Query("action") String action,
            @Query("ver") String ver,
            @Query("station") String station,
            Callback<Luas> cb
    );
}

