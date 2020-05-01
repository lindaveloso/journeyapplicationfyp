/*
package com.example.journeyapplicationfyp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.example.journeyapplicationfyp.util.myApplication.getSharedPreferencesCustomer;

public class SessionManager {

    private static final String STATION_NAME = "STATION_NAME";
    private static final String FAVOURITES = "FAVOURITES";
    private  Context context;
    private Activity activity;
    public static final String mypreference = "mypref";

    public SessionManager(Activity activity, Context context) {

        this.activity = activity;
        this.context = context;
    }

    private final SharedPreferences pref = getSharedPreferencesCustomer();


    private void setIntPreference(String name, int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(name, value);
        editor.apply();
    }

    private void setBooleanPreference(String name, boolean value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }

    private long getLongPreference(String name) {
        if (pref.contains(name)) {
            return pref.getLong(name, 0);
        } else {
            return 0;
        }
    }

    private void setLongPreference(String name, long value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(name, value);
        editor.apply();
    }


    private void setStringPreference(String name, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(name, value);
        editor.apply();
    }

    private void setFloatPreference(String name, float value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(name, value);
        editor.apply();
    }

    private Integer getIntPreference(String name) {
        if (pref.contains(name)) {
            return pref.getInt(name, 0);
        } else {
            return 0;
        }
    }

    private boolean getBooleanPreference(String name) {
        return pref.contains(name) && pref.getBoolean(name, false);
    }

    private float getFloatPreference(String name) {
        if (pref.contains(name)) {
            return pref.getFloat(name, 0);
        } else {
            return 0;
        }
    }

    private String getStringPreference(String name) {
        if (pref.contains(name)) {
            return pref.getString(name, "");
        } else {
            return null;
        }
    }



    public String getStationNme() {
        return getStringPreference(STATION_NAME);
    }

    public void setStationName(String phoneNumber) {
        setStringPreference(STATION_NAME, phoneNumber);
    }
    public String getFavourties() {
        return getStringPreference(FAVOURITES);
    }

    public void setFavourites(String favourites) {
        setStringPreference(FAVOURITES, favourites);
    }

}
*/
