package com.example.journeyapplicationfyp.object;

import java.io.Serializable;

public class Bus implements Serializable {

    private static final long serialVersionUID = 0L;

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
