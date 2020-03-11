package com.example.journeyapplicationfyp.api;

import com.example.journeyapplicationfyp.object.StopForecastStatus;
import com.example.journeyapplicationfyp.object.Tram;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiTimes {

    @SerializedName("created")
    private String createdTime;
    private String message;

    @SerializedName("status")
    private StopForecastStatus stopForecastStatus;

    private List<Tram> trams;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String c) {
        createdTime = c;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        message = m;
    }

    public StopForecastStatus getStopForecastStatus() {
        return stopForecastStatus;
    }

    public void setStopForecastStatus(StopForecastStatus s) {
        stopForecastStatus = s;
    }

    public List<Tram> getTrams() {
        return trams;
    }

    public void setTrams(List<Tram> t) {
        trams = t;
    }
}