package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.DateChooserJButton;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GUIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * @author wsh
 * @date 2019-12-27
 */
public class SetDueTimeModal extends JFrame {

    private static final String SET_FROM_DUE_TIME = "from";
    private static final String SET_TO_DUE_TIME = "to";

    private SelectTaskDialog jDialog;
    private String type;
    private JPanel timePanel;
    private JLabel timeLabel;
    private DateChooserJButton dateChooserJButton;
    private JButton okButton;
    private JButton cancelButton;

    public SetDueTimeModal(String type, SelectTaskDialog selectTaskDialog, Date date){
        this.jDialog = selectTaskDialog;
        this.type = type;
        timePanel = new JPanel();
        timePanel.setBounds(0, 0, 300, 150);
        timePanel.setLayout(null);
        timeLabel = new JLabel("Due Time:");
        timeLabel.setBounds(50,0, 100, 100);
        timePanel.add(timeLabel);

        dateChooserJButton = new DateChooserJButton(date);
        dateChooserJButton.setBounds(120, 38, 150, 25);
        timePanel.add(dateChooserJButton);

        okButton = new JButton("ok");
        okButton.setBounds(85, 90, 50, 30);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                onOk();
            }
        });

        cancelButton = new JButton("cancel");
        cancelButton.setBounds(170, 90, 60, 30);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        timePanel.add(okButton);
        timePanel.add(cancelButton);
        this.add(timePanel);
        this.pack();
        this.setBounds(GUIUtils.getCenterX(300), GUIUtils.getCenterY(150), 300, 150);
        this.setVisible(true);
    }


    private void onOk(){
        String dateStirng = this.dateChooserJButton.getText();
        String date = DateUtils.date2String(DateUtils.string2Date(dateStirng));
        if(SET_FROM_DUE_TIME.equals(this.type)){
            this.jDialog.getFromDatePickerButton().setText(date);
        }else{
            this.jDialog.getToDatePickerButton().setText(date);
        }
        this.setVisible(false);
        this.jDialog.setVisible(true);
    }

    private void onCancel(){
        this.setVisible(false);
        this.jDialog.setVisible(true);
    }

}
