package com.example.journeyapplicationfyp.navigation;


import com.mapbox.geojson.Point;

public class NavigationState {

    public Point origin;
    public Point destination;

    public NavigationState(Point origin, Point destination) {
        this.origin = origin;
        this.destination = destination;
    }
}
