package com.godwin.debugger.model;

/**
 * Created by Godwin on 4/25/2018 12:01 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DDatabase implements IBaseModel {
    private String name;
    private String uri;

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
}
