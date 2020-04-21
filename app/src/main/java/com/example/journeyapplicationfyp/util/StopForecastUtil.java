/**
 * @author Aaron Hastings
 *
 * Copyright 2015-2019 Aaron Hastings
 *
 * This file is part of Luas at a Glance.
 *
 * Luas at a Glance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Luas at a Glance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Luas at a Glance.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.journeyapplicationfyp.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.api.ApiTimes;
import com.example.journeyapplicationfyp.object.StopForecast;
import com.example.journeyapplicationfyp.object.Tram;
import com.example.journeyapplicationfyp.view.TutorialCardView;
import com.google.android.material.snackbar.Snackbar;

public final class StopForecastUtil {

    private static final String LOG_TAG = StopForecastUtil.class.getSimpleName();

    /**
     * Determine if this is the first time the app has been launched and, if so, display a brief
     * tutorial on how to use a particular feature of the app.
     * @param rootView      Root View.
     * @param line          Currently-selected line.
     * @param tutorial      Tutorial to display.
     * @param shouldDisplay Whether or not tutorial should display.
     */
    public static void displayTutorial(View rootView, String line, String tutorial,
                                       boolean shouldDisplay) {
        /* Only display tutorials on the Red Line tab. */
       if (line.equals(Constant.RED_LINE)) {
            switch (tutorial) {
                case Constant.TUTORIAL_SELECT_STOP:
                    TutorialCardView tutorialCardViewSelectStop =
                            rootView.findViewById(
                                    R.id.tutorialcardview_select_stop
                            );

                    tutorialCardViewSelectStop.setTutorial(
                            rootView.getContext().getResources().getText(
                                    R.string.select_stop_tutorial
                            )
                    );

                    if (shouldDisplay) {
                        if (!Preferences.hasRunOnce(rootView.getContext(), tutorial)) {
                            Log.i(
                                    LOG_TAG,
                                    "First time launching. Displaying select stop tutorial."
                            );

                            tutorialCardViewSelectStop.setVisibility(View.VISIBLE);

                            Preferences.saveHasRunOnce(rootView.getContext(), tutorial, true);

                        }
                    } else {
                        tutorialCardViewSelectStop.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    }

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
