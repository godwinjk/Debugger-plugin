package com.godwin.debugger.networking.communication;

import android.content.Context;

/**
 * Created by Godwin on 5/4/2018 6:04 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class CommunicationService {

    public static ResponseManager getResponseService(Context context) {
        return new ResponseManagerImpl(context);
    }
}
