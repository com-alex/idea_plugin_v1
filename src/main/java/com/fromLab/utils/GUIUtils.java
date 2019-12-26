package com.fromLab.utils;

import javax.swing.*;
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

    public static void setLabelText(JLabel jLabel, String longString) {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
    }
}
