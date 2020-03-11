package com.example.journeyapplicationfyp.api;

public class ApiFares {

    private String peak;
    private String offpeak;
    private String zonesTravelled;

    public String getPeak() {
        return peak;
    }

    public void setPeak(String p) {
        peak = p;
    }

    public String getOffpeak() {
        return offpeak;
    }

    public void setOffpeak(String o) {
        offpeak = o;
    }

    public String getZonesTravelled() {
        return zonesTravelled;
    }

    public void setZonesTravelled(String z) {
        zonesTravelled = z;
    }
}

