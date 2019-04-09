package com.godwin.ui;

import javax.swing.*;
import java.awt.*;

public class ErrorAlertDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel jLabel;

    public ErrorAlertDialog(String message, Dimension dimension) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        jLabel.setText(message);

        int height = 100;
        int width = 200;

        setPreferredSize(new Dimension(width, height));
        setSize(new Dimension(width, height));
        int locationWidth = (dimension.width / 2 - width / 2);
        int locationHeight = (dimension.height / 2 - height / 2);

        setLocation(locationWidth, locationHeight);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.setVisible(false);
    }

    private void onOK() {
        // add your code here

        dispose();
    }
}
