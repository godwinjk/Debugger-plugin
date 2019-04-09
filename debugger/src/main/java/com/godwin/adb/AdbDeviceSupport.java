package com.godwin.adb;


import com.godwin.model.DDevice;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin on 4/26/2018 6:41 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class AdbDeviceSupport {

    public static List<DDevice> getDeviceList(String deviceList) {
        List<DDevice> devices = new ArrayList<>();
        if (!TextUtils.isEmpty(deviceList)) {
            deviceList = deviceList.replace("          ", "");
            String[] arr = deviceList.split("\n");
            if (arr.length > 0) {

                for (int i = 1; i < arr.length; i++) {
                    String[] rawarr = arr[i].split(" ");
                    if (rawarr.length > 0) {
                        DDevice device = new DDevice();
                        try {
                            device.setAdbKey(rawarr[0]);
                            device.setProduct(rawarr[2]);
                            device.setModel(rawarr[3]);
                            device.setName(rawarr[4]);
                            device.setTransportId(rawarr[5]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        devices.add(device);
                    }
                }
            }
        }
        return devices;
    }
}
