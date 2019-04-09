package com.godwin.debugger.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.godwin.debugger.Debugger;
import com.godwin.debugger.common.Common;
import com.godwin.debugger.model.DQuery;
import com.godwin.debugger.model.DTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Godwin on 5/7/2018 6:56 PM for Adb.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DatabaseUtil {
    public JSONArray tableStructure() {
        Context context = Debugger.getContext();
        JSONArray database = new JSONArray();
        try {
            String[] dbList = context.databaseList();
            if (dbList != null && dbList.length > 0) {
                for (String databaseName : dbList) {
                    if (databaseName.contains("-journal"))
                        continue;
                    File databasePath = context.getDatabasePath(databaseName);

                    JSONObject dbObj = new JSONObject();
                    dbObj.put(Common.KEY_DB_NAME, databaseName);
                    dbObj.put(Common.KEY_DB_PATH, databasePath.getAbsoluteFile());

                    SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath.getAbsolutePath(), null, 0);
                    Cursor cursorTable = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

                    JSONArray array = new JSONArray();
                    if (cursorTable.moveToFirst()) {
                        while (!cursorTable.isAfterLast()) {
                            JSONObject tblObj = new JSONObject();

                            String tableName = cursorTable.getString(cursorTable.getColumnIndex("name"));
                            tblObj.put(Common.KEY_TABLE_NAME, tableName);

                            array.put(tblObj);

                            cursorTable.moveToNext();
                        }
                    }
                    dbObj.put(Common.KEY_TABLES, array);
                    database.put(dbObj);

                    db.close();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return database;
    }

    public JSONArray getTableDetails(DTable table) {
        File databasePath = Debugger.getContext().getDatabasePath(table.getDatabaseName());
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath.getAbsolutePath(), null, 0);

        Cursor cursorCSV = db.rawQuery("SELECT * FROM " + table.getName(), null);
        JSONArray array = getTableDetailsFromCursor(cursorCSV);
        db.close();
        return array;
    }

    public Object executeQuery(DQuery dQuery) {
        File databasePath = Debugger.getContext().getDatabasePath(dQuery.getDatabaseName());
        SQLiteDatabase db = SQLiteDatabase.openDatabase(databasePath.getAbsolutePath(), null, 0);

        String query = dQuery.getQuery();
        if (query.toLowerCase().contains("select")) {
            try {
                Cursor cursor = db.rawQuery(query, null);
                return getTableDetailsFromCursor(cursor);
            } catch (Exception e) {
                JSONObject object = new JSONObject();
                try {
                    object.put(Common.KEY_ERR_CODE, Common.ERR_CODE_EXCEPTION);
                    object.put(Common.KEY_ERR_MSG, e.getLocalizedMessage());
                } catch (JSONException ex) {
                    e.printStackTrace();
                }
                return object;
            }
        } else {
            JSONObject object = new JSONObject();
            try {
                object.put(Common.KEY_ERR_CODE, Common.ERR_CODE_NO_SUPPORT);
                object.put(Common.KEY_ERR_MSG, Common.ERR_MSG_NO_SUPPORT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object;
        }
    }

    private JSONArray getTableDetailsFromCursor(Cursor cursorCSV) {
        JSONArray jsonArray = new JSONArray();
        try {
            if (cursorCSV != null) {
                String[] columnNames = cursorCSV.getColumnNames();
                JSONObject object = new JSONObject();
                JSONArray rowArray = new JSONArray();
                for (int i = 0; i < columnNames.length; i++) {
                    rowArray.put(columnNames[i]);
                }
                object.put(Common.KEY_ROW, rowArray);
                jsonArray.put(object);
                if (cursorCSV.moveToFirst()) {
                    while (cursorCSV.moveToNext()) {
                        rowArray = new JSONArray();
                        object = new JSONObject();
                        for (int i = 0; i < cursorCSV.getColumnCount(); i++) {
                            String data = cursorCSV.getString(i);
                            rowArray.put(TextUtils.isEmpty(data) ? " " : data);
                        }
                        object.put(Common.KEY_ROW, rowArray);
                        jsonArray.put(object);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}