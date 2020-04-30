package com.example.journeyapplicationfyp.util;


import com.example.journeyapplicationfyp.object.Data2;
import com.example.journeyapplicationfyp.object.Luas;
import com.example.journeyapplicationfyp.object.Luas_Stop;


public final class StopForecastUtil {

    private static final String LOG_TAG = StopForecastUtil.class.getSimpleName();

    public static Luas_Stop createStopForecast(Luas luas) {
        Luas_Stop luasStop = new Luas_Stop();

        if (luas.getMessage() != null) {
            luasStop.setMessage(luas.getMessage());
        }

        if (luas.getStopForecastStatus() != null) {
            if (luas.getStopForecastStatus().getStopForecastStatusDirectionInbound() != null) {
                if (luas.getStopForecastStatus().getStopForecastStatusDirectionInbound()
                        .getMessage() != null) {
                    luasStop.getStopForecastStatusDirectionInbound().setMessage(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getMessage()
                    );
                    luasStop.getStopForecastStatusDirectionInbound().setForecastsEnabled(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getForecastsEnabled()
                    );
                    luasStop.getStopForecastStatusDirectionInbound().setOperatingNormally(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionInbound().getOperatingNormally()
                    );
                }
            }

            if (luas.getStopForecastStatus().getStopForecastStatusDirectionOutbound() != null) {
                if (luas.getStopForecastStatus().getStopForecastStatusDirectionOutbound()
                        .getMessage() != null) {
                    luasStop.getStopForecastStatusDirectionOutbound().setMessage(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getMessage()
                    );
                    luasStop.getStopForecastStatusDirectionOutbound().setForecastsEnabled(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getForecastsEnabled()
                    );
                    luasStop.getStopForecastStatusDirectionOutbound().setOperatingNormally(
                            luas.getStopForecastStatus()
                                    .getStopForecastStatusDirectionOutbound().getOperatingNormally()
                    );
                }
            }
        }

        if (luas.getTrams() != null) {
            for (Data2 tram : luas.getTrams()) {
                switch (tram.getDirection()) {
                    case "Inbound":
                        luasStop.addInboundTram(tram);

                        break;

                    case "Outbound":
                        luasStop.addOutboundTram(tram);

                        break;

                }
            }
        }

        return luasStop;
    }
}
