package com.fromLab.GUI.Modal;

import com.fromLab.VO.TaskDetailVO;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.GUIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author wsh
 * @date 2019-12-26
 * 查询任务的detail的模态框
 */
public class TaskDetailModal extends JFrame {

    private JDialog dialog;
    private Integer taskId;
    private TaskDetailVO taskDetailVO;
    private TaskService taskService;

    //5,2 GridLayout
    private JPanel contentPanel;
    private JLabel taskNameLabel;
    private JLabel taskNameText;
    private JLabel projectNameLabel;
    private JLabel projectNameText;
    private JLabel dueTimeLabel;
    private JLabel dueTimeText;
    private JLabel taskDetailLabel;
    private JScrollPane scrollPane;
    private JLabel taskDetailText;

    private JPanel buttonPanel;
    private JButton backButton;

    public TaskDetailModal(){


    }

    public TaskDetailModal(JDialog dialog, Integer taskId){
        this.dialog = dialog;
        this.taskId = taskId;
        this.taskService = new TaskServiceImpl();
        initModal();
    }

    private void initModal(){

        //获取数据源
        taskDetailVO = taskService.queryTaskDetailByTaskId(taskId);

        this.setLayout(new BorderLayout());
        this.contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(5, 2));

        taskNameLabel = new JLabel("Task Name: ");
        contentPanel.add(taskNameLabel);

        taskNameText = new JLabel("" + taskDetailVO.getTaskName());
        contentPanel.add(taskNameText);

        projectNameLabel = new JLabel("Project Name: ");
        contentPanel.add(projectNameLabel);

        projectNameText = new JLabel("" + taskDetailVO.getProjectName());
        contentPanel.add(projectNameText);

        dueTimeLabel = new JLabel("Due Time: ");
        contentPanel.add(dueTimeLabel);

        dueTimeText = new JLabel("" + taskDetailVO.getDueTime());
        contentPanel.add(dueTimeText);

        taskDetailLabel = new JLabel("Task Detail: ");
        contentPanel.add(taskDetailLabel);

        taskDetailText = new JLabel();
        taskDetailText.setSize(130, 200);

        this.scrollPane = new JScrollPane();
        scrollPane.setViewportView(taskDetailText);
        scrollPane.setBorder(null);
        GUIUtils.setLabelText(taskDetailText, "" + taskDetailVO.getTaskDetail());

        contentPanel.add(scrollPane);
        contentPanel.setSize(300, 200);
        this.add(contentPanel, BorderLayout.CENTER);

        this.backButton = new JButton("Back");
        this.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });
        backButton.setSize(70, 50);

        this.buttonPanel = new JPanel();
        buttonPanel.add(backButton, BorderLayout.CENTER);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setBounds(GUIUtils.getCenterX(300), GUIUtils.getCenterY(300), 300, 300);
        this.setVisible(true);
    }


    private void onBack(){
        this.dispose();
        this.dialog.setVisible(true);

    }


}
