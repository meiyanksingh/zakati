package com.zakati.utils;

import android.view.View;

import com.ta.base.App;

import static com.ta.base.App.isConnected;

/**
 * Created by rahil on 21/3/17.
 */

public class Net {

    public static boolean isConnected() {
        return isConnected;
    }

    public static boolean isConnected(boolean showToast) {
        if (!isConnected) DialogUtil.showNoNetworkToast(App.getInstance());
        return isConnected;
    }

    public static boolean isConnected(View anyView) {
        if (!isConnected)DialogUtil.showNoNetworkSnackBar(anyView);
        return isConnected;
    }

    public static boolean isConnected(View anyView, View.OnClickListener retryListener) {
        if (!isConnected) DialogUtil.showNoNetworkSnackBar(anyView, retryListener);
        return isConnected;
    }

}
