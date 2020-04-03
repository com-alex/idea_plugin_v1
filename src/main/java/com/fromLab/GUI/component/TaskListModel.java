package com.fromLab.GUI.component;

import javax.swing.*;

/**
 * @author zyh
 * @date 2020-03-28
 */
public class TaskListModel extends AbstractListModel {

    String values[];

    public TaskListModel(){
        super();
        this.values = new String[0];
    }
    public TaskListModel(String[] values){
        super();
        this.values = values;
    }

    public String[] getValues() {
        return values;
    }

    @Override
    public int getSize() {
        return values.length;
    }

    @Override
    public Object getElementAt(int index) {
        return values[index];
    }
}
