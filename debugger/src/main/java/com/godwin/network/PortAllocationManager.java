package com.godwin.network;


import com.godwin.common.Common;

/**
 * Created by Godwin on 4/26/2018 10:28 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class PortAllocationManager {
    public static final int NUMBER_OF_PORTS = 5;
    private static final Object MUTEX = new Object();

    private static final int[] PORTS = {Common.COMMON_PORT1,
            Common.COMMON_PORT2,
            Common.COMMON_PORT3,
            Common.COMMON_PORT4,
            Common.COMMON_PORT5,};
    private static PortAllocationManager sManager;
    public int[] selectedPorts = {0, 0, 0, 0, 0,};//0-not taken,1- taken,2-taken but already used

    private PortAllocationManager() {
        //prevent object creation
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PortAllocationManager getInstance() {
        if (null == sManager) {
            synchronized (MUTEX) {
                sManager = new PortAllocationManager();
            }
        }
        return sManager;
    }

    public int getPort(int index) {
        int port = 0;
        if (index >= 0 && index <= 5) {
            port = PORTS[index];
            selectedPorts[index] = 1;
        } else {
            throw new ArrayIndexOutOfBoundsException("Please give a index value [0..5]");
        }
        return port;
    }

    public void markErrorOnPort(int index) {
        if (index >= 0 && index <= 5) {
            selectedPorts[index] = 2;
        } else {
            throw new ArrayIndexOutOfBoundsException("Please give a index value [0..5]");
        }
    }

    public int getAllocatedPort() {
        return Common.COMMON_PORT1;//todo change to selected port
    }
}
