package com.godwin.network.communication;


import com.godwin.common.Common;
import com.godwin.model.DDatabase;
import com.godwin.model.DTable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Godwin on 5/4/2018 6:02 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
class RequestManagerImpl implements RequestManager {

    @Override
    public String getDeviceDetails() {
        JSONObject object = new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_APP_DETAILS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public String getDbRequest() {
        JSONObject object = new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_DB);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @Override
    public String getTableRequest() {
        JSONObject object = new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_TABLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    @Override
    public String getTableDetailsRequest(DTable table) {
        JSONObject object = new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_TABLE);
            object.put(Common.REQUEST_TYPE, Common.REQUEST_TABLE_DETAILS);
            object.put(Common.KEY_TABLE_NAME, table.getName());
            object.put(Common.KEY_DB_NAME, table.getDatabaseName());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return object.toString();
    }

    @Override
    public String getExecuteQueryRequest(DDatabase database, String query) {
        JSONObject object = new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_EXECUTE_QUERY);
            object.put(Common.KEY_DB_NAME, database.getName());
            object.put(Common.KEY_QUERY, query);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }

    @Override
    public String getCloseRequest() {
       JSONObject object =new JSONObject();
        try {
            object.put(Common.REQUEST_TYPE, Common.REQUEST_CLOSE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }
}
