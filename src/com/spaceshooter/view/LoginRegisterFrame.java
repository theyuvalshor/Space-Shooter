package com.spaceshooter.view;

import com.spaceshooter.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class LoginRegisterFrame extends JFrame{
    private Controller controller;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public LoginRegisterFrame()  {
        setTitle("Login");

        setPreferredSize(new Dimension(350,200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        registerPanel = new RegisterPanel();
        loginPanel = new LoginPanel();

        add(loginPanel);
        pack();
        addPanelListeners();
    }

    private void addPanelListeners() {
        registerPanel.setRegisterButtonListener(new RegisterPanelRegisterButtonClickListener() {
            @Override
            public void registerButtonClicked(String username, String password) {
                if(controller != null){
                    controller.register(username, password);
                    dispose();
                    controller.startGameWindow();
                }
            }
        });

        registerPanel.setCancelButtonListener(new CancelButtonClickListener() {
            @Override
            public void mouseCancelButtonClick() {
                if(controller != null) {
                    dispose();
                }
            }
        });

        loginPanel.setLoginButtonClickListener(new LoginPanelLoginButtonClickListener() {
            @Override
            public void loginButtonClick(String username, String password) throws Exception {
                if(controller.login(username, password)){
                    if(controller != null) {
                        dispose();
                        controller.startGameWindow();
                    }
                }
                else{
                    setContentPane(registerPanel);
                    pack();
                }
            }
        });

        loginPanel.setCancelButtonClickListener(new CancelButtonClickListener() {
            @Override
            public void mouseCancelButtonClick() {
                dispose();
            }
        });
    }

    public void setController(Controller gameController) {
        this.controller = gameController;
    }
}