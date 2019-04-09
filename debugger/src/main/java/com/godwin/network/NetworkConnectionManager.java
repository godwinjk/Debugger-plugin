package com.godwin.network;


import com.godwin.adb.AdbCommandExecutor;
import com.godwin.common.Logger;
import com.godwin.network.godwin.communication.CommunicationListener;
import com.godwin.network.godwin.communication.MessageContract;
import com.godwin.network.communication.CommunicationService;
import com.godwin.network.communication.DataCommunicationListener;
import com.godwin.network.communication.DataObserver;
import com.godwin.network.communication.ResponseManager;
import com.godwin.network.godwin.communication.TcpCallbackSubscriber;
import com.godwin.network.godwin.server.ServerManager;
import com.godwin.network.godwin.util.Error;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Godwin on 4/25/2018 12:20 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class NetworkConnectionManager implements CommunicationListener {
    private static final Object MUTEX = new Object();
    private static NetworkConnectionManager sManager;
    private final String TAG = getClass().getSimpleName();
    private AdbCommandExecutor mExecutor;
    private boolean isRunning = true;

    private DataCommunicationListener mListener;

    private NetworkConnectionManager() {
        ServerManager.INSTANCE.startServer(PortAllocationManager.getInstance().getPort(0));
        //prevent object creation
        mExecutor = new AdbCommandExecutor();
        TcpCallbackSubscriber.INSTANCE.subscribe(this);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NetworkConnectionManager getInstance() {
        if (null == sManager) {
            synchronized (MUTEX) {
                sManager = new NetworkConnectionManager();
            }
        }
        return sManager;
    }

    private void addToRegistry(MessageContract socket) {
        ClientSocket clientSocket = new ClientSocket(socket, System.currentTimeMillis());
        SocketPool.getInstance().addConnection(clientSocket);

        DataObserver.getInstance().publishOnSocketConnect(SocketPool.getInstance().getClientSocket(socket.getUid()));

        clientSocket.requestAppDetails();
    }

    private void removeFromRegistry(MessageContract socket) {
        DataObserver.getInstance().publishSocketClose(SocketPool.getInstance().getClientSocket(socket.getUid()));
        SocketPool.getInstance().removeConnection(socket);
    }

    private void removeAllSockets() {
        SocketPool.getInstance().clear();
    }

    /**
     * Is running boolean.
     *
     * @return the boolean
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Sets running.
     *
     * @param running the running
     */
    public void setRunning(boolean running) {
        isRunning = running;
    }

    public DataCommunicationListener getListener() {
        return mListener;
    }

    public void setListener(DataCommunicationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClientConnected(@NotNull MessageContract messageContract) {
        addToRegistry(messageContract);
    }

    @Override
    public void onClientDisconnected(@NotNull MessageContract messageContract, @Nullable Error error) {
        removeFromRegistry(messageContract);
    }

    @Override
    public void onMessage(@NotNull String s, @NotNull MessageContract messageContract) {
        ResponseManager manager = CommunicationService.getResponseService();
        manager.processResponse(messageContract, s);

        Logger.i("Message: " + s);
    }
}
