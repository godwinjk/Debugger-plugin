package com.godwin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin on 5/8/2018 10:53 AM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DTableDetails implements IBaseModel {
    private List<List<String>> tableDetails;

    public List<List<String>> getTableDetails() {
        return tableDetails;
    }

    public void setTableDetails(List<List<String>> tableDetails) {
        this.tableDetails = tableDetails;
    }

    public void addTableRow(List<String> row) {
        if (null == tableDetails) {
            this.tableDetails = new ArrayList<>();
        }
        this.tableDetails.add(row);
    }
}
