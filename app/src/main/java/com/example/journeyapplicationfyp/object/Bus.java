package com.example.journeyapplicationfyp.object;

/*class will be only used to hold the json data that we will fetch.
 * That is why the class is having only a constructor
 * to initialize the fields and getters to get the values.
 */

import java.io.Serializable;

public class Bus implements Serializable {
    public String dueT;
    public String destinatioN;
    public String directioN;
    public String routE;

    public Bus( String dueT, String destinatioN,  String directioN, String routE) {
        this.dueT = dueT;
        this.destinatioN = destinatioN;
        this.directioN = directioN;
        this.routE = routE;
    }



    public String getDueT() {
        return dueT;
    }

    public String getDestinatioN() {
        return destinatioN;
    }

    public String getDirectioN() {
        return directioN;
    }

    public String getRoutE() {
        return routE;
    }
}
