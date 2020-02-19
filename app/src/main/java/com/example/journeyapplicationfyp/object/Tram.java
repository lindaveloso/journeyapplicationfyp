package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Tram implements Serializable {

    private static final long serialVersionUID = 0L;

    private String destination;
    private String direction;
    private String due;

    public Tram(String destination, String direction, String due) {
        destination = destination;
        direction = direction;
        due = due;
    }

    @Override
    public String toString() {
        if (!due.equals("DUE")) {
            if (Integer.parseInt(due) > 1)
                return String.format("%s\t%s\t%s", destination, direction, due + " mins");
        }

        return String.format("%s\t%s\t%s", destination, direction, due + " min");
    }

    public String getDestination() {
        return destination;
    }

    public String getDirection() {
        return direction;
    }

    public String getDue() {
        return due;
    }
}
