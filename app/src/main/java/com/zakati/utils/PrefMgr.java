package com.zakati.utils;

import android.content.Context;

import com.ta.base.App;


/**
 * Created by rahil on 9/5/16.
 */
public class PrefMgr {

    private static final String JWT_EXPIRY_TIME = "jwt_expiry_time";
    public static final String JWT_TOKEN = "jwt_token";
    private final PreferenceUtil mPref;

    private static PrefMgr instance;

    public static PrefMgr init(Context context) {
        if (instance == null) {
            instance = new PrefMgr(context);
        }
        return instance;
    }


    public static PrefMgr get() {
        if (instance == null) {
            init(App.getInstance());
        }
        return instance;
    }


    private PrefMgr(Context context) {
        mPref = new PreferenceUtil(context);
    }

    public boolean isLoggedIn() {
        return mPref.getBooleanValue(PrefConstants.IS_LOGGED_IN);
    }


    public void clear() {
        mPref.clear();
    }

    public void clearAll() {
        mPref.clearAll();
    }


    public String getUserId() {
        return "ds";
    }

    public void saveJwt(String token) {
        mPref.save(JWT_TOKEN,token);
    }

    public void setTokenExpiry(long time) {
        mPref.save(JWT_EXPIRY_TIME,time);
    }

    public long getTokenExpiry() {
        return mPref.getLongValue(JWT_EXPIRY_TIME);
    }

    public String getJwt() {
        return mPref.getStringValue(JWT_TOKEN);
    }
}
