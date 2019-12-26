package com.fromLab.action;

import com.fromLab.GUI.Modal.SelectTaskDialog;
import com.fromLab.utils.GUIUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author wsh
 * @date 2019-12-24
 */
public class SelectTaskAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // TODO: insert action logic here
        Integer width = 1040;
        Integer height = 500;
        SelectTaskDialog selectTaskDialog = new SelectTaskDialog();
        selectTaskDialog.pack();
        selectTaskDialog.setBounds(GUIUtils.getCenterX(width), GUIUtils.getCenterY(height), width, height);
        selectTaskDialog.setVisible(true);
    }
}
