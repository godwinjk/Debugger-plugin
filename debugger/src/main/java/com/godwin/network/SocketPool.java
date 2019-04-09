package com.godwin.network;


import com.godwin.network.godwin.communication.MessageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin on 4/26/2018 10:15 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class SocketPool {
    private static final Object MUTEX = new Object();
    private static SocketPool sSocketPool;
    private final List<ClientSocket> mClientSockets;

    private SocketPool() {
        //prevent object creation
        mClientSockets = new ArrayList<>();
    }

    public static SocketPool getInstance() {
        if (null == sSocketPool) {
            synchronized (MUTEX) {
                sSocketPool = new SocketPool();
            }
        }
        return sSocketPool;
    }

    public void addConnection(ClientSocket clientSocket) {
        mClientSockets.add(clientSocket);
    }

    public void removeConnection(ClientSocket clientSocket) {
        mClientSockets.remove(clientSocket);
    }

    public void removeConnection(MessageContract webSocket) {
        try {
            for (int i = 0; i < mClientSockets.size(); i++) {
                if (mClientSockets.get(i).getSocket().getUid() == (Integer) webSocket.getUid()) {
                    mClientSockets.remove(i);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clear() {
        mClientSockets.clear();
    }

    public List<ClientSocket> listConnectedSockets() {
        return mClientSockets;
    }

    public ClientSocket getClientSocket(int uId) {
        for (ClientSocket clientSocket : mClientSockets) {
            if (clientSocket.getSocket().getUid() == uId) {
                return clientSocket;
            }
        }
        return null;
    }
}
