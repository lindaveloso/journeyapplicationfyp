package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class StopForecastStatusDirection implements Serializable {

    private static final long serialVersionUID = 3L;

    private String message;
    private boolean forecastsEnabled;
    private boolean operatingNormally;

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        message = m;
    }

    public boolean getForecastsEnabled() {
        return forecastsEnabled;
    }

    public void setForecastsEnabled(boolean f) {
        forecastsEnabled = f;
    }

    public boolean getOperatingNormally() {
        return operatingNormally;
    }

    public void setOperatingNormally(boolean o) {
        operatingNormally = o;
    }
}


