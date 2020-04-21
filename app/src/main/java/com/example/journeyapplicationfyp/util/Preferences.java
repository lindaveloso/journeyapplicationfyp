package com.example.journeyapplicationfyp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.journeyapplicationfyp.R;

public final class Preferences {

    /**
     * Load the currently-selected stop name from shared preferences.
     * @param context Context.
     * @return Selected stop name, or null if none found.
     */
    public static String defaultStopName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getString(
                context.getString(R.string.access_token),
                context.getString(R.string.none)
        );
    }

    /**
     * Whether or not a particular tutorial has been completed by the user.
     * @param context Context.
     * @return Tutorial completed.
     */
    public static boolean hasRunOnce(Context context, String tutorialName) {
        final String PREFS_NAME = "org.thecosmicfrog.luasataglance";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return prefs.getBoolean(tutorialName, false);
    }
    /**
     * Load the device screen height in DP from shared preferences.
     * @param context Context.
     * @return Device screen height in DP, or -1.0 if none found.
     */
    public static float screenHeight(Context context) {
        final String PREFS_NAME = "org.thecosmicfrog.luasataglance";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return prefs.getFloat("screenHeight", -1.0f);
    }

    /**
     * Load the currently-selected stop name from shared preferences.
     * @param context Context.
     * @return Selected stop name, or null if none found.
     */
    public static String selectedStopName(Context context, String line) {
        final String PREFS_NAME = "org.thecosmicfrog.luasataglance";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (line.equalsIgnoreCase("no_line")) {
            return prefs.getString("selectedStopName", null);
        }

        return prefs.getString(line + "_selectedStopName", null);
    }

    /**
     * Store boolean value of whether a particular tutorial have been completed by the user.
     * @param context Context.
     * @param tutorialName Tutorial that has been completed or not.
     * @param hasRun Whether or not tutorial has been completed.
     * @return Successfully saved.
     */
    public static boolean saveHasRunOnce(Context context, String tutorialName, boolean hasRun) {
        final String PREFS_NAME = "org.thecosmicfrog.luasataglance";

        SharedPreferences.Editor prefs =
                context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        prefs.putBoolean(tutorialName, hasRun);

        return prefs.commit();
    }

    /**
     * Save the currently-selected stop name to shared preferences.
     * @param context Context.
     * @param selectedStopName Name of the stop to save to shared preferences.
     * @return Successfully saved.
     */
    public static boolean saveSelectedStopName(Context context, String line,
                                               String selectedStopName) {
        final String PREFS_NAME = "org.thecosmicfrog.luasataglance";

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
