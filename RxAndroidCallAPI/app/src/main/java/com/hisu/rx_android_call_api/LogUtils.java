package com.hisu.rx_android_call_api;

import android.util.Log;

public class LogUtils {

    private static final String TAG = "LogUtils";

    public static void log(String type, String msg) {
        Log.e(TAG, type + "\t := \t" + msg);
    }
}