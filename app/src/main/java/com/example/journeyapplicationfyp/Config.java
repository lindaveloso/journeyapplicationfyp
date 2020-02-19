package com.example.journeyapplicationfyp;

public class Config {
    //JSON URL
    public static final String DATA_URL = " https://data.smartdublin.ie/cgi-bin/rtpi/busstopinformation?stopid";

    //Tags used in the JSON String
    public static final String TAG_NAME = "stopid";
    public static final String TAG_COURSE = "destination";
    public static final String TAG_SESSION = "duetime";

    //JSON array name
    public static final String JSON_ARRAY = "results";
}