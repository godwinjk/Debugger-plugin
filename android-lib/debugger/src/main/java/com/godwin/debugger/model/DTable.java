package com.godwin.debugger.model;

/**
 * Created by Godwin on 4/25/2018 12:03 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DTable implements IBaseModel{
    private String name;
    private String uri;
    private String databaseName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
