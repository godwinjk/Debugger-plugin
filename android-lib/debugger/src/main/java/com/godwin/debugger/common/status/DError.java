package com.godwin.debugger.common.status;

/**
 * Created by Godwin on 4/25/2018 11:29 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DError {
    public static final int FAILED = 0;

    private String message;
    private int code;
    private int type;

    public DError(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
