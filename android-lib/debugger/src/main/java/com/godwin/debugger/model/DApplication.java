package com.godwin.debugger.model;

/**
 * Created by Godwin on 5/7/2018 1:05 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DApplication implements IBaseModel {
    private String packageName;
    private String appName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
