package com.example.journeyapplicationfyp.object;

public class Bus {

    public String dueT;
    public String destinatioN;
    public String routE;

    public Bus(String routE, String destinatioN, String dueT) {

        this.dueT = dueT;
        this.destinatioN = destinatioN;
        this.routE = routE;
    }

    public String getRoutE() {
        return routE;
    }

    public String getDestinatioN() {
        return destinatioN;
    }

    public String getDueT() {
        return dueT;
    }


}
