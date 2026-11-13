package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hotel.dao.GuestDAO;
import com.hotel.model.Guest;

public class GuestRegistrationFrame extends JFrame {

    private GuestDAO guestDAO;

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField idTypeField;
    private JTextField idNumberField;

    public GuestRegistrationFrame() {

        guestDAO = new GuestDAO();

        setTitle(
                "Hotel Management System - Register Guest"
        );

        setSize(500, 400);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // FORM PANEL
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                5,
                                2,
                                10,
                                10
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        40,
                        20,
                        40
                )
        );

        nameField = new JTextField();

        phoneField = new JTextField();

        emailField = new JTextField();

        idTypeField = new JTextField();

        idNumberField = new JTextField();

        formPanel.add(
                new JLabel("Name:")
        );

        formPanel.add(
                nameField
        );

        formPanel.add(
                new JLabel("Phone:")
        );

        formPanel.add(
                phoneField
        );

        formPanel.add(
                new JLabel("Email:")
        );

        formPanel.add(
                emailField
        );

        formPanel.add(
                new JLabel("ID Type:")
        );

        formPanel.add(
                idTypeField
        );

        formPanel.add(
                new JLabel("ID Number:")
        );

        formPanel.add(
                idNumberField
        );

        add(
                formPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                10
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        40,
                        20,
                        40
                )
        );

        JButton registerButton =
                new JButton(
                        "Register Guest"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );

        buttonPanel.add(
                registerButton
        );

        buttonPanel.add(
                backButton
        );

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // ACTIONS
        // =========================

        registerButton.addActionListener(
                e -> registerGuest()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // REGISTER GUEST
    // =========================

    private void registerGuest() {

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        String idType =
                idTypeField.getText().trim();

        String idNumber =
                idNumberField.getText().trim();

        // =========================
        // VALIDATION
        // =========================

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter guest name.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();

            return;
        }

        if (phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter guest phone number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            phoneField.requestFocus();

            return;
        }

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly "
                            + "10 digits.",
                    "Invalid Phone",
                    JOptionPane.WARNING_MESSAGE
            );

            phoneField.requestFocus();

            return;
        }

        if (!email.isEmpty()
                && !email.matches(
                        "^[A-Za-z0-9+_.-]+@(.+)$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );

            emailField.requestFocus();

            return;
        }

        if (idType.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter ID type.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            idTypeField.requestFocus();

            return;
        }

        if (idNumber.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter ID number.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            idNumberField.requestFocus();

            return;
        }

        // =========================
        // CREATE GUEST
        // =========================

        Guest guest =
                new Guest(
                        name,
                        phone,
                        email,
                        idType,
                        idNumber
                );

        boolean success =
                guestDAO.addGuest(guest);

        // =========================
        // RESULT
        // =========================

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest registered successfully!\n\n"
                            + "Guest ID: "
                            + guest.getGuestId()
            );

            clearFields();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to register guest.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CLEAR FORM
    // =========================

    private void clearFields() {

        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        idTypeField.setText("");
        idNumberField.setText("");

        nameField.requestFocus();
    }
}