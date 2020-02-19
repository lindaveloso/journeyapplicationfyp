package com.example.journeyapplicationfyp.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tram_Time {

    @SerializedName("created")
    private String createdTime;

    private String message;

    @SerializedName("status")
    // private StopForecastStatus stopForecastStatus;

    private List<Tram> trams;

    public String getCreatedTime() {
        return createdTime;
    }

    public String getMessage() {
        return message;
    }

 /*   public StopForecastStatus getStopForecastStatus() {
        return stopForecastStatus;
    }*/

    public void setMessage(String m) {
        message = m;
    }

    public List<Tram> getTrams() {
        return trams;
    }


}
