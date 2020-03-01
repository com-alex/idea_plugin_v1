package com.fromLab.GUI.Modal;

import com.fromLab.service.UserService;
import com.fromLab.service.impl.UserServiceImpl;
import com.fromLab.utils.GUIUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zyh
 * @date 2020-03-01
 * 设置用户登录的模态框
 */

public class LoginModal extends JFrame {
    private UserService userService;
    private JPanel contentPanel;
    private JLabel openProjectUrlLabel;
    private JTextField openProjectUrlTextField;
    private JLabel apiLabel;
    private JTextField apiTextField;
    private JButton loginButton;

    public LoginModal(){
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
        loginButton.setBounds(250, 130,100, 30);
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

    private void onConfirm(){
        //获取openProjectURL
        String openProjectURL = this.openProjectUrlTextField.getText();
        //获取API Key
        String apiKey = this.apiTextField.getText();
        //验证openProjectURL和API Key是否合法
        if(StringUtils.isBlank(openProjectURL) || StringUtils.isBlank(apiKey)){
            if(StringUtils.isBlank(openProjectURL)){
                //提示框（需要填写openProjectUrl）
                this.setVisible(false);
                showOptionDialog("You need to input Open Project URL!", JOptionPane.ERROR_MESSAGE);
//                JOptionPane.showMessageDialog(null,
//                        "You need to input Open Project URL!", "Tips", JOptionPane.PLAIN_MESSAGE);
            }else{
                //提示框（需要填写API Key）
                this.setVisible(false);
                showOptionDialog("You need to input API Key!", JOptionPane.ERROR_MESSAGE);
//                JOptionPane.showMessageDialog(null,
//                        "You need to input Open Project URL!", "Tips", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            //验证是否认证成功
            boolean flag = userService.authorize(openProjectURL, apiKey);
            if(flag){
                //成功
                //打开SelectTaskDialog
                int width = 1040;
                int height = 600;
                SelectTaskDialog selectTaskDialog = new SelectTaskDialog(openProjectURL, apiKey);
                selectTaskDialog.pack();
                selectTaskDialog.setBounds(GUIUtils.getCenterX(width), GUIUtils.getCenterY(height), width, height);
                selectTaskDialog.setVisible(true);
                //把自己关闭
                this.setVisible(false);
            }
            else{
                //不成功
                this.setVisible(false);
                showOptionDialog("Unauthorized", JOptionPane.ERROR_MESSAGE);
            }
        }

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
        JOptionPane.showOptionDialog(null, info, "Tips", type, type, null, jButtons, jButtons[0]);
        this.setVisible(true);
    }
}
