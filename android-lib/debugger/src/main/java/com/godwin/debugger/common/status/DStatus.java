package com.godwin.debugger.common.status;

/**
 * Created by Godwin on 4/25/2018 11:33 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DStatus extends DError {
    public static final int SUCCESS = 1;
    public static final String MSG_SUCCESS = "Success";

    public DStatus(String message, int code) {
        super(message, code);
    }
}
