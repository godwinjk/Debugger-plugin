package com.godwin.adb;

import com.godwin.model.DDevice;
import com.godwin.network.PortAllocationManager;
import com.intellij.openapi.project.Project;

import java.util.List;

/**
 * Created by Godwin on 4/26/2018 6:23 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DeviceDetectionService implements Runnable {

    private static DeviceDetectionService mService;

    private boolean isDaemonRunning = true;

    private Project myProject;
    private final AdbCommandExecutor executor;

    private DeviceDetectionService() {
        //prevent object creation
        executor = new AdbCommandExecutor();
    }

    public static DeviceDetectionService getInstance() {
        if (null == mService) {
            synchronized (DeviceDetectionService.class) {
                mService = new DeviceDetectionService();
            }
        }
        return mService;
    }

    public void startDetecting(Project myProject) {
        this.myProject = myProject;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (isDaemonRunning()) {

            String deviceList = executor.adbDevicesWithAllValue(myProject);
            List<DDevice> devices = AdbDeviceSupport.getDeviceList(deviceList);
            if (devices != null && devices.size() > 0) {
                int allocated = PortAllocationManager.getInstance().getPort(0);
                executor.adbPortReverse(myProject, allocated, allocated);
                break;
            }
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDaemonRunning() {
        return isDaemonRunning;
    }

    public void setDaemonRunning(boolean daemonRunning) {
        isDaemonRunning = daemonRunning;
    }
}
