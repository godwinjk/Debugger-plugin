package com.godwin.network.communication;


import com.godwin.model.DDatabase;
import com.godwin.network.ClientSocket;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by Godwin on 5/7/2018 7:58 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DataObserver {
    private static final Object MUTEX = new Object();
    private static DataObserver sObserver;

    private List<DataCommunicationListener> mListeners;

    private DataObserver() {
        //prevent object creation
        mListeners = new ArrayList<>();

    }

    public static DataObserver getInstance() {
        if (null == sObserver) {
            synchronized (MUTEX) {
                if (null == sObserver) {
                    sObserver = new DataObserver();
                }
            }
        }
        return sObserver;
    }

    public void subscribe(DataCommunicationListener listener) {
        try {
            if (null == listener)
                throw new NullPointerException();

            if (!mListeners.contains(listener)) {
                mListeners.add(listener);
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    public void unSubcribe(DataCommunicationListener listener) {
        try {
            mListeners.remove(listener);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    public void publish(List<DDatabase> databases) {
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onGetDbData(databases);
        }
    }

    public void publishTable(List<List<String>> table, List<String> header) {
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onGetTableDetails(table, header);
        }
    }

    public void publishQueryResult(List<List<String>> table, List<String> header) {
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onGetQueryResult(table, header);
        }
    }

    public void publishQueryFail(int errorCode, String errorMessage) {
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onGetQueryFail(errorCode, errorMessage);
        }
    }
    public void publishSocketClose(ClientSocket socket){
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onCloseClient(socket);
        }
    }
    public void publishOnSocketConnect(ClientSocket socket){
        for (int i = 0; i < mListeners.size(); i++) {
            DataCommunicationListener listener = mListeners.get(i);
            listener.onConnectClient(socket);
        }
    }
}
