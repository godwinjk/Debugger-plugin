package com.godwin.ui;

import javax.swing.*;

/**
 * Created by Godwin on 4/21/2018 12:32 PM for json.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public interface IDebugWidget {
    void createDebugSession();

    void closeCurrentDebugSession();

    JComponent getComponent();
}
