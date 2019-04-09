package com.godwin.ui;


import com.godwin.model.DApplication;
import com.godwin.model.DDatabase;
import com.godwin.network.ClientSocket;
import com.godwin.network.SocketPool;
import com.godwin.network.communication.DataCommunicationListener;
import com.godwin.network.communication.DataObserver;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Godwin on 4/24/2018 6:20 PM for DatabaseDebug.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class MainWindowWidget {
    private JPanel container;
    private JList deviceList;
    private JLabel lbInfo;

    private Project mProject;
    private Disposable parent;

    private boolean isRunning = true;
    private List<ClientSocket> sockets = new ArrayList<>();

    private SessionWindow mSessionWindow;
    private ClientSocket mSelectSocket;


    DataCommunicationListener listener = new DataCommunicationListener() {
        @Override
        public void onGetDbData(List<DDatabase> databaseList) {

        }

        @Override
        public void onGetTableDetails(List<List<String>> table, List<String> header) {

        }

        @Override
        public void onGetQueryResult(List<List<String>> table, List<String> header) {

        }

        @Override
        public void onGetQueryFail(int errorCode, String errorMessage) {

        }

        @Override
        public void onCloseClient(ClientSocket socket) {
            if (mSelectSocket != null && socket != null) {
                if (mSelectSocket.getSocket().getUid() == socket.getSocket().getUid()) {
                    if (mSessionWindow != null) {
                        mSessionWindow.close();
                    }
                }
            }
        }

        @Override
        public void onConnectClient(ClientSocket socket) {
            runDeviceDetection();
        }
    };

    public MainWindowWidget(Project mProject, Disposable parent) {
        this.mProject = mProject;
        this.parent = parent;

        this.lbInfo.setText("<html>Connect a phone and start debugging. <br><a href=https://github.com/godwinjk/Debugger-plugin>How to use?</a></html>");

//        runDeviceDetection();
        beautify();
        deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        deviceList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;

            JList theList = (JList) e.getSource();
            int index = theList.getSelectedIndex();
            if (sockets != null && sockets.size() > index && index > -1) {
                if (mSelectSocket != null && mSelectSocket.getUid() == sockets.get(index).getUid())
                    return;

                mSelectSocket = sockets.get(index);
                mSessionWindow = new SessionWindow(mProject, parent, mSelectSocket);
                mSessionWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        mSessionWindow = null;
                        mSelectSocket = null;
                    }
                });
            }
        });
        DataObserver.getInstance().subscribe(listener);
    }

    private void beautify() {
        deviceList.setCellRenderer(new CellRenderer());
    }

    private void runDeviceDetection() {
        Thread thread = new Thread(() -> {
            while (isRunning) {
                try {

                    List<ClientSocket> clientSockets = SocketPool.getInstance().listConnectedSockets();

                    setItemsToList(clientSockets);
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void setItemsToList(List<ClientSocket> clientSockets) {
        sockets.clear();
        List<DApplication> applications = new ArrayList<>();
        for (ClientSocket socket : clientSockets) {
            if (socket.getApplication() != null) {
                applications.add(socket.getApplication());
                sockets.add(socket);
            }
        }

        lbInfo.setVisible(applications.size() <= 0);

        deviceList.setListData(applications.toArray());
    }

    public JPanel getContainer() {
        return this.container;
    }

}
