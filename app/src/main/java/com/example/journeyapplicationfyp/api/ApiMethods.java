package com.example.journeyapplicationfyp.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiMethods {

    @GET("/luas-api.php")
    void getFares(
            @Query("action") String action,
            @Query("from") String from,
            @Query("to") String to,
            @Query("adults") String adults,
            @Query("children") String children,
            Callback<ApiFares> cb
    );

    @GET("/luas-api.php")
    void getStopForecast(
            @Query("action") String action,
            @Query("ver") String ver,
            @Query("station") String station,
            Callback<ApiTimes> cb
    );
}

