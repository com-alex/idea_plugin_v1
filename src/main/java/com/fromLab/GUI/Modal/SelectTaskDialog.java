package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TableRenderer;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.GUI.window.TaskToolWindow;
import com.fromLab.VO.TaskDetailVO;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.loader.IconsLoader;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.*;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


import java.util.Date;
import java.util.List;

public class SelectTaskDialog extends JDialog implements WindowListener {
    //Static constants
    private static final String SET_FROM_DUE_TIME = "from";
    private static final String SET_TO_DUE_TIME = "to";
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private OpenprojectURL openprojectURL;
    private String originalUrl;
    private TaskToolWindow taskToolWindow;

    private Long startTime = 0L;
    private Long endTime = 0L;
    //Flag to determime whether the user choose the task
    private Boolean chosen = false;
    //Variable to save the selected task
    private Task selectedTask;
    private List<TaskVO> dataSource;
    //Flag for sorting tasks
    private Integer taskPriorityFlag = 0;
    private Integer taskDueTimeFlag = 0;
    private Integer taskNameFlag = 0;
    private Integer taskProjectFlag = 0;
    private Integer taskTypeFlag = 0;
    private BigDecimal timeSpent = BigDecimal.ZERO;
    private String spentTimeCustomFieldName;
    private String endDateCustomFieldName;
    //The data set shown in the drop-down menu
    private String[] statusShowDate = {"-- Please Choose --", "New", "In progress", "Closed", "On hold", "Rejected"};
    private String[] priorityShowData = {"-- Please Choose --", "Immediate", "High", "Normal", "Low"};
    private String[] typeShowDate = {"-- Please Choose --", "Management", "Specification", "Development", "Testing", "Support", "Other"};

    private JPanel contentPane;
    private JPanel panel1;
    private JPanel conditionPanel;
    //The area for search criteria
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

    //The area for displaying the task
    private JPanel tablePanel;
    private JButton endButton;
    private JButton chooseButton;
    private JButton viewButton;
    private JButton stopButton;
    private JTable taskTable;
    private TaskServiceImpl taskService;
    private SocketServer socketServer;

    public SelectTaskDialog(OpenprojectURL openprojectURL, Boolean chosen, Task selectedTask,
                            Long startTime, SocketServer socketServer, TaskToolWindow taskToolWindow){
        this.taskToolWindow = taskToolWindow;
        this.openprojectURL = openprojectURL;
        this.chosen = chosen;
        this.selectedTask = selectedTask;
        this.startTime = startTime;
        this.socketServer = socketServer;
        originalUrl = this.openprojectURL.getOpenProjectURL();
        initInterface();
        init();
        setContentPane(contentPane);
    }

    /**
     * Initialization for getting the custom fields and show the task
     */
    public void init() {
        taskService = new TaskServiceImpl();
        this.dataSource = new ArrayList<>();
        //获取自定义字段的名称
        try {
            this.endDateCustomFieldName = GetCustomFieldNumUtil.getCustomFieldNum("End date", openprojectURL);
            this.spentTimeCustomFieldName = GetCustomFieldNumUtil.getCustomFieldNum("Time spent", openprojectURL);
        } catch (BusinessException e) {
            this.setVisible(false);
            String errorMsg = e.getMessage();
            showOptionDialog(errorMsg, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
            return;
        }
        //Search and display tasks
        this.setTableDataSource();
    }


    /**
     * User interface initialization
     */
    public void initInterface(){

        panel1.setLocation(0,0);
        panel1.setLayout(null);
        /**
         * the search area
         */
        conditionPanel = new JPanel();
        conditionPanel.setLayout(null);
        conditionPanel.setBounds(0, 0, 1040, 100);

        this.statusPickerLabel = new JLabel("Status: ");
        this.statusPickerLabel.setBounds(0, 10, 100, 20);
        conditionPanel.add(this.statusPickerLabel);

        statusPicker = new JComboBox(this.statusShowDate);
        this.statusPicker.setBounds(50, 5, 165, 30);
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
         * The task displaying area
         */
        tablePanel = new JPanel();
        tablePanel.setLayout(null);
        tablePanel.setBounds(0, 100, 1040, 400);
        TaskTableModel taskTableModel = new TaskTableModel();
        taskTable = new JTable(taskTableModel);
        taskTable.setBounds(0, 0, 1020, 340);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBounds(0,0, 1020, 340);
        tablePanel.add(scrollPane);
        panel1.add(tablePanel);

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
                //view the detail of the task
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
        //By default, The stop button cannot be pressed
        if(!this.chosen){
            stopButton.setEnabled(false);
        }
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTask();
            }
        });
        panel1.add(stopButton);

        this.addWindowListener(this);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    /**
     * Function for displaying the task
     */
    public void setTableDataSource(){
        //Search the task from the OpenProject
        this.dataSource = this.getDataSource(null, null, null,
                null, null, null);
        //Put the tasks into the taskTable for displaying
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        //Set the taskTable style
        setTableStyle();
        //Enable to sort the task
        setTableSort();
    }

    /**
     * Update the end date of the task
     */
    private void setTaskEndTime(){
        Integer row = taskTable.getSelectedRow();
        if (row == -1) {
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            return;
        }
        //Convert the TaskVO into the entity task to operate
        Task tempTask = this.taskVOConvertToTask(this.dataSource.get(row));
        this.setVisible(false);
        openprojectURL.setOpenProjectURL(originalUrl);
        //Open the dialog for updating the end date
        new SetTimeModal(this, tempTask, this.endDateCustomFieldName, openprojectURL);
    }

    /**
     * Show the detail
     */
    private void viewTaskDetail(){
        Integer row = taskTable.getSelectedRow();
        if (row == -1) {
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            return;
        }
        //Convert the TaskVO into the taskDetailVO to show the detail
        TaskDetailVO taskDetailVO = this.taskVOConvertToTaskDetailVO(this.dataSource.get(row));
        //Open the dialog to show the detail
        new TaskDetailModal(this, taskDetailVO);
        this.setVisible(false);
    }

    /**
     * Choose the task on Select Task Dialog
     */
    private void chooseTask(){
        if(!this.chosen){
            int row = taskTable.getSelectedRow();
            if(row == -1){
                this.setVisible(false);
                showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
                return;
            }
            this.selectedTask = this.taskVOConvertToTask(this.dataSource.get(row));
            this.openprojectURL.setOpenProjectURL(originalUrl);
            //If the start date of the selected task is empty, the start data becomes the current time and is updated
            if("null".equals(this.selectedTask.getStartTime())){
                String startDate = DateUtils.date2String(new Date());
                String result = this.taskService.updateStartDate(openprojectURL, this.selectedTask.getTaskId(),
                        this.selectedTask.getLockVersion(), startDate);
                if(StringUtils.equals(result, ERROR)){
                    this.setVisible(false);
                    showOptionDialog("Fail to save the startTime of the task", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
                    return;
                }
            }
            //After choosing, the flag '*' will be added in the Select Task Dialog
            this.getTaskTable().setValueAt("*", row, 0);
            //Set the flag
            this.chosen = true;
            //Put the task into Socket
            socketServer.task = this.selectedTask;
            //The stop button is available
            this.stopButton.setEnabled(true);
            //Synchronous task display on tool window
            this.taskToolWindow.setSelectedFlag(this.selectedTask.getTaskId());
            this.taskToolWindow.setChosen(true);
            this.taskToolWindow.setSelectedTask(this.selectedTask);
            this.taskToolWindow.getStopButton().setEnabled(false);
            this.startTime = System.currentTimeMillis();
            this.taskToolWindow.setStartTime(this.startTime);
            System.out.println("Start Task  Time:" + this.startTime);
            this.setVisible(false);
            showOptionDialog("You select a task successfully!", JOptionPane.PLAIN_MESSAGE, IconsLoader.SUCCESS_ICON);
        }else{
            this.setVisible(false);
            showOptionDialog("You have selected another task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            return;
        }
    }

    /**
     * Stop the task
     */
    private void stopTask(){
        int row = taskTable.getSelectedRow();
        if(row == -1){
            this.setVisible(false);
            showOptionDialog("You need to select a task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            return;
        }
        TaskVO selectedTaskVO = this.dataSource.get(row);

        if(!selectedTaskVO.getTaskId().equals(this.selectedTask.getTaskId())){
            this.setVisible(false);
            showOptionDialog("You select a wrong task!", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            return;
        }
        if(this.endTime == 0L){
            this.endTime = System.currentTimeMillis();
        }
        //If time is spent, the calculation is not performed and the original time spent is updated.
        //If no time is spent, calculate and update the new time spent.
        if(timeSpent.compareTo(BigDecimal.ZERO) == 0){
            timeSpent = NumberUtils.covertTimeToHour(this.endTime - this.startTime);
        }
        System.out.println("Stop Task! The Spent Time is: " + this.endTime);
        System.out.println("The Spent Time is: " + timeSpent + "h");
        this.selectedTask = this.taskVOConvertToTask(this.dataSource.get(row));
        this.openprojectURL.setOpenProjectURL(originalUrl);
        //Update the spent time of the task
        String result = this.taskService.updateSpentTime(openprojectURL, this.selectedTask.getTaskId(),
                this.selectedTask.getLockVersion(), NumberUtils.calUpdateTimeSpent(this.selectedTask.getTimeSpent(), timeSpent),
                this.spentTimeCustomFieldName);
        //If succeed, the interface, parameters,
        // and flags are initialized and Open the dialog to update status and progress
        if (SUCCESS.equals(result)){
            this.chosen = false;
            this.stopButton.setEnabled(false);
            socketServer.task = null;
            //Get progress of the task
            String progressString = (String) taskTable.getValueAt(row, 10);
            Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
            this.setVisible(false);
            this.openprojectURL.setOpenProjectURL(originalUrl);
            //Open the StopTaskModal to update status and progress
            new StopTaskModal(this.selectedTask, progress, this, openprojectURL);
            this.startTime = 0L;
            this.endTime = 0L;
            this.timeSpent = BigDecimal.ZERO;
            //Remove the flag on Select Task Dialog
            this.getTaskTable().setValueAt("", row, 0);
            //Initialze the parameters and flags
            this.taskToolWindow.deleteSelectedFlag(this.selectedTask.getTaskId());
            this.taskToolWindow.setChosen(false);
            this.taskToolWindow.setSelectedTask(null);
            this.taskToolWindow.paintStopButton();
            this.selectedTask = null;
        }
        //If it fails, the program will update automatically again and the parameters, flag, user interface have no change.
        else{
            this.openprojectURL.setOpenProjectURL(originalUrl);
            try {
                this.selectedTask = this.taskService.getTaskById(openprojectURL, this.selectedTask.getTaskId());
            } catch (BusinessException e) {
                this.selectedTask = null;
            }
            this.openprojectURL.setOpenProjectURL(originalUrl);
            String response = this.taskService.updateSpentTime(openprojectURL, this.selectedTask.getTaskId(),
                    this.selectedTask.getLockVersion(), NumberUtils.calUpdateTimeSpent(this.selectedTask.getTimeSpent(), timeSpent),
                    this.spentTimeCustomFieldName);
            if(SUCCESS.equals(response)){
                this.chosen = false;
                this.stopButton.setEnabled(false);
                socketServer.task = null;
                //Get the progress
                String progressString = (String) taskTable.getValueAt(row, 10);
                Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
                this.setVisible(false);
                this.openprojectURL.setOpenProjectURL(originalUrl);
                new StopTaskModal(this.selectedTask, progress, this, openprojectURL);
                this.startTime = 0L;
                this.endTime = 0L;
                this.timeSpent = BigDecimal.ZERO;
                this.getTaskTable().setValueAt("", row, 0);
                this.taskToolWindow.deleteSelectedFlag(this.selectedTask.getTaskId());
                this.taskToolWindow.setChosen(false);
                this.taskToolWindow.setSelectedTask(null);
                this.taskToolWindow.paintStopButton();
                this.selectedTask = null;
            }
            //If it still fails, the message dialog will be pop up to prompt the user to update again
            else{
                this.setVisible(false);
                showOptionDialog("Fail to save", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
                this.resetTableDataSource();
            }
        }

    }

    /**
     * Refresh the data
     */
    public void resetTableDataSource(){
        openprojectURL.setOpenProjectURL(originalUrl);
        //Search the task
        this.dataSource = this.getDataSource(null, null, null, null, null, null);
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
        setTableSort();
        setChosenFlag();
    }

    /**
     * Set the style of the task table
     */
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

    private void showOptionDialog(String info, Integer type, Icon icon){
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
        JOptionPane.showOptionDialog(null, info, "Tips", JOptionPane.DEFAULT_OPTION, type, icon, jButtons, jButtons[0]);
        this.setVisible(true);
    }

    /**
     * Function to implement to sort the task
     */
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


    /**
     * Sort task by Priority
     */
    private void sortDataSourceOrderByPriority(){
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
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
    }

    /**
     * Sort task by Task Type
     */
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
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
    }

    /**
     * Sort task by due date
     */
    private void sortDataSourceOrderByDueTime(){
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
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
    }

    /**
     * Sort task by Subject
     */
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
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
    }

    /**
     * Sort task by Project name
     */
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
        this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
        setTableStyle();
    }


    public JButton getFromDatePickerButton() {
        return fromDatePickerButton;
    }



    public JButton getToDatePickerButton() {
        return toDatePickerButton;
    }

    /**
     * Press the button in search area to select the start and end times of due time
     * @param type
     */
    private void onDatePicker(String type){
        this.setVisible(false);
        if(SET_FROM_DUE_TIME.equals(type)){
            //Open the dialog for selecting start due time for searching
            new SetDueTimeModal(
                    type, this,
                    DateUtils.string2Date(this.fromDatePickerButton.getText()));
        }
        else{
            //Open the dialog for selecting end due time for searching
            new SetDueTimeModal(type, this,
                    DateUtils.string2Date(this.toDatePickerButton.getText()));
        }
    }

    /**
     * The function after pressing the search button
     */
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
        //If the start due date bigger than the end due date,
        // that means the due date for search is illegal
        if(min <= 0L){
            this.setVisible(false);
            showOptionDialog("Illegal due date", JOptionPane.WARNING_MESSAGE, IconsLoader.ERROR_ICON);
        }
        else{
            //filter the task by condition and display
            this.dataSource = this.getDataSource(queryStatusNum, queryPriorityNum, fromDueTime,
                    toDueTime, queryTaskTypeNum, querySubject);
            this.getTaskTable().setModel(new TaskTableModel(dataSource, this.selectedTask));
            setTableStyle();
            setChosenFlag();
            setTableSort();
        }
    }

    /**
     * Filter and search the tasks by condition
     * @param statusNum
     * @param priorityNum
     * @param fromDueDate
     * @param toDueDate
     * @param taskTypeNum
     * @param subject
     * @return
     */
    private List<TaskVO> getDataSource(Integer statusNum,
                                       Integer priorityNum,
                                       String fromDueDate,
                                       String toDueDate,
                                       Integer taskTypeNum,
                                       String subject){
        List<TaskVO> taskVOList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        try {
            taskList = taskService.getTasksByConditions(openprojectURL, statusNum, priorityNum,
                    fromDueDate, toDueDate, taskTypeNum, subject);
        } catch (BusinessException e) {
            taskList = new ArrayList<>();
            this.setVisible(false);
            String errorMsg = e.getMessage() + "\n\nFail to get task list!";
            showOptionDialog(errorMsg, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
        }
        taskList.forEach(task -> {
            TaskVO taskVO = new TaskVO();
            taskVO = (TaskVO) ReflectionUtils.copyProperties(task, taskVO);
            taskVOList.add(taskVO);
        });
        return taskVOList;
    }

    /**
     * The function is to perform synchronous task display on Select Task Dialog
     */
    private void setChosenFlag(){
        if(this.chosen){
            if(this.selectedTask != null){
                for (int i = 0; i < this.dataSource.size(); i++) {
                    if(this.selectedTask.getTaskId().equals(this.dataSource.get(i).getTaskId())){
                        this.getTaskTable().setValueAt("*", i, 0);
                    }
                }
            }
        }
    }

    /**
     * Covert the view object TaskVO to entity Task
     * @param taskVO
     * @return Task
     */
    private Task taskVOConvertToTask(TaskVO taskVO){
        Task task = new Task();
        return (Task) ReflectionUtils.copyProperties(taskVO, task);
    }

    /**
     * Covert the view object TaskVO to view object TaskDetailVO
     * @param taskVO
     * @return TaskDetailVO
     */
    private TaskDetailVO taskVOConvertToTaskDetailVO(TaskVO taskVO){
        Task task = new Task();
        task = (Task) ReflectionUtils.copyProperties(taskVO, task);
        TaskDetailVO taskDetailVO = new TaskDetailVO();
        return (TaskDetailVO) ReflectionUtils.copyProperties(task, taskDetailVO);
    }

    /**
     * Filter for priority
     * @param input
     * @return
     */
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

    /**
     * Filter for status
     * @param input
     * @return
     */
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

    public JTable getTaskTable(){
        return this.taskTable;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Listen for Select Task Dialog close events
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e) {

        if(selectedTask != null){
            //If there is a selected task, the stop button is available when closing the Select Task Dialog
            this.taskToolWindow.getStopButton().setEnabled(true);
        }else{
            //If there is no selected task, the stop button is unavailable when closing the Select Task Dialog
            this.taskToolWindow.getStopButton().setEnabled(false);
        }
        //The more and choose button is available when closing the Select Task Dialog
        this.taskToolWindow.getMoreButton().setEnabled(true);
        this.taskToolWindow.getChooseButton().setEnabled(true);
        this.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


}
