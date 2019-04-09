package com.godwin.debugger;

import android.content.Context;

import com.godwin.debugger.networking.NetworkConnectionManager;

import java.net.URISyntaxException;

/**
 * Created by Godwin on 5/7/2018 6:54 PM for Adb.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class Debugger {
    private static Context sContext;

    public static void initialize(Context context) {
        sContext = context;
        NetworkConnectionManager.getInstance().connect();
    }

    public static Context getContext() {
        return sContext;
    }

}
