package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
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

import com.hotel.dao.BookingDAO;
import com.hotel.model.Booking;

public class BookingManagementFrame extends JFrame {

    private BookingDAO bookingDAO;

    private JTable bookingTable;
    private DefaultTableModel tableModel;

    private DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BookingManagementFrame() {

        bookingDAO = new BookingDAO();

        setTitle("Hotel Management System - Bookings");
        setSize(1000, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadBookings();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 4, 10, 10));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton detailsButton =
                new JButton("View Details");

        JButton cancelButton =
                new JButton("Cancel Booking");

        JButton refreshButton =
                new JButton("Refresh");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(detailsButton);
        buttonPanel.add(cancelButton);
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
                "Booking ID",
                "Guest ID",
                "Room ID",
                "Check-in",
                "Check-out",
                "Total Amount",
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

        bookingTable =
                new JTable(tableModel);

        bookingTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        bookingTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(bookingTable);

        add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        detailsButton.addActionListener(
                e -> viewBookingDetails()
        );

        cancelButton.addActionListener(
                e -> cancelBooking()
        );

        refreshButton.addActionListener(
                e -> loadBookings()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD BOOKINGS
    // =========================

    private void loadBookings() {

        tableModel.setRowCount(0);

        List<Booking> bookings =
                bookingDAO.getAllBookings();

        for (Booking booking : bookings) {

            String checkIn =
                    booking.getCheckIn() != null
                            ? booking.getCheckIn()
                                    .format(dateFormatter)
                            : "";

            String checkOut =
                    booking.getCheckOut() != null
                            ? booking.getCheckOut()
                                    .format(dateFormatter)
                            : "";

            Object[] row = {

                    booking.getBookingId(),

                    booking.getGuestId(),

                    booking.getRoomId(),

                    checkIn,

                    checkOut,

                    "₹" + booking.getTotalAmount(),

                    booking.getStatus()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // VIEW DETAILS
    // =========================

    private void viewBookingDetails() {

        int selectedRow =
                bookingTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                bookingTable.convertRowIndexToModel(
                        selectedRow
                );

        int bookingId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        Booking booking =
                bookingDAO.getBookingById(
                        bookingId
                );

        if (booking == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        String details =
                "Booking ID: "
                + booking.getBookingId()
                + "\n\n"

                + "Guest ID: "
                + booking.getGuestId()
                + "\n"

                + "Room ID: "
                + booking.getRoomId()
                + "\n\n"

                + "Check-in: "
                + booking.getCheckIn()
                + "\n"

                + "Check-out: "
                + booking.getCheckOut()
                + "\n\n"

                + "Total Amount: ₹"
                + booking.getTotalAmount()
                + "\n"

                + "Status: "
                + booking.getStatus();

        JOptionPane.showMessageDialog(
                this,
                details,
                "Booking Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================
    // CANCEL BOOKING
    // =========================

    private void cancelBooking() {

        int selectedRow =
                bookingTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a booking first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                bookingTable.convertRowIndexToModel(
                        selectedRow
                );

        int bookingId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        String status =
                tableModel.getValueAt(
                        modelRow,
                        6
                ).toString();

        if (status.equals("CANCELLED")) {

            JOptionPane.showMessageDialog(
                    this,
                    "This booking is already cancelled."
            );

            return;
        }

        if (status.equals("CHECKED_OUT")) {

            JOptionPane.showMessageDialog(
                    this,
                    "A checked-out booking cannot be cancelled."
            );

            return;
        }

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel "
                                + "Booking ID "
                                + bookingId
                                + "?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation !=
                JOptionPane.YES_OPTION) {

            return;
        }

        boolean success =
                bookingDAO.cancelBooking(
                        bookingId
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking cancelled successfully!"
            );

            loadBookings();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to cancel booking.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}