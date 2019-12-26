package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TaskProgressSlider;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.GUIUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-26
 */
public class StopTaskModal extends JFrame {

    private TaskService taskService;

    private SelectTaskDialog dialog;
    private Integer taskProgress = 0;
    private Integer taskId;
    private Integer timeSpent;
    private String []statusDataSource = {"New", "To be scheduled", "Scheduled", "In progress", "Closed", "On hold", "Rejected"};

    //放滑块与状态选择控件
    private JPanel contentPanel;
    private TaskProgressSlider taskProgressSlider;
    private JLabel taskProgressLabel;
    private JLabel taskStatusLabel;
    private JComboBox comboBox;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private JButton backButton;



    public StopTaskModal(Integer taskId, Integer taskProgress, Integer timeSpent, SelectTaskDialog dialog){
        this.setLayout(new BorderLayout());

        this.dialog = dialog;
        this.taskService = new TaskServiceImpl();
        this.taskId = taskId;
        this.timeSpent = timeSpent;
        this.taskProgress = taskProgress;
        this.contentPanel = new JPanel();
        contentPanel.setLayout(null);
        this.add(contentPanel);

        taskProgressSlider = new TaskProgressSlider(taskProgress);
        taskProgressSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                watchTaskProgress();
            }
        });
        taskProgressSlider.setBounds(100, 50, 300, 40);
        contentPanel.add(taskProgressSlider);

        this.taskProgressLabel = new JLabel("Progress: " + taskProgress + "%");
        this.taskProgressLabel.setBounds(210, 120, 100, 20);
        contentPanel.add(taskProgressLabel);

        this.taskStatusLabel = new JLabel("Status:");
        this.taskStatusLabel.setBounds(180, 170, 100, 20);
        contentPanel.add(taskStatusLabel);

        this.comboBox = new JComboBox(statusDataSource);
        this.comboBox.setBounds(240, 165, 150, 30);
        contentPanel.add(comboBox);
        this.add(contentPanel, BorderLayout.CENTER);

        this.buttonPanel = new JPanel();
        this.confirmButton = new JButton("confirm");
        confirmButton.setSize(50, 20);
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
        buttonPanel.add(confirmButton);

        this.backButton = new JButton("back");
        backButton.setSize(50, 20);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setBounds(GUIUtils.getCenterX(500), GUIUtils.getCenterY(300), 500, 300);
        this.setVisible(true);
    }



    private void watchTaskProgress(){
        this.taskProgress = taskProgressSlider.getValue();
        taskProgressLabel.setText("Progress: " + this.taskProgress + "%");
    }

    private void updateTask(){
        Task task = this.taskService.queryTaskByTaskId(this.taskId);
        //保存设置的进度
        task.setProgress(this.taskProgress + "%");
        //保存设置的状态
        task.setStatus((String) this.comboBox.getSelectedItem());
        //保存工作的耗时
        task.setTimeSpent(this.timeSpent + task.getTimeSpent());
        taskService.saveOrUpdateTask(task);
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
        JOptionPane.showOptionDialog(null, "Save successfully", "Tips", JOptionPane.WARNING_MESSAGE, 0, null, jButtons, jButtons[0]);
        this.dialog.resetTableDataSource();
        this.dialog.setVisible(true);
    }

}
