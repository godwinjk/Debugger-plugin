package com.godwin.debugger.model;

/**
 * Created by Godwin on 5/23/2018 11:11 AM for L_and_B.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DQuery implements IBaseModel {
    private String databaseName;
    private String query;

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
