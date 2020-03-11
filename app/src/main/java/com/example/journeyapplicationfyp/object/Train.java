package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Train implements Serializable {

   private static final long serialVersionUID = 1L;

    public String direction;
    public String PublicMessage;
    public String TrainStatus;


    public Train(String direction, String PublicMessage, String TrainStatus) {
        this.direction = direction;
        this.PublicMessage = PublicMessage;
        this.TrainStatus = TrainStatus;
    }

    public String getDirection() {
        return direction;
    }

    public String getPublicMessage() {
        return PublicMessage;
    }

    public String getTrainStatus() {
        return TrainStatus;
    }

}
