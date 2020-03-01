package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.DateChooserJButton;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GUIUtils;
import com.fromLab.utils.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-17
 * 设置开始时间与结束时间的模态框
 */
public class SetTimeModal extends JFrame {

    private final static String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private final static String API_KEY="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    private SelectTaskDialog jDialog;
    private Task task;
    private String endDateCustomFieldName;
    private TaskServiceImpl taskService;
    private JPanel timePanel;
    private JLabel timeLabel;
    private DateChooserJButton dateChooserJButton;
    private JButton okButton;
    private JButton cancelButton;

    public SetTimeModal(SelectTaskDialog dialog, Task task, String endDateCustomFieldName){
        this.task = task;
        this.jDialog = dialog;
        this.endDateCustomFieldName = endDateCustomFieldName;
        taskService = new TaskServiceImpl();
        timePanel = new JPanel();
        timePanel.setBounds(0, 0, 300, 150);
        timePanel.setLayout(null);
        timeLabel = new JLabel("End Date:");
        timeLabel.setBounds(50,0, 100, 100);
        timePanel.add(timeLabel);

        dateChooserJButton = new DateChooserJButton();
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
        //更新数据
        String time = this.dateChooserJButton.getText();
        String date = DateUtils.date2String(DateUtils.string2Date(time));
        this.taskService.updateEndDate(OPENPROJECT_URL, API_KEY, this.task.getTaskId(), this.task.getLockVersion(), date, this.endDateCustomFieldName);
        JButton[] jButtons = new JButton[1];
        JButton button = new JButton("ok");
        jButtons[0] = button;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window win = SwingUtilities.getWindowAncestor(button);
                win.dispose();
            }
        });
        this.dispose();
        JOptionPane.showOptionDialog(null, "Save successfully", "Tips",
                JOptionPane.WARNING_MESSAGE, 0, null, jButtons, jButtons[0]);
        this.jDialog.resetTableDataSource();
        this.jDialog.setVisible(true);


    }

    private void onCancel(){
        this.setVisible(false);
        this.jDialog.setVisible(true);
    }
}
