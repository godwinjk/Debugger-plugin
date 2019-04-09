package com.godwin.network.communication;


import com.godwin.model.DDatabase;
import com.godwin.model.DTable;

/**
 * Created by Godwin on 5/4/2018 5:37 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public interface RequestManager {

    String getDeviceDetails();

    String getDbRequest();

    String getTableRequest();

    String getTableDetailsRequest(DTable table);

    String getExecuteQueryRequest(DDatabase database, String query);

    String getCloseRequest();

}
