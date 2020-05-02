package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Data2 implements Serializable {

    private static final long serialVersionUID = 0L;

    private String destination;
    private String direction;
    private String dueMinutes;

    public Data2(String de, String di, String du) {
        destination = de;
        direction = di;
        dueMinutes = du;
    }

    @Override
    public String toString() {
        if (!dueMinutes.equals("DUE")) {
            if (Integer.parseInt(dueMinutes) > 1)
                return String.format("%s\t%s\t%s", destination, direction, dueMinutes + " mins");
        }
        return String.format("%s\t%s\t%s", destination, direction, dueMinutes + " min");
    }

    public String getDestination() {
        return destination;
    }

    public String getDirection() {
        return direction;
    }

    public String getDueMinutes() {
        return dueMinutes;
    }
}
