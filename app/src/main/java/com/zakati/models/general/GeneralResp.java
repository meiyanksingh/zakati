package com.zakati.models.general;

/**
 * Created by rahil on 13/5/16.
 */
public class GeneralResp {


    /**
     * success : true
     * status : 200
     * message : Successfully login
     * session : true
     */

    private boolean status;
    private String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
