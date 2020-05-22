package com.fromLab.GUI.component;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author wsh
 * @date 2020-01-07
 * The component to handle the color of the priority
 */
public class TableRenderer implements TableCellRenderer {
    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Integer rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String content = (String) table.getValueAt(row, column);
            renderer.setForeground(this.SetColor(content));
        }

        return renderer;
    }

    private Color SetColor(String content) {
        if ("Low".equals(content)) {
            return new Color(34, 139, 34);
        } else if ("Normal".equals(content)) {
            return new Color(205, 205, 0);
        } else if ("High".equals(content)) {
            return Color.ORANGE;
        } else if ("Immediate".equals(content)) {
            return Color.RED;
        } else {
            return Color.BLACK;
        }
    }
}
