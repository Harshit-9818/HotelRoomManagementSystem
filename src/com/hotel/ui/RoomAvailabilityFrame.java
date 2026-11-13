package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class RoomAvailabilityFrame extends JFrame {

    private RoomDAO roomDAO;
    private BookingDAO bookingDAO;

    private JTextField checkInField;
    private JTextField checkOutField;

    private JTable roomTable;
    private DefaultTableModel tableModel;

    private DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public RoomAvailabilityFrame() {

        roomDAO = new RoomDAO();
        bookingDAO = new BookingDAO();

        setTitle(
                "Hotel Management System - Room Availability"
        );

        setSize(850, 550);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        setLayout(
                new BorderLayout(10, 10)
        );

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                10,
                                10
                        )
                );

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JLabel checkInLabel =
                new JLabel(
                        "Check-in Date (dd-MM-yyyy):",
                        SwingConstants.CENTER
                );

        JLabel checkOutLabel =
                new JLabel(
                        "Check-out Date (dd-MM-yyyy):",
                        SwingConstants.CENTER
                );

        checkInField =
                new JTextField();

        checkOutField =
                new JTextField();

        JButton searchButton =
                new JButton(
                        "Check Availability"
                );

        searchPanel.add(checkInLabel);
        searchPanel.add(checkOutLabel);
        searchPanel.add(new JLabel());

        searchPanel.add(checkInField);
        searchPanel.add(checkOutField);
        searchPanel.add(searchButton);

        add(
                searchPanel,
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

        tableModel =
                new DefaultTableModel(
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

        roomTable =
                new JTable(tableModel);

        roomTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        roomTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(roomTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                1
                        )
                );

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        20,
                        15,
                        20
                )
        );

        JButton backButton =
                new JButton("Back");

        bottomPanel.add(backButton);

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // ACTIONS
        // =========================

        searchButton.addActionListener(
                e -> checkAvailability()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // CHECK AVAILABILITY
    // =========================

    private void checkAvailability() {

        String checkInText =
                checkInField.getText().trim();

        String checkOutText =
                checkOutField.getText().trim();

        if (checkInText.isEmpty()
                || checkOutText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both dates.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            LocalDate checkIn =
                    LocalDate.parse(
                            checkInText,
                            dateFormatter
                    );

            LocalDate checkOut =
                    LocalDate.parse(
                            checkOutText,
                            dateFormatter
                    );

            if (!checkOut.isAfter(checkIn)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Check-out date must be after "
                                + "check-in date.",
                        "Invalid Dates",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            loadAvailableRooms(
                    checkIn,
                    checkOut
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n"
                            + "Please use dd-MM-yyyy.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // LOAD AVAILABLE ROOMS
    // =========================

    private void loadAvailableRooms(
            LocalDate checkIn,
            LocalDate checkOut) {

        tableModel.setRowCount(0);

        List<Room> rooms =
                roomDAO.getAvailableRooms();

        int availableCount = 0;

        for (Room room : rooms) {

            if (room.getStatus()
                    .equalsIgnoreCase("MAINTENANCE")) {

                continue;
            }

            boolean available =
                    bookingDAO.isRoomAvailable(
                            room.getRoomId(),
                            checkIn,
                            checkOut
                    );

            if (available) {

                Object[] row = {

                        room.getRoomId(),

                        room.getRoomNumber(),

                        room.getRoomType(),

                        "₹" + room.getPricePerDay(),

                        room.getStatus()
                };

                tableModel.addRow(row);

                availableCount++;
            }
        }

        if (availableCount == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No rooms are available for the "
                            + "selected dates.",
                    "No Availability",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}