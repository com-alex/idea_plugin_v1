package com.fromLab.action.taskToolWindowAction;

import com.fromLab.GUI.Modal.LoginModal;
import com.fromLab.GUI.window.TaskToolWindow;
import com.fromLab.utils.GUIUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
/**
 * @author zyh
 * @date 2020-03-31
 */
public class AuthenticationAction extends AnAction {

    private TaskToolWindow taskToolWindow;

    public AuthenticationAction(@Nullable String text, @Nullable String description,
                                @Nullable Icon icon, TaskToolWindow taskToolWindow) {
        super(text, description, icon);
        this.taskToolWindow = taskToolWindow;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if(!taskToolWindow.getIsLogin()){
            Integer width = 600;
            Integer height = 200;
            LoginModal loginModal = new LoginModal(taskToolWindow);
            loginModal.pack();
            loginModal.setBounds(GUIUtils.getCenterX(width), GUIUtils.getCenterY(height), width, height);
            loginModal.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "You have been authorized!",
                    "Tips", JOptionPane.WARNING_MESSAGE);
        }

    }
}
