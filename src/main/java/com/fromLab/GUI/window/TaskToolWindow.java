package com.fromLab.GUI.window;

import com.fromLab.GUI.Modal.SelectTaskDialog;
import com.fromLab.GUI.Modal.StopTaskModal;
import com.fromLab.GUI.component.TaskListModel;
import com.fromLab.action.taskToolWindowAction.AuthenticationAction;
import com.fromLab.action.taskToolWindowAction.RefreshAction;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.loader.IconsLoader;
import com.fromLab.service.TaskService;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.*;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.impl.ToolWindowImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zyh
 * @date 2020-03-31
 */
public class TaskToolWindow implements ToolWindowFactory {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private ToolWindow customToolWindow;
    private JPanel panel;
    private JPanel contentPanel;
    private JPanel controlPanel;
    private JButton chooseButton;
    private JButton stopButton;
    private JButton moreButton;
    private JList taskList;
    private TaskService taskService;
    private OpenprojectURL openprojectURL;
    private String originalUrl;
    private Boolean isLogin = false;
    private Boolean chosen = false;
    private Task selectedTask = null;
    private List<Task> datasource;
    private Long startTime = 0L;
    private Long endTime = 0L;
    private String spentTimeCustomFieldName;
    private BigDecimal timeSpent;

    private SocketServer socketServer;
    private Thread thread;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        customToolWindow = toolWindow;
        this.socketServer=new SocketServer();
        this.socketServer.start();
        this.thread=new Thread(socketServer);
        this.thread.start();
        taskService = new TaskServiceImpl();
        this.datasource = new ArrayList<>();
        timeSpent = BigDecimal.ZERO;

        panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));


        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));

        taskList = new JList();
        taskList.setModel(new TaskListModel());
        taskList.setBounds(0, 0, 500, 200);
        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBounds(0, 0, 500, 200);
        contentPanel.add(scrollPane);
        controlPanel = new JPanel();
        controlPanel.setLayout(null);
        chooseButton = new JButton("choose");
        chooseButton.setBounds(10, 10, 100, 30);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choose();
            }
        });
        stopButton = new JButton("stop");
        stopButton.setBounds(150, 10, 100, 30);
        paintStopButton();
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        moreButton = new JButton("more");
        moreButton.setBounds(290, 10, 100, 30);
        moreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                more();
            }
        });
        controlPanel.add(chooseButton);
        controlPanel.add(stopButton);
        controlPanel.add(moreButton);
        contentPanel.add(controlPanel);

        panel.add(contentPanel);


        RefreshAction refreshAction = new RefreshAction("Refresh", "Refresh Task Data", AllIcons.Actions.Refresh, this);
        AuthenticationAction authenticationAction = new AuthenticationAction("Authentication",
                "Authentication", IconsLoader.AUTHENTICATION, this);
        ((ToolWindowImpl) toolWindow).setTitleActions(refreshAction, authenticationAction);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, null, false);
        toolWindow.getContentManager().addContent(content);

    }

    public void init(){
        //获取自定义字段的名称
        try {
            this.spentTimeCustomFieldName = GetCustomFieldNumUtil.getCustomFieldNum("Time spent", openprojectURL);
        } catch (BusinessException e) {
            String errorMsg = e.getMessage();
            JOptionPane.showMessageDialog(null, errorMsg,
                    "Tips", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
            return;
        }
    }



    public void refresh() {
        if(this.isLogin){
            this.openprojectURL.setOpenProjectURL(originalUrl);
            try {
                this.datasource = this.taskService.getTasksByConditions(this.openprojectURL, null, null,
                        null, null, null, null);

                String[] taskStringDatasource = this.datasource.stream().map(
                        task -> "ID:" + task.getTaskId() + "   Subject:" + task.getTaskName() +
                                "   Due Time:" + (task.getDueTime() == "null" ? ' ' : task.getDueTime())
                ).collect(Collectors.toList()).toArray(new String[]{});
                this.taskList.setModel(new TaskListModel(taskStringDatasource));
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Unauthorized!",
                    "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.ERROR_ICON);
        }


    }

    public void choose() {
        if(isLogin){
            if(!chosen){
                Object selectedTaskInfo = this.taskList.getSelectedValue();
                if(selectedTaskInfo == null){
                    JOptionPane.showMessageDialog(null, "You need to choose the task",
                            "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
                    return;
                }
                String selectedTaskString = selectedTaskInfo.toString();
                Integer selectedTaskId = getTaskId(selectedTaskString);
                this.openprojectURL.setOpenProjectURL(originalUrl);
                try {
                    this.selectedTask = this.taskService.getTaskById(openprojectURL, selectedTaskId);
                } catch (BusinessException e) {
                    JOptionPane.showMessageDialog(null, "Fail to get task by Id",
                            "Tips", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
                }
                if(selectedTask.getTaskId() != null){
                    if("null".equals(this.selectedTask.getStartTime())){
                        this.openprojectURL.setOpenProjectURL(originalUrl);
                        String startDate = DateUtils.date2String(new Date());
                        String result = this.taskService.updateStartDate(openprojectURL, this.selectedTask.getTaskId(),
                                this.selectedTask.getLockVersion(), startDate);
                        if(StringUtils.equals(result, ERROR)){
                            JOptionPane.showMessageDialog(null, "Fail to save the start Date of the task!\nPlease select again!",
                                    "Tips", JOptionPane.ERROR_MESSAGE, IconsLoader.WARNING_ICON);
                            return;
                        }
                    }
                    Integer selectedIndex = this.taskList.getSelectedIndex();
                    repaintSelectedTask(selectedIndex);
                    this.chosen = true;
                    this.stopButton.setEnabled(true);
                    socketServer.task = this.selectedTask;
                    this.startTime = System.currentTimeMillis();
                    System.out.println("Start Task  Time:" + this.startTime);
                    JOptionPane.showMessageDialog(null, "Success to select task!",
                            "Tips", JOptionPane.PLAIN_MESSAGE, IconsLoader.SUCCESS_ICON);
                }

            }else{
                JOptionPane.showMessageDialog(null, "You have selected another task",
                        "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Unauthorized!",
                    "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.ERROR_ICON);
        }

    }

    public void stop(){
        if(isLogin){
            if(chosen){
                Object selectedTaskInfo = this.taskList.getSelectedValue();
                if(selectedTaskInfo == null){
                    JOptionPane.showMessageDialog(null, "You need to choose the task",
                            "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
                    return;
                }
                String selectedTaskString = selectedTaskInfo.toString();
                Integer selectedTaskId = getTaskId(selectedTaskString);
                if(!this.selectedTask.getTaskId().equals(selectedTaskId)){
                    JOptionPane.showMessageDialog(null, "You select a wrong task",
                            "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
                }

                this.openprojectURL.setOpenProjectURL(originalUrl);
                if(selectedTask.getTaskId() != null){
                    if(this.endTime == 0L){
                        this.endTime = System.currentTimeMillis();
                    }
                    if(timeSpent.compareTo(BigDecimal.ZERO) == 0){
                        timeSpent = NumberUtils.covertTimeToHour(this.endTime - this.startTime);
                    }
                    System.out.println("Stop Task! The Spent Time is: " + timeSpent + "h");

                    String result = this.taskService.updateSpentTime(openprojectURL, this.selectedTask.getTaskId(),
                            this.selectedTask.getLockVersion(), NumberUtils.calUpdateTimeSpent(this.selectedTask.getTimeSpent(), timeSpent),
                            this.spentTimeCustomFieldName);
                    //先发一次update请求，如果成功表示更新成功
                    if (SUCCESS.equals(result)){
                        this.chosen = false;
                        this.stopButton.setEnabled(false);
                        socketServer.task = null;
                        //获取所选行的task的progress
                        String progressString = this.selectedTask.getProgress();
                        Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
                        this.openprojectURL.setOpenProjectURL(originalUrl);
                        new StopTaskModal(this.selectedTask, progress,  openprojectURL);
                        deleteSelectedFlag(selectedTaskId);
                        this.startTime = 0L;
                        this.endTime = 0L;
                        this.timeSpent = BigDecimal.ZERO;
                        this.chosen = false;
                        this.selectedTask = null;
                    }
                    //如果没成功，可能是服务器问题，也有可能是因为lock_version不正确，因此重新获取task，然后再发一次update请求
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
                            //获取所选行的task的progress
                            String progressString = this.selectedTask.getProgress();
                            Integer progress = Integer.parseInt(progressString.substring(0, progressString.indexOf("%")));
                            this.openprojectURL.setOpenProjectURL(originalUrl);
                            new StopTaskModal(this.selectedTask, progress,  openprojectURL);
                            deleteSelectedFlag(selectedTaskId);
                            this.startTime = 0L;
                            this.endTime = 0L;
                            this.timeSpent = BigDecimal.ZERO;
                            this.chosen = false;
                            this.selectedTask = null;
                        }else{
                            JOptionPane.showMessageDialog(null, "Fail to save task",
                                    "Tips", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
                        }
                    }
                }

            }else{
                JOptionPane.showMessageDialog(null, "There is no selected task",
                        "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Unauthorized!",
                    "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.ERROR_ICON);
        }

    }

    private void more() {
        if(isLogin){
            //打开SelectTaskDialog
            int width = 1040;
            int height = 600;
            this.openprojectURL.setOpenProjectURL(originalUrl);
            SelectTaskDialog selectTaskDialog = new SelectTaskDialog(this.openprojectURL, this.chosen,
                    this.selectedTask, this.startTime, this.socketServer, this);
            selectTaskDialog.pack();
            selectTaskDialog.setBounds(GUIUtils.getCenterX(width), GUIUtils.getCenterY(height), width, height);
            selectTaskDialog.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Unauthorized!",
                    "Tips", JOptionPane.WARNING_MESSAGE, IconsLoader.ERROR_ICON);
        }

    }





    public Integer getTaskId(String taskString) {
        if (StringUtils.isBlank(taskString)) {
            return null;
        }
        int start = taskString.indexOf(":");
        int end = taskString.indexOf(" ");
        String taskIdString = taskString.substring(start + 1, end);
        Integer taskId = null;
        try {
            taskId = Integer.parseInt(taskIdString);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Fail to select a task", "Tips", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
            return null;
        }
        return taskId;
    }

    public void repaintSelectedTask(Integer index){
        ((TaskListModel)this.taskList.getModel()).getValues()[index] = "*" +
                ((TaskListModel)this.taskList.getModel()).getValues()[index];
        this.taskList.repaint();
    }

    public void setSelectedFlag(Integer taskId){
        String[] values = ((TaskListModel) this.taskList.getModel()).getValues();
        for (int i = 0; i < values.length; i++) {
            String taskString = values[i];
            Integer selectedId = getTaskId(taskString);
            if(selectedId.equals(taskId)){
                repaintSelectedTask(i);
            }
        }
    }

    public void deleteSelectedFlag(Integer taskId){
        String[] values = ((TaskListModel) this.taskList.getModel()).getValues();
        for (int i = 0; i < values.length; i++) {
            String taskString = values[i];
            Integer selectedId = getTaskId(taskString);
            if(selectedId.equals(taskId)){
               values[i] = taskString.substring(1);
               this.taskList.repaint();
            }
        }
    }

    public void paintStopButton(){
        if(!this.chosen){
            this.stopButton.setEnabled(false);
        }
    }


    public void setOpenprojectURL(OpenprojectURL openprojectURL) {
        this.openprojectURL = openprojectURL;
        this.originalUrl = openprojectURL.getOpenProjectURL();
    }

    public OpenprojectURL getOpenprojectURL() {
        return this.openprojectURL;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen){
        this.chosen = chosen;
    }

    public void setSelectedTask(Task selectedTask){
        this.selectedTask = selectedTask;
    }

    public Task getSelectedTask(){
        return this.selectedTask;
    }

    public void setIsLogin(Boolean isLogin){
        this.isLogin = isLogin;
    }

    public Boolean getIsLogin(){
        return isLogin;
    }
}

