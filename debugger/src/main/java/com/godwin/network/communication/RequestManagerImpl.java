package com.godwin.network.communication;


import com.godwin.common.Common;
import com.godwin.model.DDatabase;
import com.godwin.model.DTable;
import com.google.gson.JsonObject;

/**
 * Created by Godwin on 5/4/2018 6:02 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
class RequestManagerImpl implements RequestManager {

    @Override
    public String getDeviceDetails() {
        JsonObject object = new JsonObject();
        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_APP_DETAILS);
        return object.toString();
    }

    @Override
    public String getDbRequest() {
        JsonObject object = new JsonObject();
        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_DB);
        return object.toString();
    }

    @Override
    public String getTableRequest() {
        JsonObject object = new JsonObject();
        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_TABLE);

        return object.toString();
    }

    @Override
    public String getTableDetailsRequest(DTable table) {
        JsonObject object = new JsonObject();
        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_TABLE_DETAILS);
        object.addProperty(Common.KEY_TABLE_NAME, table.getName());
        object.addProperty(Common.KEY_DB_NAME, table.getDatabaseName());

        return object.toString();
    }

    @Override
    public String getExecuteQueryRequest(DDatabase database, String query) {
        JsonObject object = new JsonObject();

        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_EXECUTE_QUERY);
        object.addProperty(Common.KEY_DB_NAME, database.getName());
        object.addProperty(Common.KEY_QUERY, query);

        return object.toString();
    }

    @Override
    public String getCloseRequest() {
        JsonObject object = new JsonObject();
        object.addProperty(Common.REQUEST_TYPE, Common.REQUEST_CLOSE);
        return object.toString();
    }
}
