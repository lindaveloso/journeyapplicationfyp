package com.example.journeyapplicationfyp.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StopForecast implements Serializable {
    private static final long serialVersionUID = 0L;

    private String message;
    private StopForecastStatusDirection stopForecastStatusDirectionInbound;
    private StopForecastStatusDirection stopForecastStatusDirectionOutbound;
    private List<Tram> inboundTrams;
    private List<Tram> outboundTrams;

    public StopForecast() {
        inboundTrams = new ArrayList<>();
        outboundTrams = new ArrayList<>();
        stopForecastStatusDirectionInbound = new StopForecastStatusDirection();
        stopForecastStatusDirectionOutbound = new StopForecastStatusDirection();
    }

    public void addInboundTram(Tram t) {
        if (t != null)
            inboundTrams.add(t);
    }

    public void addOutboundTram(Tram t) {
        if (t != null)
            outboundTrams.add(t);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        message = m;
    }

    public StopForecastStatusDirection getStopForecastStatusDirectionInbound() {
        return stopForecastStatusDirectionInbound;
    }

    public StopForecastStatusDirection getStopForecastStatusDirectionOutbound() {
        return stopForecastStatusDirectionOutbound;
    }

    public List<Tram> getInboundTrams() {
        return inboundTrams;
    }

    public List<Tram> getOutboundTrams() {
        return outboundTrams;
    }

}


