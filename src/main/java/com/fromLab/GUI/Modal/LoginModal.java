package com.fromLab.GUI.Modal;

import com.fromLab.GUI.window.TaskToolWindow;
import com.fromLab.loader.IconsLoader;
import com.fromLab.service.UserService;
import com.fromLab.service.impl.UserServiceImpl;
import com.fromLab.utils.GUIUtils;
import com.fromLab.utils.OpenprojectURL;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zyh
 * @date 2020-03-01
 * The dialog for loggin
 */

public class LoginModal extends JFrame {
    private TaskToolWindow taskToolWindow;
    private UserService userService;
    private JPanel contentPanel;
    private JLabel openProjectUrlLabel;
    private JTextField openProjectUrlTextField;
    private JLabel apiLabel;
    private JTextField apiTextField;
    private JButton loginButton;

    public LoginModal(TaskToolWindow taskToolWindow) {
        this.taskToolWindow = taskToolWindow;
        userService = new UserServiceImpl();
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        openProjectUrlLabel = new JLabel("Open Project URL: ");
        openProjectUrlLabel.setBounds(50, 30, 200, 20);
        contentPanel.add(openProjectUrlLabel);

        openProjectUrlTextField = new JTextField();
        openProjectUrlTextField.setBounds(170, 25, 350, 30);
        contentPanel.add(openProjectUrlTextField);

        apiLabel = new JLabel("API Key: ");
        apiLabel.setBounds(50, 80, 200, 20);
        contentPanel.add(apiLabel);

        apiTextField = new JTextField();
        apiTextField.setBounds(170, 75, 350, 30);
        contentPanel.add(apiTextField);

        loginButton = new JButton("confirm");
        loginButton.setBounds(250, 130, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConfirm();
            }
        });
        contentPanel.add(loginButton);

        contentPanel.setBounds(0, 0, 600, 300);
        this.add(contentPanel);
    }

    private void onConfirm() {
        //get the OpenProject url
        String openProjectURL = this.openProjectUrlTextField.getText();
        //get the API key
        String apiKey = this.apiTextField.getText();
        //Verify that openProjectURL and API Key are legal
        if (StringUtils.isBlank(openProjectURL) || StringUtils.isBlank(apiKey)) {
            if (StringUtils.isBlank(openProjectURL)) {
                //Prompt dialog（need to input openProjectUrl）
                this.setVisible(false);
                showOptionDialog("You need to input Open Project URL!", JOptionPane.ERROR_MESSAGE, IconsLoader.WARNING_ICON);
            } else {
                //Prompt dialog（need to input API Key）
                this.setVisible(false);
                showOptionDialog("You need to input API Key!", JOptionPane.ERROR_MESSAGE, IconsLoader.WARNING_ICON);
            }
        } else {
            //Verify that the login was successful
            boolean flag = userService.authorize(openProjectURL, apiKey);
            //If succeed
            if (flag) {
                this.setVisible(false);
                OpenprojectURL openprojectURL = new OpenprojectURL(openProjectURL + OpenprojectURL.WORK_PACKAGES_URL, apiKey);
                this.taskToolWindow.setOpenprojectURL(openprojectURL);
                showOptionDialog("Success to authorize!", JOptionPane.ERROR_MESSAGE, IconsLoader.SUCCESS_ICON);
                //Modify the flag isLogin

                this.taskToolWindow.setIsLogin(true);
                //Search and display the task on tool window
                this.taskToolWindow.refresh();
                this.taskToolWindow.init();

            } else {
                //If it fails
                this.setVisible(false);
                showOptionDialog("Unauthorized", JOptionPane.ERROR_MESSAGE, IconsLoader.ERROR_ICON);
            }
        }

    }

    private void showOptionDialog(String info, Integer type, Icon icon) {
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
        JOptionPane.showOptionDialog(null, info, "Tips", type, type, icon, jButtons, jButtons[0]);
        this.dispose();
    }
}
