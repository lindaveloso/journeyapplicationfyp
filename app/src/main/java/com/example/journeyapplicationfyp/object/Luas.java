package com.example.journeyapplicationfyp.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Luas {

    @SerializedName("created")
    private String createdTime;
    private String message;

    @SerializedName("status")
    private Status status;

    private List<Data2> trams;

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

    public Status getStopForecastStatus() {
        return status;
    }

    public void setStopForecastStatus(Status s) {
        status = s;
    }

    public List<Data2> getTrams() {
        return trams;
    }

    public void setTrams(List<Data2> t) {
        trams = t;
    }
}