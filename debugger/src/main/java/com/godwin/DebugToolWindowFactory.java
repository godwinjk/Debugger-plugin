package com.godwin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Godwin on 4/22/2018 9:29 AM for DatabaseDebug.
 *
 * @author : Godwin Joseph Kurinjikattu
 */
public class DebugToolWindowFactory implements ToolWindowFactory {
    public static final String TOOL_WINDOW_ID = "Debugger";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        DebugComponent apiDebuggerView = DebugComponent.getInstance(project);
        apiDebuggerView.initParser(toolWindow);
    }
}
