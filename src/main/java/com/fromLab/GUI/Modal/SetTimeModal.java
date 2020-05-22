package com.fromLab.GUI.Modal;

import com.fromLab.GUI.component.DateChooserJButton;
import com.fromLab.GUI.component.TaskTableModel;
import com.fromLab.VO.TaskVO;
import com.fromLab.entity.Task;
import com.fromLab.exception.BusinessException;
import com.fromLab.loader.IconsLoader;
import com.fromLab.service.impl.TaskServiceImpl;
import com.fromLab.utils.DateUtils;
import com.fromLab.utils.GUIUtils;
import com.fromLab.utils.OpenprojectURL;
import com.fromLab.utils.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

/**
 * @author wsh
 * @date 2019-12-17
 * The dialog for setting the end date and update it
 */
public class SetTimeModal extends JFrame {


    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    private SelectTaskDialog jDialog;
    private OpenprojectURL openprojectURL;
    private Task task;
    private String originalUrl;
    private String endDateCustomFieldName;
    private TaskServiceImpl taskService;
    private JPanel timePanel;
    private JLabel timeLabel;
    private DateChooserJButton dateChooserJButton;
    private JButton okButton;
    private JButton cancelButton;

    public SetTimeModal(SelectTaskDialog dialog, Task task, String endDateCustomFieldName, OpenprojectURL openprojectURL) {
        this.task = task;
        this.jDialog = dialog;
        this.openprojectURL = openprojectURL;
        this.endDateCustomFieldName = endDateCustomFieldName;
        this.originalUrl = this.openprojectURL.getOpenProjectURL();
        taskService = new TaskServiceImpl();
        timePanel = new JPanel();
        timePanel.setBounds(0, 0, 300, 150);
        timePanel.setLayout(null);
        timeLabel = new JLabel("End Date:");
        timeLabel.setBounds(50, 0, 100, 100);
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

    private void onOk() {
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
        openprojectURL.setOpenProjectURL(this.originalUrl);
        String time = this.dateChooserJButton.getText();
        if (time == null) {
            time = DateUtils.date2String(new Date());
        }
        Date endDate = DateUtils.string2Date(time);
        Date startDate = null;
        if (task.getStartTime() == null || StringUtils.equals(task.getStartTime(), "null")) {
            startDate = new Date();
        } else {
            startDate = DateUtils.string2Date(task.getStartTime());
        }
        if (startDate.after(endDate)) {
            this.setVisible(false);
            JOptionPane.showOptionDialog(null, "The end date can not be smaller than the start date", "Tips",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, IconsLoader.WARNING_ICON, jButtons, jButtons[0]);
            this.setVisible(true);
            return;
        }
        String date = DateUtils.date2String(endDate);
        //Update the end date
        String result = this.taskService.updateEndDate(openprojectURL, this.task.getTaskId(),
                this.task.getLockVersion(), date, this.endDateCustomFieldName);
        if (StringUtils.equals(result, SUCCESS)) {
            this.dispose();
            JOptionPane.showOptionDialog(null, "Save successfully", "Tips",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, IconsLoader.SUCCESS_ICON, jButtons, jButtons[0]);
        } else {
            openprojectURL.setOpenProjectURL(originalUrl.substring(0, originalUrl.lastIndexOf("/") + 1));
            Task tempTask = new Task();
            try {
                tempTask = this.taskService.getTaskById(openprojectURL, this.task.getTaskId());
            } catch (BusinessException e) {
                tempTask = null;
            }
            openprojectURL.setOpenProjectURL(originalUrl.substring(0, originalUrl.lastIndexOf("/") + 1));
            if (tempTask == null) {
                this.setVisible(false);
                JOptionPane.showOptionDialog(null, "Fail to save", "Tips",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON, jButtons, jButtons[0]);
                this.setVisible(true);
                return;
            } else {
                String response = this.taskService.updateEndDate(openprojectURL, this.task.getTaskId(),
                        this.task.getLockVersion(), date, this.endDateCustomFieldName);
                if (StringUtils.equals(response, SUCCESS)) {
                    this.dispose();
                    JOptionPane.showOptionDialog(null, "Save successfully", "Tips",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, IconsLoader.SUCCESS_ICON, jButtons, jButtons[0]);
                } else {
                    this.setVisible(false);
                    JOptionPane.showOptionDialog(null, "Fail to save", "Tips",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON, jButtons, jButtons[0]);
                    this.setVisible(true);
                    return;
                }
            }
        }

        this.jDialog.resetTableDataSource();
        this.jDialog.setVisible(true);


    }

    private void onCancel() {
        this.setVisible(false);
        this.jDialog.setVisible(true);
    }

}
