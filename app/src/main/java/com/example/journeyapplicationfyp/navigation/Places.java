package com.example.journeyapplicationfyp.navigation;

import com.mapbox.geojson.Point;

public class Places {
    public String placeName;
    public String address;
    public Point point;

    public Places(String placeName, String address, Point point) {
        this.placeName = placeName;
        this.address = address;
        this.point = point;
    }
}
