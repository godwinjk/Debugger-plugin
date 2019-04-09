package com.godwin.debugger.networking;

import com.godwin.debugger.Debugger;
import com.godwin.debugger.common.Logger;
import com.godwin.debugger.networking.communication.CommunicationService;
import com.godwin.debugger.networking.communication.ResponseManager;
import com.godwin.debugger.networking.godwin.client.ClientManager;
import com.godwin.debugger.networking.godwin.communication.MessageContract;
import com.godwin.debugger.networking.godwin.communication.TcpCallbackSubscriber;
import com.godwin.network.godwin.communication.CommunicationListener;
import com.godwin.debugger.networking.godwin.util.Error;
import com.godwin.network.godwin.worker.ThreadPoolProvider;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Godwin on 5/8/2018 6:20 PM for L_and_B.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class NetworkConnectionManager implements CommunicationListener {
    private static final Object MUTEX = new Object();
    private static final String TAG = NetworkConnectionManager.class.getSimpleName();
    private static NetworkConnectionManager sInstance = null;
    private ClientManager manager;
    private Timer timer;
    private MessageContract messageContract;

    public static NetworkConnectionManager getInstance() {
        if (null == sInstance) {
            synchronized (MUTEX) {
                sInstance = new NetworkConnectionManager();
            }
        }
        return sInstance;
    }

    private NetworkConnectionManager() {
        TcpCallbackSubscriber.INSTANCE.subscribe(this);
    }

    public void connect() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (manager == null || manager.getSocket() == null || !manager.getSocket().isConnected()) {
                    manager = new ClientManager();
                    manager.connect("127.0.0.1", 45569);
                }
            }
        }, 100, 20 * 1000);
    }

    @Override
    public void onClientConnected(MessageContract socket) {
        messageContract = socket;
    }

    @Override
    public void onMessage(String message, MessageContract socket) {
        Logger.d(TAG, "onMessage: " + message);
        ResponseManager manager = CommunicationService.getResponseService(Debugger.getContext());
        String response = manager.processResponse(message);
        socket.sendMessage(response);
    }

    @Override
    public void onClientDisconnected(MessageContract socket, Error error) {
        if (timer != null) {
            timer = null;
        }
        manager = null;
        socket = null;
    }
}
