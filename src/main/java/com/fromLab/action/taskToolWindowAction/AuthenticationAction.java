package com.fromLab.action.taskToolWindowAction;

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

    public AuthenticationAction(@Nullable String text, @Nullable String description, @Nullable Icon icon){
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("登录");
    }
}
