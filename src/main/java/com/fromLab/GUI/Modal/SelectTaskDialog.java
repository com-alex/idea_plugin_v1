package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.ReflectionUtils;
import com.fromLab.utils.SortUtils;
import com.intellij.ui.GuiUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectTaskDialog extends JDialog {
    //常量，防止出现魔法值
    private static final String SET_START_TIME_MODAL_FLAG = "start";
    private static final String SET_END_TIME_MODAL_FLAG = "end";

    private Long startTime;
    private Long endTime;
    private List<TaskVO> dataSource;
    private Integer taskPriorityFlag = 0;
    private Integer taskDueTimeFlag = 0;

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

        this.setTableDataSource();



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
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseTask();

            }
        });
        panel1.add(chooseButton);

        stopButton = new JButton("stop");
        stopButton.setBounds(400, 0, 70, 30);
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

    public void getDataSource(){


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
                    if(pick == 3){
                        sortDataSourceOrderByPriority();
                    }
                    //进行deadline排序
                    else if(pick == 7){
                        System.out.println(1);
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
}
