package com.godwin.debugger.common;

import android.util.Log;

/**
 * Created by Godwin on 4/21/2018 12:32 PM for json.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class Logger {
    public static void i(String tag, String message) {
        Log.i(tag, "Debugger: " + message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, "Debugger: " + message);
    }

    public static void e(String tag, String message) {
        Log.e(tag, "Debugger: " + message);
    }

    public static void e(Throwable throwable) {
        throwable.printStackTrace();
    }

}
