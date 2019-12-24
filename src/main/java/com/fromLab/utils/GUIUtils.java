package com.fromLab.utils;

import java.awt.*;

/**
 * @author wsh
 * @date 2019-12-17
 */
public class GUIUtils {

    public static Integer getCenterX(Integer frameWidth){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Integer screenWidth = screenSize.width;
        Integer framePosition = (screenWidth - frameWidth) / 2;
        return framePosition;
    }

    public static Integer getCenterY(Integer frameHeight){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Integer screenHeight = screenSize.height;
        Integer framePosition = (screenHeight - frameHeight) / 2;
        return framePosition;
    }
}
