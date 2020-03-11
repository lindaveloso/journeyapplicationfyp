package com.example.journeyapplicationfyp.object;

import com.google.gson.annotations.SerializedName;

public class StopForecastStatus {

    @SerializedName("inbound")
    private StopForecastStatusDirection stopForecastStatusDirectionInbound;

    @SerializedName("outbound")
    private StopForecastStatusDirection stopForecastStatusDirectionOutbound;

    public StopForecastStatus() {
        stopForecastStatusDirectionInbound = new StopForecastStatusDirection();
        stopForecastStatusDirectionOutbound = new StopForecastStatusDirection();
    }

    public StopForecastStatusDirection getStopForecastStatusDirectionInbound() {
        return stopForecastStatusDirectionInbound;
    }

    public void setStopForecastStatusDirectionInbound(StopForecastStatusDirection f) {
        stopForecastStatusDirectionInbound = f;
    }

    public StopForecastStatusDirection getStopForecastStatusDirectionOutbound() {
        return stopForecastStatusDirectionOutbound;
    }

    public void setStopForecastStatusDirectionOutbound(StopForecastStatusDirection f) {
        stopForecastStatusDirectionOutbound = f;
    }
}
