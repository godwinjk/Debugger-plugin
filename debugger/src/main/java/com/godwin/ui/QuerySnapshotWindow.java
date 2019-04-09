package com.godwin.ui;

import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Created by Godwin on 5/23/2018 2:03 PM for plugin.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class QuerySnapshotWindow {
    private JPanel container;
    private JBTable mTable;
    private JFrame mFrame;

    private List<String> header;
    private List<List<String>> data;

    public QuerySnapshotWindow(List<List<String>> data, List<String> header, Dimension dimension) {
        super();

        this.header = header;
        this.data = data;

        mFrame = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 5;

        height = height == 0 ? 300 : height;
        width = width == 0 ? 1000 : width;
        int locationWidth = (screenSize.width / 2 - width / 2);
        int locationHeight = (screenSize.height / 2 - height / 2);


        mFrame.setSize(new Dimension(width, height));
        mFrame.setLocation(locationWidth, locationHeight);

        mFrame.setResizable(true);
        mFrame.setTitle("Debug window");
        mFrame.setLayout(new BorderLayout());
        mFrame.add(container, BorderLayout.CENTER);
        mFrame.pack();

        populateTable();
    }

    public void setVisible() {
        mFrame.setVisible(true);
        mFrame.toFront();

    }

    private void populateTable() {
        mTable = new JBTable();
        container.removeAll();

        container.add(mTable, BorderLayout.CENTER);
        container.repaint();
        container.revalidate();

        String[][] array = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            try {
                array[i] = row.toArray(new String[row.size()]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DefaultTableModel model = new DefaultTableModel(array, header.toArray());

        mTable.setModel(model);
    }
}
