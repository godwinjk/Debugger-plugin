package com.godwin.network;


import com.godwin.network.godwin.communication.MessageContract;
import com.godwin.model.DApplication;
import com.godwin.model.DDatabase;
import com.godwin.model.DTable;
import com.godwin.network.communication.CommunicationService;

/**
 * Created by Godwin on 4/26/2018 10:16 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class ClientSocket {
    private MessageContract socket;
    private long connectedTime;
    private DApplication application;

    /**
     * Instantiates a new Client socket.
     *
     * @param socket        the socket
     * @param connectedTime the connected time
     */
    public ClientSocket(MessageContract socket, long connectedTime) {
        this.socket = socket;
        this.connectedTime = connectedTime;
    }

    public int getUid() {
        return socket.getUid();
    }

    /**
     * Gets socket.
     *
     * @return the socket
     */
    public MessageContract getSocket() {
        return socket;
    }

    /**
     * Sets socket.
     *
     * @param socket the socket
     */
    public void setSocket(MessageContract socket) {
        this.socket = socket;
    }


    /**
     * Gets connected time.
     *
     * @return the connected time
     */
    public long getConnectedTime() {
        return connectedTime;
    }

    /**
     * Sets connected time.
     *
     * @param connectedTime the connected time
     */
    public void setConnectedTime(long connectedTime) {
        this.connectedTime = connectedTime;
    }


    /**
     * Request app details.
     */
    public void requestAppDetails() {
        String string = CommunicationService.getRequestService().getDeviceDetails();
        send(string);
    }

    /**
     * Request db details.
     */
    public void requestDbDetails() {
        String string = CommunicationService.getRequestService().getDbRequest();
        send(string);
    }

    /**
     * Request table details.
     *
     * @param table
     */
    public void requestTableDetails(DTable table) {
        String string = CommunicationService.getRequestService().getTableDetailsRequest(table);
        send(string);
    }

    public void executeQuery(DDatabase database, String query) {
        String string = CommunicationService.getRequestService().getExecuteQueryRequest(database, query);
        send(string);
    }

    private void send(String string) {
        if (socket.getSocket().isConnected()) {
            socket.sendMessage(string);
        }
    }

    /**
     * Gets application.
     *
     * @return the application
     */
    public DApplication getApplication() {
        return application;
    }

    /**
     * Sets application.
     *
     * @param application the application
     */
    public void setApplication(DApplication application) {
        this.application = application;
    }
}
