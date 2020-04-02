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

    private JLabel label;
    private JRadioButton radioBtn01 = new JRadioButton("update the function's comment");
    private JRadioButton radioBtn02 = new JRadioButton("choose the correct task");
    private ButtonGroup btnGroup = new ButtonGroup();
    public ChooseDialog(ArrayList<String> old,String cur){
        super(true);
        this.old=old;
        this.cur=cur;
        init();
    }
    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
       label= new JLabel("The work your doing now is not the task you selected," +
                "please update the task's comment or choose right task"+"\n"+
                "current taskId is"+
                old.toString()+" you choice is "+cur);
        btnGroup.add(radioBtn01);
        btnGroup.add(radioBtn02);
        JPanel button=new JPanel();
        button.add(radioBtn01);
        button.add(radioBtn02);
        dialogPanel.add(label,BorderLayout.NORTH);
        dialogPanel.add(button,BorderLayout.CENTER);
       // JButton choose=new JButton("choose");
       // dialogPanel.add(choose,BorderLayout.SOUTH);
        return dialogPanel;
    }
    public int getStatus(){
        if(radioBtn01.isSelected())
            return 1;
        else if(radioBtn02.isSelected())
            return 2;
        return 0;
    }


    public static void main(String[] args) {
    }
}
