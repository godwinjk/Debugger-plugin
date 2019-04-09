package com.godwin.command;

import com.godwin.common.Logger;
import com.godwin.command.receiver.GenericReceiver;
import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import org.apache.http.util.TextUtils;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.android.sdk.AndroidSdkUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class WifiCommand implements Command {

    private String androidSdkPath;
    private boolean success;
    private Project project;

    @Override
    public boolean run(Project project, IDevice device, AndroidFacet facet, String packageName) {
        this.project = project;
        GenericReceiver receiver = new GenericReceiver();
        try {
            WindowManager.getInstance().getStatusBar(project).setInfo("ADB WIFI : scan IP address ...");
            device.executeShellCommand("ip -f inet addr show wlan0", receiver);
        } catch (AdbCommandRejectedException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return false;
        } catch (TimeoutException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return false;
        } catch (ShellCommandUnresponsiveException e) {
            e.printStackTrace();
            Logger.e(TextUtils.isEmpty(e.getMessage()) ? "Killing process after timeout" : e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return false;
        }

        String ipAddress = getIpAddress(receiver);
        if (ipAddress == null) {
            Logger.e("Can't connect to wireless or get a valid IP address.");
        } else {
            File adbPath = AndroidSdkUtils.getAdb(project);
            if (adbPath != null) {
                androidSdkPath = adbPath.getAbsolutePath();
            } else {
                Logger.e("Android SDK path not found");
                return false;
            }

            if (adbTcpip()) {
                try {
                    Thread.sleep(500);
                    success = adbWificonnect(ipAddress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        return success;
    }

    private String getIpAddress(GenericReceiver receiver) {

        for (String line : receiver.getAdbOutputLines()) {
//            WindowManager.getInstance().getStatusBar(project).setInfo("ADB WIFI : " + line);
            if (line.contains("inet") && line.contains("wlan0")) {
                int end = line.indexOf("/");
                try {
                    return line.substring(5, end);
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private boolean adbTcpip() {
        try {
            WindowManager.getInstance().getStatusBar(project).setInfo("ADB WIFI : restarting in TCP mode port: 5555 ...");
            Process process = Runtime.getRuntime().exec(androidSdkPath + "adb tcpip 5555");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                if (line.contains("Logger.e")) {
                    Logger.e(line);
                    return false;
                }
                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean adbWificonnect(String ipAddress) {
        boolean connected = false;
        try {
            WindowManager.getInstance().getStatusBar(project).setInfo("ADB WIFI : connect to " + ipAddress + "...");
            Process process = Runtime.getRuntime().exec(androidSdkPath + "adb connect " + ipAddress);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            String message = null;
            while ((line = in.readLine()) != null) {
                if (line.contains("connected")) {
                    connected = true;
                }
                message = line;
            }
            if (connected) {
                Logger.i(message);
                return true;
            } else {
                Logger.e(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
        }
        return false;
    }
}
