package com.fromLab.action.taskToolWindowAction;

import com.fromLab.GUI.window.TaskToolWindow;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
/**
 * @author zyh
 * @date 2020-03-31
 */
public class RefreshAction extends AnAction {

    private TaskToolWindow taskToolWindow;

    public RefreshAction(@Nullable String text, @Nullable String description,
                         @Nullable Icon icon, TaskToolWindow taskToolWindow){
        super(text, description, icon);
        this.taskToolWindow = taskToolWindow;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.taskToolWindow.refresh();
    }
}
