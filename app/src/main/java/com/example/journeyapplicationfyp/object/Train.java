package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Train implements Serializable {

   private static final long serialVersionUID = 1L;

    public String direction;
    public String PublicMessage;
    public String TrainStatus;
    public String Status;

    public String Origin;
    public String Stationfullname;
    public String Duein;
    public String Direction;

    public Train(String direction, String PublicMessage, String TrainStatus) {
        this.direction = direction;
        this.PublicMessage = PublicMessage;
        this.TrainStatus = TrainStatus;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getStationfullname() {
        return Stationfullname;
    }

    public void setStationfullname(String stationfullname) {
        Stationfullname = stationfullname;
    }

    public String getDuein() {
        return Duein;
    }

    public void setDuein(String duein) {
        Duein = duein;
    }

    public void setDirection(String direction) {
        Direction = direction;
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
