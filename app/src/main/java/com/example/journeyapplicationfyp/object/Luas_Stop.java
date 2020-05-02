package com.example.journeyapplicationfyp.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Luas_Stop implements Serializable {
    private static final long serialVersionUID = 0L;

    private String message;
    private StopForecastStatusDirection stopForecastStatusDirectionInbound;
    private StopForecastStatusDirection stopForecastStatusDirectionOutbound;
    private List<Data2> inboundTrams;
    private List<Data2> outboundTrams;

    public Luas_Stop() {
        inboundTrams = new ArrayList<>();
        outboundTrams = new ArrayList<>();
        stopForecastStatusDirectionInbound = new StopForecastStatusDirection();
        stopForecastStatusDirectionOutbound = new StopForecastStatusDirection();
    }

    public void addInboundTram(Data2 t) {
        if (t != null)
            inboundTrams.add(t);
    }

    public void addOutboundTram(Data2 t) {
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

    public List<Data2> getInboundTrams() {
        return inboundTrams;
    }

    public List<Data2> getOutboundTrams() {
        return outboundTrams;
    }

}


