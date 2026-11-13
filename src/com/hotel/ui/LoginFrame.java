package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.hotel.model.User;
import com.hotel.service.AuthService;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private AuthService authService;

    public LoginFrame() {

        authService = new AuthService();

        setTitle("Hotel Management System - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 40, 25, 40)
        );

        JLabel titleLabel =
                new JLabel(
                        "HOTEL MANAGEMENT SYSTEM",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                titleLabel.getFont().deriveFont(20f)
        );

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Username:"),
                gbc
        );

        usernameField = new JTextField(20);

        gbc.gridx = 1;

        formPanel.add(
                usernameField,
                gbc
        );

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Password:"),
                gbc
        );

        passwordField = new JPasswordField(20);

        gbc.gridx = 1;

        formPanel.add(
                passwordField,
                gbc
        );

        // Login button
        JButton loginButton =
                new JButton("LOGIN");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        formPanel.add(
                loginButton,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // Login button action
        loginButton.addActionListener(
                e -> login()
        );

        // Press Enter to login
        passwordField.addActionListener(
                e -> login()
        );
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty() ||
            password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        User user =
                authService.login(
                        username,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!\nWelcome, "
                            + user.getName(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            if (user.getRole().equals("ADMIN")) {

                new AdminDashboard(user);

            } else if (
                    user.getRole()
                        .equals("RECEPTIONIST")) {

                new ReceptionDashboard(user);

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Unknown user role!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}