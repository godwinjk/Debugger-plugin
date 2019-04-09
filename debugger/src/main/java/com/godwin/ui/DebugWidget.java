package com.godwin.ui;


import com.godwin.adb.DeviceDetectionService;
import com.godwin.network.NetworkConnectionManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * crea
 */
public class DebugWidget extends JPanel implements IDebugWidget {
    private Project mProject;
    private Disposable mParent;
    private JBPanel<JBPanel> mPanel;
    private JComponent mInnerDebuggerWidget;

    public DebugWidget(Project project, Disposable disposable) {
        super(new BorderLayout());
        mProject = project;
        mParent = disposable;
        mPanel = new JBPanel<>(new BorderLayout());

        mPanel.add(this, BorderLayout.CENTER);
    }

    @Override
    public void createDebugSession() {
        JComponent innerDebuggerWidget = createInnerDebuggerWidget();
        if (mInnerDebuggerWidget == null) {
            mInnerDebuggerWidget = innerDebuggerWidget;
            add(mInnerDebuggerWidget, BorderLayout.CENTER);
        }
    }

    @Override
    public void closeCurrentDebugSession() {

    }

    private JComponent createInnerDebuggerWidget() {
        DeviceDetectionService.getInstance().startDetecting(mProject);
        NetworkConnectionManager.getInstance();

        return new MainWindowWidget(mProject, mParent).getContainer();
    }

    @Override
    public JComponent getComponent() {
        return mPanel;
    }
}
