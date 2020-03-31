package com.fromLab.GUI.window;

import com.fromLab.action.taskToolWindowAction.AuthenticationAction;
import com.fromLab.action.taskToolWindowAction.RefreshAction;
import com.fromLab.loader.IconsLoader;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author zyh
 * @date 2020-03-31
 */
public class TaskToolWindow implements ToolWindowFactory {

    private ToolWindow customToolWindow;
    private JPanel panel;
    private JPanel contentPanel;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        customToolWindow = toolWindow;

        //界面
        panel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.blue);
        panel.add(contentPanel);

        RefreshAction refreshAction = new RefreshAction("Refresh", "Refresh", AllIcons.Actions.Refresh);
        AuthenticationAction authenticationAction = new AuthenticationAction("Authentication",
                "Authentication", IconsLoader.AUTHENTICATION);
        ((ToolWindowImpl) toolWindow).setTitleActions(refreshAction, authenticationAction);

        //将界面放进去toolWindow里
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, null, false);
        toolWindow.getContentManager().addContent(content);
    }
}
