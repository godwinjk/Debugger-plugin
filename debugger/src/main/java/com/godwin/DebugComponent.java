package com.godwin;

import com.godwin.adb.DeviceDetectionService;
import com.godwin.ui.DebugWidget;
import com.godwin.ui.IDebugWidget;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerEx;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by Godwin on 4/22/2018 9:30 AM for DatabaseDebug.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DebugComponent implements ProjectComponent {
    private final Project myProject;
    private final String TAG = getClass().getSimpleName();

    protected DebugComponent(Project project) {
        this.myProject = project;
    }

    public static DebugComponent getInstance(Project project) {
        return project.getComponent(DebugComponent.class);
    }

    public void initParser(ToolWindow toolWindow) {
        Content content = createParserContentPanel(toolWindow);
        content.setCloseable(true);
        toolWindow.getContentManager().addContent(content);
        ((ToolWindowManagerEx) ToolWindowManager.getInstance(myProject)).addToolWindowManagerListener(getToolWindowListener());


        DeviceDetectionService.getInstance().setDaemonRunning(true);
        DeviceDetectionService.getInstance().startDetecting(myProject);
    }

    private Content createParserContentPanel(ToolWindow toolWindow) {
        toolWindow.setToHideOnEmptyContent(true);

        DebugToolWindowPanel panel = new DebugToolWindowPanel(PropertiesComponent.getInstance(myProject), toolWindow);
        Content content = ContentFactory.SERVICE.getInstance().createContent(panel, "", false);

        IDebugWidget debuggerWidget = createContent(content);
        ActionToolbar toolBar = createToolBar(debuggerWidget);

        panel.setToolbar(toolBar.getComponent());
        panel.setContent(debuggerWidget.getComponent());

        return content;
    }

    private IDebugWidget createContent(Content content) {
        IDebugWidget debuggerWidget = new DebugWidget(myProject, content);
        debuggerWidget.createDebugSession();

        return debuggerWidget;
    }

    private ActionToolbar createToolBar(IDebugWidget debuggerWidget) {
        DefaultActionGroup group = new DefaultActionGroup();

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.TOOLBAR, group, false);
        toolbar.setOrientation(SwingConstants.VERTICAL);
        return toolbar;
    }

    private ToolWindowManagerListener getToolWindowListener() {
        return new ToolWindowManagerListener() {
            @Override
            public void toolWindowRegistered(@NotNull String s) {
//                Logger.d(TAG, "DebuggerComponent.toolWindowRegistered");
            }

            @Override
            public void stateChanged() {
                ToolWindow toolWindow = ToolWindowManager.getInstance(myProject).getToolWindow(DebugToolWindowFactory.TOOL_WINDOW_ID);

                if (toolWindow != null) {

                    if (toolWindow.isVisible() && toolWindow.getContentManager().getContentCount() == 0) {
//                        Logger.d(TAG, "DebuggerComponent.isVisible ContentCount>0");
                        initParser(toolWindow);
                    }
                }
            }
        };
    }
}
