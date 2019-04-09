package com.godwin.model;

import com.godwin.common.HexConversionHelper;

import javax.swing.*;

/**
 * Created by Godwin on 5/7/2018 1:05 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DApplication implements IBaseModel {
    private String packageName;
    private String appName;
    private String version;
    private ImageIcon icon;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public void setIcon(String baseIcon) {
        try {
            byte[] bytes = HexConversionHelper.hexToByteArray(baseIcon);
            this.icon = new ImageIcon(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return appName + " (" + packageName + ")";
    }
}
