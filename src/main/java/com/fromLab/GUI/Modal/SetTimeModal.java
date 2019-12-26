package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.DateChooserJButton;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.GUIUtils;
import com.fromLab.utils.ReflectionUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-17
 * 设置开始时间与结束时间的模态框
 */
public class SetTimeModal extends JFrame {
    //常量，防止出现魔法值
    private static final String SET_START_TIME_MODAL = "start";
    private static final String SET_END_TIME_MODAL = "end";

    private SelectTaskDialog jDialog;
    private TaskVO taskVO;
    private TaskServiceImpl taskService;
    private String flag;
    private JPanel timePanel;
    private JLabel timeLabel;
    private DateChooserJButton dateChooserJButton;
    private JButton okButton;
    private JButton cancelButton;

    public SetTimeModal(){
        taskService = new TaskServiceImpl();
        timePanel = new JPanel();
        timePanel.setBounds(0, 0, 300, 150);
        timePanel.setLayout(null);
        timeLabel = new JLabel("Start Time:");
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
    public SetTimeModal(String flag, SelectTaskDialog dialog, TaskVO taskVO){
        this();
        this.taskVO = taskVO;
        this.jDialog = dialog;
        this.flag = flag;
        if(this.flag.indexOf(SET_START_TIME_MODAL) > -1){
            timeLabel.setText("Start Time:");
        }
        else{
            timeLabel.setText("End Time:");
        }

    }

    private void onOk(){
        Integer taskId = taskVO.getTaskId();
        Task task = taskService.queryTaskByTaskId(taskId);
        //更新数据
        String time = this.dateChooserJButton.getText();
        if(this.flag.contains(SET_START_TIME_MODAL)){
            task.setStartTime(time);
        } else if (this.flag.contains(SET_END_TIME_MODAL)) {
            task.setEndTime(time);
        }
        taskService.saveOrUpdateTask(task);
        //刷新表格数据源
        List<TaskVO> dataSource = taskService.queryAllShowTask(1);
        this.setVisible(false);


        this.jDialog.getTaskTable().setModel(new TaskTableModel(dataSource));
        this.jDialog.getTaskTable().getColumnModel().getColumn(0).setPreferredWidth(100);
        this.jDialog.getTaskTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        this.jDialog.getTaskTable().getColumnModel().getColumn(2).setPreferredWidth(100);
        this.jDialog.getTaskTable().getColumnModel().getColumn(3).setPreferredWidth(100);
        this.jDialog.getTaskTable().getColumnModel().getColumn(4).setPreferredWidth(100);
        this.jDialog.getTaskTable().getColumnModel().getColumn(5).setPreferredWidth(150);
        this.jDialog.getTaskTable().getColumnModel().getColumn(6).setPreferredWidth(150);
        this.jDialog.getTaskTable().getColumnModel().getColumn(7).setPreferredWidth(166);
        this.jDialog.getTaskTable().getColumnModel().getColumn(8).setPreferredWidth(100);
        this.jDialog.getTaskTable().getColumnModel().getColumn(9).setPreferredWidth(100);
        this.jDialog.getTaskTable().setRowHeight(30);
        this.jDialog.getTaskTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.jDialog.getTaskTable().setShowHorizontalLines(false);
        this.jDialog.getTaskTable().setShowVerticalLines(false);
        this.jDialog.setVisible(true);

    }

    private void onCancel(){
        this.setVisible(false);
        this.jDialog.setVisible(true);
    }
}
