package com.zakati.models.events;

import android.content.Intent;

/**
 * Created by rahil on 1/12/16.
 */

public class OnActivityResultEvent {

    int requestCode;
    int resultCode;
    Intent data;

    public OnActivityResultEvent(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
