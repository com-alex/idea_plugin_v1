package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TaskProgressSlider;
import com.fromLab.entity.Status;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.loader.IconsLoader;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.GUIUtils;
import com.fromLab.utils.OpenprojectURL;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wsh
 * @date 2019-12-26
 */
public class StopTaskModal extends JFrame {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private TaskService taskService;
    private OpenprojectURL openprojectURL;

    private SelectTaskDialog dialog;
    private Integer taskProgress = 0;
    private Task selectedTask;
    private Integer timeSpent;
    private Status selectedStatus;
    private String []statusDataSourceView;
    private Status[] statusDataSource = {Status.NEW, Status.InProgress, Status.Closed, Status.OnHold, Status.Rejected};

    //放滑块与状态选择控件
    private JPanel contentPanel;
    private TaskProgressSlider taskProgressSlider;
    private JLabel taskProgressLabel;
    private JLabel taskStatusLabel;
    private JComboBox comboBox;
    private JPanel buttonPanel;
    private JButton confirmButton;
    private JButton backButton;



    public StopTaskModal(Task selectedTask, Integer taskProgress, OpenprojectURL openprojectURL){
        this.statusDataSourceView = new String[this.statusDataSource.length];
        for (int i = 0; i < statusDataSource.length; i++) {
            statusDataSourceView[i] = statusDataSource[i].getName();
        }

        this.setLayout(new BorderLayout());


        this.taskService = new TaskServiceImpl();
        this.selectedTask = selectedTask;
        this.taskProgress = taskProgress;
        this.openprojectURL = openprojectURL;
        this.contentPanel = new JPanel();
        contentPanel.setLayout(null);

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

        this.comboBox = new JComboBox(statusDataSourceView);
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
        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setBounds(GUIUtils.getCenterX(500), GUIUtils.getCenterY(300), 500, 300);
        this.setVisible(true);
    }

    public StopTaskModal(Task selectedTask, Integer taskProgress,  SelectTaskDialog dialog, OpenprojectURL openprojectURL){
        this(selectedTask, taskProgress, openprojectURL);
        this.dialog = dialog;
    }



    private void watchTaskProgress(){
        this.taskProgress = taskProgressSlider.getValue();
        taskProgressLabel.setText("Progress: " + this.taskProgress + "%");
    }

    private void updateTask(){

        this.selectedStatus = statusDataSource[this.comboBox.getSelectedIndex()];
        String originalUrl = openprojectURL.getOpenProjectURL();
        String result = this.taskService.updateStatusAndProgress(openprojectURL, this.selectedTask.getTaskId(),
                this.selectedTask.getLockVersion(), this.selectedStatus, this.taskProgress);

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
        if (result.equals(SUCCESS)) {
            this.dispose();
            JOptionPane.showOptionDialog(null, "Save successfully", "Tips",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, IconsLoader.SUCCESS_ICON, jButtons, jButtons[0]);
            if(this.dialog != null){
                this.dialog.setVisible(true);
            }

        }else{
            Integer taskId = this.selectedTask.getTaskId();
            openprojectURL.setOpenProjectURL(originalUrl.substring(0, originalUrl.lastIndexOf("/")+1));
            try {
                this.selectedTask = this.taskService.getTaskById(openprojectURL, taskId);
            } catch (BusinessException e) {
                this.selectedTask = null;
            }
            openprojectURL.setOpenProjectURL(originalUrl.substring(0, originalUrl.lastIndexOf("/")+1));
            if(this.selectedTask == null){
                JOptionPane.showOptionDialog(null, "Fail to save", "Tips",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON, jButtons, jButtons[0]);
            }else {
                String response = this.taskService.updateStatusAndProgress(openprojectURL, this.selectedTask.getTaskId(),
                        this.selectedTask.getLockVersion(), this.selectedStatus, this.taskProgress);
                if (StringUtils.equals(response, SUCCESS)) {
                    this.dispose();
                    JOptionPane.showOptionDialog(null, "Save successfully", "Tips",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, IconsLoader.SUCCESS_ICON, jButtons, jButtons[0]);
                    if(this.dialog != null){
                        this.dialog.setVisible(true);
                    }
                } else {
                    JOptionPane.showOptionDialog(null, "Fail to save", "Tips",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON, jButtons, jButtons[0]);
                }
            }
        }

    }


    private void onCancel(){
        this.setVisible(false);
        if(this.dialog != null){
            this.dialog.dispose();
        }
    }

}
