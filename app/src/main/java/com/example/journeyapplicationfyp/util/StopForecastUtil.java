package com.example.journeyapplicationfyp.util;


import com.example.journeyapplicationfyp.api.ApiTimes;
import com.example.journeyapplicationfyp.object.StopForecast;
import com.example.journeyapplicationfyp.object.Tram;


public final class StopForecastUtil {

    private static final String LOG_TAG = StopForecastUtil.class.getSimpleName();

    public static StopForecast createStopForecast(ApiTimes apiTimes) {
        StopForecast stopForecast = new StopForecast();

        if (apiTimes.getMessage() != null) {
            stopForecast.setMessage(apiTimes.getMessage());
        }

        if (apiTimes.getStopForecastStatus() != null) {
            if (apiTimes.getStopForecastStatus().getStopForecastStatusDirectionInbound() != null) {
                if (apiTimes.getStopForecastStatus().getStopForecastStatusDirectionInbound()
                        .getMessage() != null) {
                    stopForecast.getStopForecastStatusDirectionInbound().setMessage(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getMessage()
                    );
                    stopForecast.getStopForecastStatusDirectionInbound().setForecastsEnabled(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getForecastsEnabled()
                    );
                    stopForecast.getStopForecastStatusDirectionInbound().setOperatingNormally(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getOperatingNormally()
                    );
                }
            }

            if (apiTimes.getStopForecastStatus().getStopForecastStatusDirectionOutbound() != null) {
                if (apiTimes.getStopForecastStatus().getStopForecastStatusDirectionOutbound()
                        .getMessage() != null) {
                    stopForecast.getStopForecastStatusDirectionOutbound().setMessage(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getMessage()
                    );
                    stopForecast.getStopForecastStatusDirectionOutbound().setForecastsEnabled(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getForecastsEnabled()
                    );
                    stopForecast.getStopForecastStatusDirectionOutbound().setOperatingNormally(
                            apiTimes.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getOperatingNormally()
                    );
                }
            }
        }

        if (apiTimes.getTrams() != null) {
            for (Tram tram : apiTimes.getTrams()) {
                switch (tram.getDirection()) {
                    case "Inbound":
                        stopForecast.addInboundTram(tram);

                        break;

                    case "Outbound":
                        stopForecast.addOutboundTram(tram);

                        break;

                }
            }
        }

        return stopForecast;
    }
}
