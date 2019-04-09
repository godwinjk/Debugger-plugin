package com.godwin.network.communication;


import com.godwin.model.DDatabase;
import com.godwin.network.ClientSocket;

import java.util.List;

/**
 * Created by Godwin on 5/7/2018 2:42 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public interface DataCommunicationListener {
    void onGetDbData(List<DDatabase> databaseList);

    void onGetTableDetails(List<List<String>> table, List<String> header);

    void onGetQueryResult(List<List<String>> table, List<String> header);

    void onGetQueryFail(int errorCode, String errorMessage);

    void onCloseClient(ClientSocket socket);

    void onConnectClient(ClientSocket socket);
}
