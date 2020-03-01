package com.fromLab.action;

import com.fromLab.GUI.Modal.LoginModal;
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
        Integer width = 600;
        Integer height = 200;
        LoginModal loginModal = new LoginModal();
        loginModal.pack();
        loginModal.setBounds(GUIUtils.getCenterX(width), GUIUtils.getCenterY(height), width, height);
        loginModal.setVisible(true);
    }
}
