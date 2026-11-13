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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class RoomManagementFrame extends JFrame {

    private RoomDAO roomDAO;

    private JTable roomTable;
    private DefaultTableModel tableModel;

    public RoomManagementFrame() {

        roomDAO = new RoomDAO();

        setTitle("Hotel Management System - Room Management");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadRooms();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // TOP BUTTON PANEL
        // =========================

        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 5, 10, 10)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton addButton =
                new JButton("Add Room");

        JButton updateButton =
                new JButton("Update Room");

        JButton deleteButton =
                new JButton("Delete Room");

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(
                buttonPanel,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns = {
                "Room ID",
                "Room Number",
                "Room Type",
                "Price / Day",
                "Status"
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

        roomTable = new JTable(tableModel);

        roomTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane =
                new JScrollPane(roomTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        addButton.addActionListener(
                e -> addRoom()
        );

        updateButton.addActionListener(
                e -> updateRoom()
        );

        deleteButton.addActionListener(
                e -> deleteRoom()
        );

        refreshButton.addActionListener(
                e -> loadRooms()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD ROOMS
    // =========================

    private void loadRooms() {

        tableModel.setRowCount(0);

        List<Room> rooms =
                roomDAO.getAllRooms();

        for (Room room : rooms) {

            Object[] row = {

                    room.getRoomId(),

                    room.getRoomNumber(),

                    room.getRoomType(),

                    room.getPricePerDay(),

                    room.getStatus()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // ADD ROOM
    // =========================

    private void addRoom() {

        JTextField roomNumberField =
                new JTextField();

        JTextField roomTypeField =
                new JTextField();

        JTextField priceField =
                new JTextField();

        JTextField statusField =
                new JTextField("AVAILABLE");

        JPanel panel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(
                new JLabel("Room Number:")
        );

        panel.add(roomNumberField);

        panel.add(
                new JLabel("Room Type:")
        );

        panel.add(roomTypeField);

        panel.add(
                new JLabel("Price Per Day:")
        );

        panel.add(priceField);

        panel.add(
                new JLabel("Status:")
        );

        panel.add(statusField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Room",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            int roomNumber =
                    Integer.parseInt(
                            roomNumberField.getText()
                    );

            double price =
                    Double.parseDouble(
                            priceField.getText()
                    );

            String roomType =
                    roomTypeField.getText()
                            .trim()
                            .toUpperCase();

            String status =
                    statusField.getText()
                            .trim()
                            .toUpperCase();

            if (roomType.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room type cannot be empty."
                );

                return;
            }

            if (price <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price must be greater than zero."
                );

                return;
            }

            Room room = new Room(
                    roomNumber,
                    roomType,
                    price,
                    status
            );

            boolean success =
                    roomDAO.addRoom(room);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room added successfully!"
                );

                loadRooms();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add room.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // UPDATE ROOM
    // =========================

    private void updateRoom() {

        int selectedRow =
                roomTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a room first."
            );

            return;
        }

        int roomId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        int currentRoomNumber =
                (int) tableModel.getValueAt(
                        selectedRow,
                        1
                );

        String currentType =
                tableModel.getValueAt(
                        selectedRow,
                        2
                ).toString();

        double currentPrice =
                Double.parseDouble(
                        tableModel.getValueAt(
                                selectedRow,
                                3
                        ).toString()
                );

        String currentStatus =
                tableModel.getValueAt(
                        selectedRow,
                        4
                ).toString();

        JTextField roomNumberField =
                new JTextField(
                        String.valueOf(currentRoomNumber)
                );

        JTextField roomTypeField =
                new JTextField(currentType);

        JTextField priceField =
                new JTextField(
                        String.valueOf(currentPrice)
                );

        JTextField statusField =
                new JTextField(currentStatus);

        JPanel panel =
                new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(
                new JLabel("Room Number:")
        );

        panel.add(roomNumberField);

        panel.add(
                new JLabel("Room Type:")
        );

        panel.add(roomTypeField);

        panel.add(
                new JLabel("Price Per Day:")
        );

        panel.add(priceField);

        panel.add(
                new JLabel("Status:")
        );

        panel.add(statusField);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Room",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {

            int roomNumber =
                    Integer.parseInt(
                            roomNumberField.getText()
                    );

            double price =
                    Double.parseDouble(
                            priceField.getText()
                    );

            String roomType =
                    roomTypeField.getText()
                            .trim()
                            .toUpperCase();

            String status =
                    statusField.getText()
                            .trim()
                            .toUpperCase();

            Room room = new Room(
                    roomNumber,
                    roomType,
                    price,
                    status
            );

            room.setRoomId(roomId);

            boolean success =
                    roomDAO.updateRoom(room);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room updated successfully!"
                );

                loadRooms();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to update room.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // DELETE ROOM
    // =========================

    private void deleteRoom() {

        int selectedRow =
                roomTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a room first."
            );

            return;
        }

        int roomId =
                (int) tableModel.getValueAt(
                        selectedRow,
                        0
                );

        int roomNumber =
                (int) tableModel.getValueAt(
                        selectedRow,
                        1
                );

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete room " +
                                roomNumber +
                                "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        boolean success =
                roomDAO.deleteRoom(roomId);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Room deleted successfully!"
            );

            loadRooms();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete room.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}