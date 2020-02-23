package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TableRenderer;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GetCustomFieldNumUtil;
import com.fromLab.utils.ReflectionUtils;
import com.fromLab.utils.SortUtils;
import org.apache.commons.lang3.StringUtils;

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
    private static final String SET_FROM_DUE_TIME = "from";
    private static final String SET_TO_DUE_TIME = "to";
    private final static String OPENPROJECT_URL="http://projects.plugininide.com/openproject";
    private final static String API_KEY="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    private Long startTime;
    private Long endTime;
    private Boolean chosen = false;
    private Integer selectedTaskIndex;
    private List<TaskVO> dataSource;
    private Integer taskPriorityFlag = 0;
    private Integer taskDueTimeFlag = 0;
    private Integer taskNameFlag = 0;
    private Integer taskProjectFlag = 0;
    private Integer taskTypeFlag = 0;
    private String taskTypeCustomFieldName;
    private String spentTimeCustomFieldName;
    private String endDateCustomFieldName;
    private Task selectedTask;
    private String[] statusShowDate = {"-- Please Choose --", "New", "In progress", "Closed", "On hold", "Rejected"};
    private String[] priorityShowData = {"-- Please Choose --", "Immediate", "High", "Normal", "Low"};
    private String[] typeShowDate = {"-- Please Choose --", "Management", "Specification", "Development", "Testing", "Support", "Other"};

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
    private JLabel subjectLabel;
    private JTextField subjectField;
    private JLabel prioriryPickerLabel;
    private JComboBox priorityPicker;
    private JLabel typeLabel;
    private JComboBox typePicker;


    private JButton searchButton;



    //数据显示控件
    private JPanel tablePanel;
//    private JButton startButton;
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

        //获取自定义字段的名称
        this.taskTypeCustomFieldName = GetCustomFieldNumUtil.getCustomfiledNum("Task type", OPENPROJECT_URL, API_KEY);
        this.endDateCustomFieldName = GetCustomFieldNumUtil.getCustomfiledNum("End date", OPENPROJECT_URL, API_KEY);
        this.spentTimeCustomFieldName = GetCustomFieldNumUtil.getCustomfiledNum("Time spent", OPENPROJECT_URL, API_KEY);

        this.selectedTask = new Task();

        taskService = new TaskServiceImpl();
        this.dataSource = new ArrayList<>();

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

        statusPicker = new JComboBox(this.statusShowDate);
        this.statusPicker.setBounds(50, 5, 165, 30);
        this.statusPicker.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    if(selectedItem.contains("Please")){
                        selectedItem = "";
                    }
//                    queryShowTaskByStatus(selectedItem);
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


        this.subjectLabel = new JLabel("Subject: ");
        subjectLabel.setBounds(750, 10, 100, 20);
        conditionPanel.add(this.subjectLabel);

        this.subjectField = new JTextField();
        subjectField.setBounds(840, 5, 100, 30);
        conditionPanel.add(this.subjectField);

        this.prioriryPickerLabel = new JLabel("Priority: ");
        this.prioriryPickerLabel.setBounds(0, 60, 100, 20);
        conditionPanel.add(this.prioriryPickerLabel);

        this.priorityPicker = new JComboBox(this.priorityShowData);
        this.priorityPicker.setBounds(50, 55, 165, 30);
        conditionPanel.add(this.priorityPicker);

        this.typeLabel = new JLabel("Type:");
        this.typeLabel.setBounds(300, 60, 100, 20);
        conditionPanel.add(this.typeLabel);

        this.typePicker = new JComboBox(this.typeShowDate);
        this.typePicker.setBounds(350, 55, 165, 30);
        conditionPanel.add(this.typePicker);


        this.searchButton = new JButton("search");
        searchButton.setBounds(750, 55, 70, 30);
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

        this.setTableDataSource();


        endButton = new JButton("set end date");
        endButton.setBounds(0, 500, 150, 30);
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTaskEndTime();
            }
        });
        panel1.add(endButton);

        viewButton = new JButton("view task's detail");
        viewButton.setBounds(180, 500, 150, 30);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查看task的detail
                viewTaskDetail();
            }
        });
        panel1.add(viewButton);

        chooseButton = new JButton("choose the task");
        chooseButton.setBounds(360, 500, 150, 30);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTask();

            }
        });
        panel1.add(chooseButton);

        stopButton = new JButton("stop the task");
        stopButton.setBounds(540, 500, 150, 30);
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

    private void setTaskEndTime(){
        Integer row = taskTable.getSelectedRow();
        if (row == -1) {
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.selectedTask = this.taskVOConvertToTask(this.dataSource.get(row));
        this.setVisible(false);
        new SetTimeModal(this, this.selectedTask, this.endDateCustomFieldName);

    }

    public JTable getTaskTable(){
        return this.taskTable;
    }

    private void viewTaskDetail(){
        Integer row = taskTable.getSelectedRow();
        if (row == -1) {
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaskDetailVO taskDetailVO = this.taskVOConvertToTaskDetailVO(this.dataSource.get(row));
        new TaskDetailModal(this, taskDetailVO);
        this.setVisible(false);
    }


    private void chooseTask(){
        if(!this.chosen){
            int row = taskTable.getSelectedRow();
            if(row == -1){
                this.setVisible(false);
                showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            this.selectedTask = this.taskVOConvertToTask(this.dataSource.get(row));
            if("null".equals(this.selectedTask.getStartTime())){
                String startDate = DateUtils.date2String(new Date());
                this.taskService.updateStartDate(OPENPROJECT_URL, API_KEY, this.selectedTask.getTaskId(),
                        this.selectedTask.getLockVersion(), startDate);
            }
            this.getTaskTable().setValueAt("*", row, 0);
            this.chosen = true;
            this.selectedTaskIndex = row;
            this.stopButton.setEnabled(true);
            this.startTime = System.currentTimeMillis();
            System.out.println("Start Task  Time:" + this.startTime);
            this.setVisible(false);
            showOptionDialog("You select a task successfully!", JOptionPane.PLAIN_MESSAGE);
        }else{
            this.setVisible(false);
            showOptionDialog("You have selected a task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }

    private void stopTask(){
        int row = taskTable.getSelectedRow();
        if(row == -1){
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        TaskVO selectedTaskVO = this.dataSource.get(row);

        if(!selectedTaskVO.getTaskId().equals(this.selectedTask.getTaskId())){
            this.setVisible(false);
            showOptionDialog("You select a wrong task!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.endTime = System.currentTimeMillis();
        Integer timeSpent = (int)((this.endTime - this.startTime) / 1000);
        System.out.println("Stop Task! The Spent Time is: " + timeSpent + "s");
        this.getTaskTable().setValueAt("", row, 0);
        this.startTime = 0L;
        this.endTime = 0L;
        this.selectedTask = this.taskVOConvertToTask(this.dataSource.get(row));
        //TODO 更新Spent time
        this.taskService.updateSpentTime(OPENPROJECT_URL, API_KEY, this.selectedTask.getTaskId(),
                this.selectedTask.getLockVersion(), this.selectedTask.getTimeSpent() + timeSpent,
                this.spentTimeCustomFieldName);
        this.chosen = false;

        this.stopButton.setEnabled(false);
        //获取所选行的task的progress
            String progressString = (String) taskTable.getValueAt(row, 10);
        Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
        this.setVisible(false);
        new StopTaskModal(this.selectedTask, progress, this);
        this.selectedTask = null;
    }

    public void setTableDataSource(){
        //TODO uid为静态，需要进行动态变化


        this.dataSource = this.getDataSource(null, null, null,
                null, null, null);



        TaskTableModel taskTableModel = new TaskTableModel(dataSource);
        taskTable = new JTable(taskTableModel);

        setTableStyle();
        taskTable.setBounds(0, 0, 1020, 340);

        setTableSort();

        JScrollPane scrollPane = new JScrollPane(taskTable);

        scrollPane.setBounds(0,0, 1020, 340);

        tablePanel.add(scrollPane);
    }


    public void resetTableDataSource(){
        //刷新表格数据源
        this.dataSource = this.getDataSource(null, null, null, null, null, null);
        this.getTaskTable().setModel(new TaskTableModel(dataSource));
        setTableStyle();
        setTableSort();
        setChosenFlag();
    }

    public void setTableStyle(){
        this.getTaskTable().setDefaultRenderer(Object.class, new TableRenderer());
        this.getTaskTable().getColumnModel().getColumn(0).setPreferredWidth(50);
        this.getTaskTable().getColumnModel().getColumn(1).setPreferredWidth(80);
        this.getTaskTable().getColumnModel().getColumn(2).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(3).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(4).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(5).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(6).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(7).setPreferredWidth(150);
        this.getTaskTable().getColumnModel().getColumn(8).setPreferredWidth(166);
        this.getTaskTable().getColumnModel().getColumn(9).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(10).setPreferredWidth(100);
        this.getTaskTable().getColumnModel().getColumn(11).setPreferredWidth(100);
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
                    if(pick == 2){
                        sortDataSourceOrderByTaskName();
                    }
                    else if(pick == 3){
                        sortDataSourceOrderByProjectName();
                    }
                    else if(pick == 4){
                        sortDataSourceOrderByPriority();
                    }
                    else if(pick == 5){
                        sortDataSourceOrderByTaskType();
                    }
                    //进行deadline排序
                    else if(pick == 8){
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
        //Get the condition
        //status
        String queryStatus = (String) this.statusPicker.getSelectedItem();
        Integer queryStatusNum = this.statusFilter(queryStatus);
        //priorityNum
        String queryPriority = (String) this.priorityPicker.getSelectedItem();
        Integer queryPriorityNum = null;
        if(queryPriority.contains("Please")){
            queryPriorityNum = null;
        }else {
            queryPriorityNum = this.priorityFilter(queryPriority);
        }
        //taskType
        Integer queryTaskTypeNum = this.typePicker.getSelectedIndex();
        if(queryTaskTypeNum == 0){
            queryTaskTypeNum = null;
        }
        //subject
        String querySubject = this.subjectField.getText().trim();
        if(StringUtils.equals(querySubject, "")){
            querySubject = null;
        }
        //fromDueDate, toDueDate
        String fromDueTime = this.fromDatePickerButton.getText();
        String toDueTime = this.toDatePickerButton.getText();
        LocalDateTime fromLocalDateTime = DateUtils.string2LocalDateTime(fromDueTime);
        LocalDateTime toLocalDateTime = DateUtils.string2LocalDateTime(toDueTime);
        Duration duration = Duration.between(fromLocalDateTime, toLocalDateTime);
        Long min = duration.toMillis();
        if(min <= 0L){
            this.setVisible(false);
            showOptionDialog("Illegal due date", JOptionPane.WARNING_MESSAGE);
        }
        else{
            this.dataSource = this.getDataSource(queryStatusNum, queryPriorityNum, fromDueTime,
                                                    toDueTime, queryTaskTypeNum, querySubject);
            this.getTaskTable().setModel(new TaskTableModel(dataSource));
            setTableStyle();
            setChosenFlag();
            setTableSort();
        }
    }



    private List<TaskVO> getDataSource(Integer statusNum,
                                       Integer priorityNum,
                                       String fromDueDate,
                                       String toDueDate,
                                       Integer taskTypeNum,
                                       String subject){
        List<TaskVO> taskVOList = new ArrayList<>();
        List<Task> taskList = taskService.getTasksByConditons(OPENPROJECT_URL, API_KEY, statusNum, priorityNum,
                                                                fromDueDate, toDueDate, taskTypeNum, subject);
        taskList.forEach(task -> {
            TaskVO taskVO = new TaskVO();
            taskVO = (TaskVO) ReflectionUtils.copyProperties(task, taskVO);
            taskVOList.add(taskVO);
        });
        return taskVOList;
    }

    private void setChosenFlag(){
        if(this.selectedTask != null){
            for (int i = 0; i < this.dataSource.size(); i++) {
                if(this.selectedTask.getTaskId().equals(this.dataSource.get(i).getTaskId())){
                    this.getTaskTable().setValueAt("*", i, 0);
                }
            }
        }
    }


    private Task taskVOConvertToTask(TaskVO taskVO){
        Task task = new Task();
        return (Task) ReflectionUtils.copyProperties(taskVO, task);
    }

    private TaskDetailVO taskVOConvertToTaskDetailVO(TaskVO taskVO){
        Task task = new Task();
        task = (Task) ReflectionUtils.copyProperties(taskVO, task);
        TaskDetailVO taskDetailVO = new TaskDetailVO();
        return (TaskDetailVO) ReflectionUtils.copyProperties(task, taskDetailVO);
    }

    private Integer priorityFilter(String input){

        if("Low".equals(input)){
            return 7;
        }else if("Normal".equals(input)){
            return 8;
        }else if("High".equals(input)){
            return 9;
        }else if("Immediate".equals(input)){
            return 10;
        }
        return null;
    }


    private Integer statusFilter(String input){
        if("New".equals(input)){
            return 1;
        }else if("In progress".equals(input)){
            return 7;
        }else if("Closed".equals(input)){
            return 13;
        }else if("On hold".equals(input)){
            return 14;
        }else if("Rejected".equals(input)){
            return 15;
        }
        return null;
    }

}
