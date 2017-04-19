package com.zakati.models.general;

/**
 * Created by rahil on 13/5/16.
 */
public class ApiResp<T> extends GeneralResp {


    private T result;

    public T getResponse() {
        return result;
    }

}
