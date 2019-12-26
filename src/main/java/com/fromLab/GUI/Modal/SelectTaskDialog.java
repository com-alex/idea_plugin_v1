package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.ReflectionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SelectTaskDialog extends JDialog {
    //常量，防止出现魔法值
    private static final String SET_START_TIME_MODAL_FLAG = "start";
    private static final String SET_END_TIME_MODAL_FLAG = "end";

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel tablePanel;
    private JButton startButton;
    private JButton endButton;
    private JButton chooseButton;
    private JButton viewButton;
    private JButton stopButton;
    private JTable taskTable;
    private TaskServiceImpl taskService;
    private Integer uid;

    public SelectTaskDialog() {

        initInterface();
        setContentPane(contentPane);
        setModal(true);
    }

    /**
     * 主界面初始化
     */
    public void initInterface(){
        taskService = new TaskServiceImpl();

        panel1.setLocation(0,0);
        panel1.setLayout(null);


        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setLocation(0, 100);
        tablePanel.setBounds(0, 100, 1040, 400);

        panel1.add(tablePanel);

        tablePanel.setLayout(null);

        this.getDataSource();

        //设置表格的宽高
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        taskTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        taskTable.getColumnModel().getColumn(7).setPreferredWidth(166);
        taskTable.setRowHeight(30);
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        taskTable.setShowHorizontalLines(false);
        taskTable.setShowVerticalLines(false);

        startButton = new JButton("Start");
        startButton.setBounds(0, 0, 50, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTaskTime(SET_START_TIME_MODAL_FLAG);
            }
        });
        panel1.add(startButton);

        endButton = new JButton("End");
        endButton.setBounds(100, 0, 50, 30);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTaskTime(SET_END_TIME_MODAL_FLAG);
            }
        });
        panel1.add(endButton);

        viewButton = new JButton("view");
        viewButton.setBounds(200, 0, 70, 30);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查看task的detail
                viewTaskDetail();
            }
        });
        panel1.add(viewButton);

        chooseButton = new JButton("choose");
        chooseButton.setBounds(300, 0, 70, 30);
        panel1.add(chooseButton);

        stopButton = new JButton("stop");
        stopButton.setBounds(400, 0, 70, 30);
        panel1.add(stopButton);

    }

    public void getDataSource(){
        List<TaskVO> dataSource = taskService.queryAllShowTask(1);
        this.uid = 1;

        TaskTableModel taskTableModel = new TaskTableModel(dataSource);
        taskTable = new JTable(taskTableModel);



        taskTable.setBounds(0, 0, 1020, 340);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        scrollPane.setBounds(0,0, 1020, 340);

        tablePanel.add(scrollPane);

    }

    /**
     * 设置开始时间与结束时间的模态框事件
     * @param flag
     */
    private void setTaskTime(String flag) {
        int row = taskTable.getSelectedRow();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < taskTable.getColumnCount(); i++){
            if(i == 1){
                params.add(this.uid);
            }
            params.add(taskTable.getValueAt(row, i));
        }
        TaskVO selectedTaskVO = (TaskVO) ReflectionUtils.createObject(TaskVO.class, params);
        selectedTaskVO.setUid(this.uid);

        this.setVisible(false);
        if(flag != null && flag.indexOf(SET_START_TIME_MODAL_FLAG) > -1){
            if ((selectedTaskVO.getStartTime() == null) || (selectedTaskVO.getStartTime() != null && "".equals(selectedTaskVO.getStartTime()))) {

                SetTimeModal setStartTimeModal = new SetTimeModal(SET_START_TIME_MODAL_FLAG, this, selectedTaskVO);
            }
            else{
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
                JOptionPane.showOptionDialog(null, "You have chosen start time!", "Tips", JOptionPane.WARNING_MESSAGE, 0, null, jButtons, jButtons[0]);
                this.setVisible(true);
            }

        }else{
            if (selectedTaskVO.getEndTime() != null && "".equals(selectedTaskVO.getEndTime())) {
                SetTimeModal setStartTimeModal = new SetTimeModal(SET_END_TIME_MODAL_FLAG, this, selectedTaskVO);
            }
            else{
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
                JOptionPane.showOptionDialog(null, "You have chosen end time!", "Tips", JOptionPane.WARNING_MESSAGE, 0, null, jButtons, jButtons[0]);
                this.setVisible(true);
            }

        }

    }

    public JTable getTaskTable(){
        return this.taskTable;
    }

    private void viewTaskDetail(){
        int row = taskTable.getSelectedRow();
//        List<Object> params = new ArrayList<>();
//        for (int i = 0; i < taskTable.getColumnCount(); i++){
//            params.add(taskTable.getValueAt(row, i));
//        }
        Integer taskId = (Integer) taskTable.getValueAt(row, 0);
        System.out.println(taskId);
        new TaskDetailModal(this, taskId);
        this.setVisible(false);
    }



}
