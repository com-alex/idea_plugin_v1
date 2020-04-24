package com.fromLab.loader;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
/**
 * @author zyh
 * @date 2020-03-31
 */
public interface IconsLoader {
    Icon AUTHENTICATION = IconLoader.getIcon("/icons/authentication.png");
    // 64*64
    Icon SUCCESS_ICON = IconLoader.getIcon("/icons/success.png");
    Icon ERROR_ICON = IconLoader.getIcon("/icons/error.png");
    Icon WARNING_ICON = IconLoader.getIcon("/icons/warning.png");
}
