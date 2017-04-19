package com.zakati.base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.ta.R;
import com.ta.utils.Lg;
import com.ta.utils.LocaleHelper;
import com.ta.utils.PrefMgr;
import com.ta.utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Created by rahil on 30/8/16.
 */
public class App extends Application  {


    private static String TAG = App.class.getName();
    public static boolean isConnected;
    private static App mAppInstance;
    private BroadcastReceiver mNetStateChangeReceiver;
    private static int numRunningActivities = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(getNetworkStateReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mAppInstance = this;
        isConnected = Utils.isNetworkAvailable(this);
        PrefMgr.init(this);
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        registerActivityLifecycleCallbacks(getCallback());
        LocaleHelper.init(this);
    }

    private ActivityLifecycleCallbacks getCallback() {
        return new EmptyLifecycleCallbacks() {

            @Override
            public void onActivityStarted(Activity activity) {
                numRunningActivities++;
            }

            @Override
            public void onActivityStopped(Activity activity) {

                numRunningActivities--;
                if (numRunningActivities == 0) {
                    Lg.i(TAG, "No running activities left, app has likely entered the background.");
                } else {
                    Lg.i(TAG, numRunningActivities + " activities remaining");
                }
            }
        };

    }


    public static App getInstance() {
        return mAppInstance;
    }


    @Override
    public void onTerminate() {
        unregisterReceiver(mNetStateChangeReceiver);
        super.onTerminate();

    }

    public static boolean isAppInForeground() {
        return numRunningActivities > 0;
    }

    private BroadcastReceiver getNetworkStateReceiver() {
        mNetStateChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isConnected = Utils.isNetworkAvailable(App.this);
                Lg.e("Network State", isConnected ? "Network Available" : "Network Unavailable");

            }
        };
        return mNetStateChangeReceiver;
    }





}
