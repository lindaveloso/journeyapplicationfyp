package com.example.journeyapplicationfyp.object;

public class Favourtie {

    String Station;

    public String getStation() {
        return Station;
    }

    public void setStation(String station) {
        Station = station;
    }

    @Override
    public String toString() {
        return "Favourtie{" +
                "Station='" + Station + '\'' +
                '}';
    }
}
