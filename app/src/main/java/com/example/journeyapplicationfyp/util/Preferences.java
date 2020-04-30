package com.example.journeyapplicationfyp.util;

import android.content.Context;
import android.content.SharedPreferences;

public final class Preferences {

    /**
     * Load the currently-selected stop name from shared preferences.
     * @param context Context.
     * @return Selected stop name, or null if none found.
     */
    public static String selectedStopName(Context context, String line) {
        final String PREFS_NAME = "qw";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (line.equalsIgnoreCase("no_line")) {
            return prefs.getString("selectedStopName", null);
        }

        return prefs.getString(line + "_selectedStopName", null);
    }


    /**
     * Save the currently-selected stop name to shared preferences.
     * @param context Context.
     * @param selectedStopName Name of the stop to save to shared preferences.
     * @return Successfully saved.
     */
    public static boolean saveSelectedStopName(Context context, String line,
                                               String selectedStopName) {
        final String PREFS_NAME = "qw";

        SharedPreferences.Editor prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        /*
         * Save two preferences. The currently-selected stop name, and the same thing but with a
         * currently-selected tab prefix.
         */
        prefs.putString("selectedStopName", selectedStopName);
        prefs.putString(line + "_selectedStopName", selectedStopName);

        return prefs.commit();
    }
}
