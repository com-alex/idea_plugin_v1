package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.ReflectionUtils;
import com.fromLab.utils.SortUtils;

import javax.swing.*;

import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;

public class SelectTaskDialog extends JDialog {
    //常量，防止出现魔法值
    private static final String SET_START_TIME_MODAL_FLAG = "start";
    private static final String SET_END_TIME_MODAL_FLAG = "end";
    private static final String SET_FROM_DUE_TIME = "from";
    private static final String SET_TO_DUE_TIME = "to";

    private Long startTime;
    private Long endTime;
    private List<TaskVO> dataSource;
    private Integer taskPriorityFlag = 0;
    private Integer taskDueTimeFlag = 0;
    private Integer taskNameFlag = 0;
    private Integer taskProjectFlag = 0;
    private Integer taskTypeFlag = 0;
    private String []statusDataSource = {"-- Please Choose --", "New", "To be scheduled", "Scheduled", "In progress", "Closed", "On hold", "Rejected"};
    private String []priorityDataSource = {"1", "2", "3", "4", "5"};

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel conditionPanel;
    //查询条件控件
    private JLabel statusPickerLabel;
    private JComboBox statusPicker;
    private JLabel dueTimeFromLabel;
    private JButton fromDatePickerButton;
    private JLabel dueTimeToLabel;
    private JButton toDatePickerButton;
    private JLabel projectLabel;
    private JTextField projectField;
    private JLabel prioriryPickerLabel;
    private JComboBox priorityPicker;
    private JLabel typeLabel;
    private JTextField typePickerLabel;


    private JButton searchButton;



    //数据显示控件
    private JPanel tablePanel;
    private JButton startButton;
    private JButton endButton;
    private JButton chooseButton;
    private JButton viewButton;
    private JButton stopButton;
    private JTable taskTable;
    private TaskServiceImpl taskService;
    private Integer uid = 1;

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

        /**
         * 查询条件部分
         */
        conditionPanel = new JPanel();
        conditionPanel.setLayout(null);
        conditionPanel.setBounds(0, 0, 1040, 100);

        this.statusPickerLabel = new JLabel("Status: ");
        this.statusPickerLabel.setBounds(0, 10, 100, 20);
        conditionPanel.add(this.statusPickerLabel);

        statusPicker = new JComboBox(this.statusDataSource);
        this.statusPicker.setBounds(50, 5, 165, 30);
        this.statusPicker.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    if(selectedItem.contains("Please")){
                        selectedItem = "";
                    }
                    queryShowTaskByStatus(selectedItem);
                }
            }
        });
        conditionPanel.add(this.statusPicker);

        this.dueTimeFromLabel = new JLabel("From");
        this.dueTimeFromLabel.setBounds(300, 10, 100, 20);
        conditionPanel.add(this.dueTimeFromLabel);

        this.fromDatePickerButton = new JButton(DateUtils.date2String(new Date()));
        this.fromDatePickerButton.setBounds(350, 5, 150, 30);
        this.fromDatePickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDatePicker(SET_FROM_DUE_TIME);
            }
        });
        conditionPanel.add(this.fromDatePickerButton);


        this.dueTimeToLabel = new JLabel("to");
        this.dueTimeToLabel.setBounds(520, 10, 100, 20);
        conditionPanel.add(this.dueTimeToLabel);


        this.toDatePickerButton = new JButton(DateUtils.date2String(DateUtils.plusOneDay(new Date())));
        this.toDatePickerButton.setBounds(550, 5, 150, 30);
        this.toDatePickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onDatePicker(SET_TO_DUE_TIME);
            }
        });
        conditionPanel.add(this.toDatePickerButton);


        this.projectLabel = new JLabel("Project name: ");
        projectLabel.setBounds(750, 10, 100, 20);
        conditionPanel.add(this.projectLabel);

        this.projectField = new JTextField();
        projectField.setBounds(840, 5, 100, 30);
        conditionPanel.add(this.projectField);

        this.prioriryPickerLabel = new JLabel("Priority: ");
        this.prioriryPickerLabel.setBounds(0, 60, 100, 20);
        conditionPanel.add(this.prioriryPickerLabel);

        this.priorityPicker = new JComboBox(this.priorityDataSource);
        this.priorityPicker.setBounds(50, 55, 165, 30);
        conditionPanel.add(this.priorityPicker);

        this.typeLabel = new JLabel("Type:");
        this.typeLabel.setBounds(300, 60, 100, 20);
        conditionPanel.add(this.typeLabel);

        this.typePickerLabel = new JTextField();
        this.typePickerLabel.setBounds(350, 55, 100, 30);
        conditionPanel.add(this.typePickerLabel);


        this.searchButton = new JButton("search");
        searchButton.setBounds(750, 50, 70, 30);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDataSource();
            }
        });
        conditionPanel.add(this.searchButton);

        panel1.add(conditionPanel);


        /**
         * 数据显示部分
         */
        tablePanel = new JPanel();
        tablePanel.setLayout(null);

        tablePanel.setBounds(0, 100, 1040, 400);

        panel1.add(tablePanel);

        tablePanel.setLayout(null);

        this.setTableDataSource();



        startButton = new JButton("start");
        startButton.setBounds(0, 500, 70, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTaskTime(SET_START_TIME_MODAL_FLAG);
            }
        });
        panel1.add(startButton);

        endButton = new JButton("end");
        endButton.setBounds(100, 500, 70, 30);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTaskTime(SET_END_TIME_MODAL_FLAG);
            }
        });
        panel1.add(endButton);

        viewButton = new JButton("view");
        viewButton.setBounds(200, 500, 70, 30);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查看task的detail
                viewTaskDetail();
            }
        });
        panel1.add(viewButton);

        chooseButton = new JButton("choose");
        chooseButton.setBounds(300, 500, 70, 30);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTask();

            }
        });
        panel1.add(chooseButton);

        stopButton = new JButton("stop");
        stopButton.setBounds(400, 500, 70, 30);
        //默认停止工作按钮为不能按
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTask();
            }
        });
        panel1.add(stopButton);

    }



    /**
     * 设置开始时间与结束时间的模态框事件
     * @param flag
     */
    private void setTaskTime(String flag) {
        this.setVisible(false);
        Integer row = taskTable.getSelectedRow();
        if (row == -1) {
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < taskTable.getColumnCount(); i++) {
            if (i == 1) {
                params.add(this.uid);
            }
            params.add(taskTable.getValueAt(row, i));
        }
        TaskVO selectedTaskVO = (TaskVO) ReflectionUtils.createObject(TaskVO.class, params);
        selectedTaskVO.setUid(this.uid);


        if (flag != null && flag.indexOf(SET_START_TIME_MODAL_FLAG) > -1) {
            if ((selectedTaskVO.getStartTime() == null) || (selectedTaskVO.getStartTime() != null && "".equals(selectedTaskVO.getStartTime()))) {

                SetTimeModal setStartTimeModal = new SetTimeModal(SET_START_TIME_MODAL_FLAG, this, selectedTaskVO);
            } else {
                showOptionDialog("You have chosen start time!", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            if (selectedTaskVO.getStartTime() != null && !("".equals(selectedTaskVO.getStartTime()))) {
                if (selectedTaskVO.getEndTime() != null && "".equals(selectedTaskVO.getEndTime())) {
                    SetTimeModal setStartTimeModal = new SetTimeModal(SET_END_TIME_MODAL_FLAG, this, selectedTaskVO);
                } else {
                    showOptionDialog("You have chosen end time!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                showOptionDialog("You need to set start time firstly!", JOptionPane.WARNING_MESSAGE);
            }

        }

    }

    public JTable getTaskTable(){
        return this.taskTable;
    }

    private void viewTaskDetail(){
        int row = taskTable.getSelectedRow();
        Integer taskId = (Integer) taskTable.getValueAt(row, 0);
        System.out.println(taskId);
        new TaskDetailModal(this, taskId);
        this.setVisible(false);
    }


    private void chooseTask(){
        //按下选择工作按钮才能停止工作按钮
        this.stopButton.setEnabled(true);
        Long startTime = System.currentTimeMillis();
        this.startTime = startTime;
        System.out.println("Start Task  Time:" + this.startTime);
    }

    private void stopTask(){
        int row = taskTable.getSelectedRow();
        //获取所选行的task的id
        Integer taskId = (Integer) taskTable.getValueAt(row, 0);
        Long endTime = System.currentTimeMillis();
        this.endTime = endTime;
        System.out.println("Stop Task  Time:" + this.endTime);
        Integer timeSpent = (int)((this.endTime - this.startTime) / 1000);
        System.out.println("Stop Task! The Spent Time is: " + timeSpent + "s");
        this.startTime = 0L;
        this.endTime = 0L;
        //获取所选行的task的progress
        String progressString = (String) taskTable.getValueAt(row, 9);
        Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
        this.setVisible(false);
        new StopTaskModal(taskId, progress, timeSpent, this);

    }

    public void setTableDataSource(){
        //TODO uid为静态，需要进行动态变化
        List<TaskVO> dataSource = taskService.queryAllShowTask(1);
        this.uid = 1;

        TaskTableModel taskTableModel = new TaskTableModel(dataSource);
        taskTable = new JTable(taskTableModel);

        setTableStyle();
        taskTable.setBounds(0, 0, 1020, 340);

        this.dataSource = dataSource;

        setTableSort();

        JScrollPane scrollPane = new JScrollPane(taskTable);

        scrollPane.setBounds(0,0, 1020, 340);

        tablePanel.add(scrollPane);
    }


    public void resetTableDataSource(){
        //TODO uid为静态，需要进行动态变化
        //刷新表格数据源
        List<TaskVO> dataSource = taskService.queryAllShowTask(1);
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
        this.dataSource = dataSource;
        setTableSort();
    }

    public void setTableStyle(){
        this.getTaskTable().getColumnModel().getColumn(0).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(2).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(3).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(4).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(5).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(6).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(7).setPreferredWidth(166);
        this.getTaskTable().getColumnModel().getColumn(8).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(9).setPreferredWidth(100);
        taskTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        this.getTaskTable().setRowHeight(30);
        this.getTaskTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.getTaskTable().setShowHorizontalLines(false);
        this.getTaskTable().setShowVerticalLines(false);
    }

    private void showOptionDialog(String info, Integer type){
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
        JOptionPane.showOptionDialog(null, info, "Tips", type, 0, null, jButtons, jButtons[0]);
        this.setVisible(true);
    }

    private void setTableSort(){
        int flag = 0;
        final JTableHeader tableHeader = this.taskTable.getTableHeader();
        tableHeader.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getClickCount() == 1){
                    int pick = tableHeader.columnAtPoint(e.getPoint());

                    //进行优先级排序
                    if(pick == 1){
                        sortDataSourceOrderByTaskName();
                    }
                    else if(pick == 2){
                        sortDataSourceOrderByProjectName();
                    }
                    else if(pick == 3){
                        sortDataSourceOrderByPriority();
                    }
                    else if(pick == 4){
                        sortDataSourceOrderByTaskType();
                    }
                    //进行deadline排序
                    else if(pick == 7){
                        sortDataSourceOrderByDueTime();
                    }

                }
            }
        });
    }



    private void sortDataSourceOrderByPriority(){

        //TODO 需进行修改，taskPriority是String类型

        if(this.taskPriorityFlag % 2 == 0){
            SortUtils.sort(this.dataSource,
                    new String[]{"taskPriority","dueTime","projectName","taskType","taskId"},
                    new boolean[]{false,true,true,true,true});
        }else{
            SortUtils.sort(this.dataSource,
                    new String[]{"taskPriority","dueTime","projectName","taskType","taskId"},
                    new boolean[]{true,true,true,true,true});
        }
        this.taskPriorityFlag++;
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
    }

    private void sortDataSourceOrderByTaskType() {
        if(this.taskTypeFlag % 2 == 0){
            SortUtils.sort(this.dataSource,
                    new String[]{"taskType","dueTime","taskPriority","projectName","taskId"},
                    new boolean[]{true, true, false, true, true});
        }
        else{
            SortUtils.sort(this.dataSource,
                    new String[]{"taskType","dueTime","taskPriority","projectName","taskId"},
                    new boolean[]{false, true, false, true, true});
        }
        this.taskTypeFlag++;
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
    }

    private void sortDataSourceOrderByDueTime(){

        //TODO 需进行修改，将来时间的排序还得重写
        if(this.taskDueTimeFlag % 2 == 0){
            SortUtils.sort(this.dataSource,
                    new String[]{"dueTime","taskPriority","projectName","taskType","taskId"},
                    new boolean[]{true,false,true,true,true});
        }else{
            SortUtils.sort(this.dataSource,
                    new String[]{"dueTime","taskPriority","projectName","taskType","taskId"},
                    new boolean[]{false,false,true,true,true});
        }
        this.taskDueTimeFlag++;
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
    }

    private void sortDataSourceOrderByTaskName(){
        if(this.taskNameFlag % 2 == 0){
            SortUtils.sort(this.dataSource,
                    new String[]{"taskName","dueTime","taskPriority","taskType","taskId"},
                    new boolean[]{true,true,false,true,true});
        }else{
            SortUtils.sort(this.dataSource,
                    new String[]{"taskName","dueTime","taskPriority","taskType","taskId"},
                    new boolean[]{false,true,false,true,true});
        }
        this.taskNameFlag++;
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
    }

    private void sortDataSourceOrderByProjectName(){
        if(this.taskProjectFlag % 2 == 0){
            SortUtils.sort(this.dataSource,
                    new String[]{"projectName","dueTime","taskPriority","taskType","taskId"},
                    new boolean[]{true, true, false, true, true});
        }else{
            SortUtils.sort(this.dataSource,
                    new String[]{"projectName","dueTime","taskPriority","taskType","taskId"},
                    new boolean[]{false, true, false, true, true});
        }
        this.taskProjectFlag++;
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
    }

    /**
     * 界面状态下拉菜单触发查询事件
     */
    private void queryShowTaskByStatus(String status){
        List<TaskVO> taskVOS = new ArrayList<>();
        taskVOS = taskService.queryAllShowTaskByStatus(this.uid, status);
        this.dataSource = taskVOS;
        this.getTaskTable().setModel(new TaskTableModel(this.dataSource));
        setTableStyle();
    }

    public JButton getFromDatePickerButton() {
        return fromDatePickerButton;
    }



    public JButton getToDatePickerButton() {
        return toDatePickerButton;
    }

    private void onDatePicker(String type){
        this.setVisible(false);
        if(SET_FROM_DUE_TIME.equals(type)){
            new SetDueTimeModal(
                    type, this,
                    DateUtils.string2Date(this.fromDatePickerButton.getText()));
        }
        else{
            new SetDueTimeModal(type, this,
                    DateUtils.string2Date(this.toDatePickerButton.getText()));
        }
    }


    private void searchDataSource(){
        //获取条件
        String queryStatus = (String) this.statusPicker.getSelectedItem();
        if(queryStatus.contains("Please")){
            queryStatus = "";
        }
        String fromDueTime = this.fromDatePickerButton.getText();
        String toDueTime = this.toDatePickerButton.getText();
        LocalDateTime fromLocalDateTime = DateUtils.string2LocalDateTime(fromDueTime);
        LocalDateTime toLocalDateTime = DateUtils.string2LocalDateTime(toDueTime);
        Duration duration = Duration.between(fromLocalDateTime, toLocalDateTime);
        Long min = duration.toMillis();
        if(min <= 0L){
            this.setVisible(false);
            showOptionDialog("Illegal time", JOptionPane.WARNING_MESSAGE);
        }
        else{
            List<TaskVO> taskVOS = this.taskService.queryShowTaskByCondition(
                    this.uid, queryStatus, fromDueTime, toDueTime);
            this.dataSource = taskVOS;
            this.getTaskTable().setModel(new TaskTableModel(dataSource));
            setTableStyle();
        }
    }
}
