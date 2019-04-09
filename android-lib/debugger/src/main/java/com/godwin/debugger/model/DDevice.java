package com.godwin.debugger.model;

/**
 * Created by Godwin on 4/26/2018 3:33 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DDevice implements IBaseModel {
    private String adbKey;
    private String name;
    private String product;
    private String model;
    private String transportId;

    public String getAdbKey() {
        return adbKey;
    }

    public void setAdbKey(String adbKey) {
        this.adbKey = adbKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append("\n");
        builder.append(getModel()).append("\n");
        builder.append(getProduct()).append("\n");
        builder.append(getAdbKey());
        return builder.toString();
    }
}
