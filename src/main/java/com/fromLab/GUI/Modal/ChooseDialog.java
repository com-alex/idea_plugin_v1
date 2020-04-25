package com.fromLab.GUI.Modal;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChooseDialog extends DialogWrapper {

    private ArrayList<String> old;
    private String cur;

    public ArrayList<String> getOld() {
        return old;
    }

    public void setOld(ArrayList<String> old) {
        this.old = old;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }


    public ChooseDialog(ArrayList<String> old,String cur){
        super(true);
        this.old=old;
        this.cur=cur;
        init();
        setTitle("Update Comment");
    }
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JPanel labelPanel1 = new JPanel(new BorderLayout());
        JPanel labelPanel2 = new JPanel(new BorderLayout());
        JPanel labelPanel3 = new JPanel(new BorderLayout());
        labelPanel1.add(new JLabel("This function is used by a new Task!"));
        labelPanel2.add(new JLabel("Old taskId: "+ old.toString()+"          Current TaskId: "+cur));
        labelPanel3.add(new JLabel("Will you update the comment?"));
        dialogPanel.add(labelPanel1,BorderLayout.NORTH);
        dialogPanel.add(labelPanel2,BorderLayout.CENTER);
        dialogPanel.add(labelPanel3,BorderLayout.SOUTH);


        return dialogPanel;
    }


}
