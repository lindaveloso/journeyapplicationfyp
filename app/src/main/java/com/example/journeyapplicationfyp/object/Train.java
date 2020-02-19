package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Train implements Serializable {
    public String direction;
    public String PublicMessage;
    public String TrainStatus;


    public Train(String direction, String publicMessage, String trainStatus) {
        this.direction = direction;
        PublicMessage = publicMessage;
        TrainStatus = trainStatus;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPublicMessage() {
        return PublicMessage;
    }

    public void setPublicMessage(String publicMessage) {
        PublicMessage = publicMessage;
    }

    public String getTrainStatus() {
        return TrainStatus;
    }

    public void setTrainStatus(String trainStatus) {
        TrainStatus = trainStatus;
    }
}
