package com.godwin.network.communication;


import com.godwin.common.Common;
import com.godwin.network.godwin.communication.MessageContract;
import com.godwin.model.DApplication;
import com.godwin.model.DDatabase;
import com.godwin.model.DTable;
import com.godwin.network.ClientSocket;
import com.godwin.network.SocketPool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by Godwin on 5/7/2018 2:52 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
class ResponseManagerImpl implements ResponseManager {
    @Override
    public void processResponse(MessageContract socket, String response) {

        JSONObject object = null;
        try {
            object = new JSONObject(response);


            int responseCode = object.optInt(Common.RESPONSE_TYPE);

            switch (responseCode) {
                case Common.REQUEST_APP_DETAILS:
                    processGetAppDetails(socket, object);
                    break;
                case Common.REQUEST_DB:
                    processDatabase(socket, object);
                    break;
                case Common.REQUEST_TABLE_DETAILS:
                    processTableDetails(socket, object);
                    break;
                case Common.REQUEST_EXECUTE_QUERY:
                    processQuery(socket, object);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processDatabase(MessageContract socket, JSONObject object) throws JSONException {

        int responseCode = object.optInt(Common.RESPONSE_TYPE);

        JSONArray array = object.optJSONArray(Common.KEY_DATA);
        List<DDatabase> databases = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject dbObject = array.optJSONObject(i);
            String dbName = dbObject.optString(Common.KEY_DB_NAME);
            String dbPath = dbObject.optString(Common.KEY_DB_PATH);

            DDatabase database = new DDatabase();
            database.setName(dbName);
            database.setUri(dbPath);

            List<DTable> tables = new ArrayList<>();
            JSONArray tblArray = dbObject.optJSONArray(Common.KEY_TABLES);
            for (int j = 0; j < tblArray.length(); j++) {
                JSONObject tableObject = tblArray.optJSONObject(j);

                String tableName = tableObject.optString(Common.KEY_TABLE_NAME);
                DTable table = new DTable();
                table.setDatabaseName(dbName);
                table.setName(tableName);
                tables.add(table);
            }
            database.setTables(tables);
            databases.add(database);
        }
        DataObserver.getInstance().publish(databases);
    }

    private void processGetAppDetails(MessageContract socket, JSONObject object) throws JSONException {
        int responseCode = object.optInt(Common.RESPONSE_TYPE);

        String packageName = object.optString(Common.KEY_PKG);
        String name = object.optString(Common.KEY_NAME);
        String version = object.optString(Common.KEY_VERSION);
        String base64Icon = object.optString(Common.KEY_ICON);

        DApplication application = new DApplication();
        application.setPackageName(packageName);
        application.setAppName(name);
        application.setVersion(version);
        application.setIcon(base64Icon);

        ClientSocket clientSocket = SocketPool.getInstance().getClientSocket(socket.getUid());
        if (null != clientSocket) {
            clientSocket.setApplication(application);
        }
        DataObserver.getInstance().publishApplication(clientSocket);
    }

    private void processTableDetails(MessageContract socket, JSONObject object) throws JSONException {
        int responseCode = object.optInt(Common.RESPONSE_TYPE);

        JSONArray array = object.optJSONArray(Common.KEY_DATA);
        List<String> header = new ArrayList<>();
        List<List<String>> table = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject rowObject = array.optJSONObject(i);
            JSONArray rowArray = rowObject.optJSONArray(Common.KEY_ROW);
            List<String> row = new ArrayList<>();
            for (int j = 0; j < rowArray.length(); j++) {
                String data = rowArray.optString(j);
                row.add(data);
            }
            table.add(row);
        }
        header = table.get(0);
        table.remove(0);
        DataObserver.getInstance().publishTable(table, header);
    }

    private void processQuery(MessageContract socket, JSONObject object) throws JSONException {
        int responseCode = object.optInt(Common.RESPONSE_TYPE);

        JSONArray array = object.optJSONArray(Common.KEY_DATA);
        JSONObject errorElement = object.optJSONObject(Common.KEY_ERR);
        if (array != null) {

            List<String> header = new ArrayList<>();
            List<List<String>> table = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject rowObject = array.optJSONObject(i);
                JSONArray rowArray = rowObject.optJSONArray(Common.KEY_ROW);
                List<String> row = new ArrayList<>();
                for (int j = 0; j < rowArray.length(); j++) {
                    String data = rowArray.optString(j);
                    row.add(data);
                }
                table.add(row);
            }
            header = table.get(0);
            table.remove(0);
            DataObserver.getInstance().publishQueryResult(table, header);
        } else if (errorElement != null) {
            DataObserver.getInstance().publishQueryFail(errorElement.optInt(Common.KEY_ERR_CODE), errorElement.optString(Common.KEY_ERR_MSG));
        }
    }
}
