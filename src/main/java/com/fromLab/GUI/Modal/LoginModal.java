package com.fromLab.GUI.Modal;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zyh
 * @date 2020-03-01
 * �����û���¼��ģ̬��
 */

public class LoginModal extends JFrame {
    private final static String OPENPROJECT_URL="";
    private final static String API_KEY="";
    private JPanel contentPanel;
    private JLabel openProjectUrlLabel;
    private JTextField openProjectUrlTextField;
    private JLabel apiLabel;
    private JTextField apiTextField;
    private JButton loginButton;

    public LoginModal(){
        contentPanel = new JPanel();
        contentPanel.setLayout(null);

        openProjectUrlLabel = new JLabel("Open Project URL: ");
        openProjectUrlLabel.setBounds(50, 30, 200, 20);
        contentPanel.add(openProjectUrlLabel);

        openProjectUrlTextField = new JTextField();
        openProjectUrlTextField.setBounds(170, 30, 350, 20);
        contentPanel.add(openProjectUrlTextField);

        apiLabel = new JLabel("API Key: ");
        apiLabel.setBounds(50, 70, 200, 20);
        contentPanel.add(apiLabel);

        apiTextField = new JTextField();
        apiTextField.setBounds(170, 70, 350, 20);
        contentPanel.add(apiTextField);

        loginButton = new JButton("confirm");
        loginButton.setBounds(250, 120,100, 20);
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
        //��ȡopenProjectURL
        String openProjectURL = this.openProjectUrlTextField.getText();
        //��ȡAPI Key
        String apiKey = this.apiTextField.getText();
        //��֤openProjectURL��API Key�Ƿ�Ϸ�
        if(StringUtils.isBlank(openProjectURL) || StringUtils.isBlank(apiKey)){
            if(StringUtils.isBlank(openProjectURL)){
                //��ʾ����Ҫ��дopenProjectUrl��
                this.setVisible(false);
                showOptionDialog("You need to input Open Project URL!", JOptionPane.ERROR_MESSAGE);
//                JOptionPane.showMessageDialog(null,
//                        "You need to input Open Project URL!", "Tips", JOptionPane.PLAIN_MESSAGE);
            }else{
                //��ʾ����Ҫ��дAPI Key��
                this.setVisible(false);
                showOptionDialog("You need to input API Key!", JOptionPane.ERROR_MESSAGE);
//                JOptionPane.showMessageDialog(null,
//                        "You need to input Open Project URL!", "Tips", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            //��֤�Ƿ���֤�ɹ�
            boolean flag = false;
            if(flag){
                //�ɹ�
                //��SelectTaskDialog
                //���Լ��ر�
            }
            else{
                //���ɹ�
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
