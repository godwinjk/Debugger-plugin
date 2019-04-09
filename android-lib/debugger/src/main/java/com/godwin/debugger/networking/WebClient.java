package com.godwin.debugger.networking;

import android.content.Context;


import com.godwin.debugger.common.Logger;
import com.godwin.debugger.networking.communication.CommunicationService;
import com.godwin.debugger.networking.communication.ResponseManager;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

/**
 * Created by Godwin on 5/4/2018 2:10 PM for Adb.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class WebClient extends WebSocketClient {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;

    public WebClient(Context context, URI serverUri) {
        super(serverUri);
        this.mContext = context;
    }

    public WebClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public WebClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public WebClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public WebClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Logger.d(TAG, "onOpen: ");
    }

    @Override
    public void onMessage(String message) {
        Logger.d(TAG, "onMessage: " + message);
        ResponseManager manager = CommunicationService.getResponseService(mContext);
        String response = manager.processResponse(message);
        send(response);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Logger.d(TAG, "onClose: " + code + " reason " + reason + " " + remote);
    }

    @Override
    public void onError(Exception ex) {
        Logger.d(TAG, "onError: ");
        ex.printStackTrace();
    }
}
