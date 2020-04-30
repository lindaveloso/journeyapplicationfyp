package com.example.journeyapplicationfyp.object;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("inbound")
    private StopForecastStatusDirection stopForecastStatusDirectionInbound;

    @SerializedName("outbound")
    private StopForecastStatusDirection stopForecastStatusDirectionOutbound;

    public Status() {
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
