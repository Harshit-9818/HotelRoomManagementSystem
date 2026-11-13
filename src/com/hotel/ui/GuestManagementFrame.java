package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.hotel.dao.GuestDAO;
import com.hotel.model.Guest;

public class GuestManagementFrame extends JFrame {

    private GuestDAO guestDAO;

    private JTable guestTable;
    private DefaultTableModel tableModel;

    public GuestManagementFrame() {

        guestDAO = new GuestDAO();

        setTitle("Hotel Management System - Guests");
        setSize(950, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadGuests();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 3, 10, 10));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton addButton =
                new JButton("Register Guest");

        JButton updateButton =
                new JButton("Update Guest");

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Guest ID",
                "Name",
                "Phone",
                "Email",
                "ID Type",
                "ID Number"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        guestTable = new JTable(tableModel);

        guestTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(guestTable);

        add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> registerGuest()
        );

        updateButton.addActionListener(
                e -> updateGuest()
        );

        refreshButton.addActionListener(
                e -> loadGuests()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD GUESTS
    // =========================

    private void loadGuests() {

        tableModel.setRowCount(0);

        List<Guest> guests =
                guestDAO.getAllGuests();

        for (Guest guest : guests) {

            Object[] row = {

                    guest.getGuestId(),
                    guest.getName(),
                    guest.getPhone(),
                    guest.getEmail(),
                    guest.getIdType(),
                    guest.getIdNumber()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // REGISTER GUEST
    // =========================

    private void registerGuest() {

        JPanel panel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        javax.swing.JTextField nameField =
                new javax.swing.JTextField();

        javax.swing.JTextField phoneField =
                new javax.swing.JTextField();

        javax.swing.JTextField emailField =
                new javax.swing.JTextField();

        javax.swing.JTextField idTypeField =
                new javax.swing.JTextField();

        javax.swing.JTextField idNumberField =
                new javax.swing.JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("ID Type:"));
        panel.add(idTypeField);

        panel.add(new JLabel("ID Number:"));
        panel.add(idNumberField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Register Guest",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

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

        if (name.isEmpty() || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name and phone are required.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Guest guest = new Guest(
                name,
                phone,
                email,
                idType,
                idNumber
        );

        boolean success =
                guestDAO.addGuest(guest);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest registered successfully!\n"
                            + "Guest ID: "
                            + guest.getGuestId()
            );

            loadGuests();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to register guest.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // UPDATE GUEST
    // =========================

    private void updateGuest() {

        int selectedRow =
                guestTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a guest first."
            );

            return;
        }

        int guestId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        javax.swing.JTextField nameField =
                new javax.swing.JTextField(
                        tableModel.getValueAt(
                                selectedRow, 1
                        ).toString()
                );

        javax.swing.JTextField phoneField =
                new javax.swing.JTextField(
                        tableModel.getValueAt(
                                selectedRow, 2
                        ).toString()
                );

        javax.swing.JTextField emailField =
                new javax.swing.JTextField(
                        tableModel.getValueAt(
                                selectedRow, 3
                        ) == null
                                ? ""
                                : tableModel.getValueAt(
                                        selectedRow, 3
                                ).toString()
                );

        javax.swing.JTextField idTypeField =
                new javax.swing.JTextField(
                        tableModel.getValueAt(
                                selectedRow, 4
                        ) == null
                                ? ""
                                : tableModel.getValueAt(
                                        selectedRow, 4
                                ).toString()
                );

        javax.swing.JTextField idNumberField =
                new javax.swing.JTextField(
                        tableModel.getValueAt(
                                selectedRow, 5
                        ) == null
                                ? ""
                                : tableModel.getValueAt(
                                        selectedRow, 5
                                ).toString()
                );

        JPanel panel =
                new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("ID Type:"));
        panel.add(idTypeField);

        panel.add(new JLabel("ID Number:"));
        panel.add(idNumberField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Guest",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name and phone are required.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Guest guest = new Guest(
                name,
                phone,
                emailField.getText().trim(),
                idTypeField.getText().trim(),
                idNumberField.getText().trim()
        );

        guest.setGuestId(guestId);

        boolean success =
                guestDAO.updateGuest(guest);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest updated successfully!"
            );

            loadGuests();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update guest.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}