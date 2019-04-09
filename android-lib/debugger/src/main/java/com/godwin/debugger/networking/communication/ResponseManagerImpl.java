package com.godwin.debugger.networking.communication;

import android.content.Context;

import com.godwin.debugger.common.Common;
import com.godwin.debugger.common.Utils;
import com.godwin.debugger.database.DatabaseUtil;
import com.godwin.debugger.model.DQuery;
import com.godwin.debugger.model.DTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Godwin on 5/7/2018 2:52 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class ResponseManagerImpl implements ResponseManager {
    private Context mContext;

    /**
     * Instantiates a new Response manager.
     *
     * @param mContext the m context
     */
    public ResponseManagerImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String processResponse(String message) {
        try {
            JSONObject object = new JSONObject(message);
            int requestType = object.optInt(Common.REQUEST_TYPE);

            switch (requestType) {
                case Common.REQUEST_APP_DETAILS:
                    return processAppDetailsRequest(object).toString();
                case Common.REQUEST_DB:
                    return processDatabaseRequest(object).toString();
                case Common.REQUEST_TABLE_DETAILS:
                    return processTableDetailsRequest(object).toString();
                case Common.REQUEST_EXECUTE_QUERY:
                    return processExecuteQueryRequest(object).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getInvalidRequest().toString();
    }

    private JSONObject getInvalidRequest() {

        JSONObject response = new JSONObject();
        try {
            response.put(Common.RESPONSE_TYPE, -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject processAppDetailsRequest(JSONObject object) {
        int requestType = object.optInt(Common.REQUEST_TYPE);

        JSONObject response = new JSONObject();
        try {
            response.put(Common.REQUEST_TYPE, requestType);
            response.put(Common.RESPONSE_TYPE, requestType);

            response.put(Common.KEY_PKG, mContext.getPackageName());
            response.put(Common.KEY_NAME, Utils.getApplicationName(mContext));
            response.put(Common.KEY_VERSION, Utils.getApplicationVersion(mContext));
            response.put(Common.KEY_ICON, Utils.getApplicationIcon(mContext));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject processDatabaseRequest(JSONObject object) {
        int requestType = object.optInt(Common.REQUEST_TYPE);

        JSONObject response = new JSONObject();
        try {
            response.put(Common.REQUEST_TYPE, requestType);
            response.put(Common.RESPONSE_TYPE, requestType);
            JSONArray database = new DatabaseUtil().tableStructure();
            response.put(Common.KEY_DATA, database);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject processTableDetailsRequest(JSONObject object) {
        int requestType = object.optInt(Common.REQUEST_TYPE);
        String tableName = object.optString(Common.KEY_TABLE_NAME);
        String dbName = object.optString(Common.KEY_DB_NAME);

        DTable table = new DTable();
        table.setDatabaseName(dbName);
        table.setName(tableName);

        JSONObject response = new JSONObject();
        try {
            response.put(Common.REQUEST_TYPE, requestType);
            response.put(Common.RESPONSE_TYPE, requestType);
            JSONArray tableDetails = new DatabaseUtil().getTableDetails(table);
            response.put(Common.KEY_DATA, tableDetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject processExecuteQueryRequest(JSONObject object) {
        int requestType = object.optInt(Common.REQUEST_TYPE);
        String dbName = object.optString(Common.KEY_DB_NAME);
        String query = object.optString(Common.KEY_QUERY);

        DQuery dQuery = new DQuery();
        dQuery.setDatabaseName(dbName);
        dQuery.setQuery(query);

        JSONObject response = new JSONObject();
        try {
            response.put(Common.REQUEST_TYPE, requestType);
            response.put(Common.RESPONSE_TYPE, requestType);
            Object data = new DatabaseUtil().executeQuery(dQuery);
            if (data instanceof JSONObject) {
                response.put(Common.KEY_ERR, (JSONObject) data);
            } else if (data instanceof JSONArray) {
                response.put(Common.KEY_DATA, (JSONArray) data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
