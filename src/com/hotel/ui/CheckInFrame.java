package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hotel.dao.BookingDAO;
import com.hotel.model.Booking;
import com.hotel.service.BookingService;

public class CheckInFrame extends JFrame {

    private BookingDAO bookingDAO;
    private BookingService bookingService;

    private JTextField bookingIdField;

    private JLabel guestLabel;
    private JLabel roomLabel;
    private JLabel datesLabel;
    private JLabel amountLabel;
    private JLabel statusLabel;

    private DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CheckInFrame() {

        bookingDAO = new BookingDAO();
        bookingService = new BookingService();

        setTitle(
                "Hotel Management System - Check-in"
        );

        setSize(550, 500);

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
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "GUEST CHECK-IN",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                titleLabel.getFont().deriveFont(20f)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 10, 10
                )
        );

        add(
                titleLabel,
                BorderLayout.NORTH
        );

        // =========================
        // FORM
        // =========================

        JPanel formPanel =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10
                        )
                );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        40,
                        20,
                        40
                )
        );

        bookingIdField =
                new JTextField();

        guestLabel =
                new JLabel("-");

        roomLabel =
                new JLabel("-");

        datesLabel =
                new JLabel("-");

        amountLabel =
                new JLabel("-");

        statusLabel =
                new JLabel("-");

        formPanel.add(
                new JLabel("Booking ID:")
        );

        formPanel.add(
                bookingIdField
        );

        formPanel.add(
                new JLabel("Guest ID:")
        );

        formPanel.add(
                guestLabel
        );

        formPanel.add(
                new JLabel("Room ID:")
        );

        formPanel.add(
                roomLabel
        );

        formPanel.add(
                new JLabel("Stay:")
        );

        formPanel.add(
                datesLabel
        );

        formPanel.add(
                new JLabel("Total Amount:")
        );

        formPanel.add(
                amountLabel
        );

        formPanel.add(
                new JLabel("Booking Status:")
        );

        formPanel.add(
                statusLabel
        );

        add(
                formPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
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

        JButton findButton =
                new JButton(
                        "Find Booking"
                );

        JButton checkInButton =
                new JButton(
                        "Check-in"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );

        buttonPanel.add(
                findButton
        );

        buttonPanel.add(
                checkInButton
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

        findButton.addActionListener(
                e -> findBooking()
        );

        checkInButton.addActionListener(
                e -> checkInBooking()
        );

        backButton.addActionListener(
                e -> dispose()
        );

        bookingIdField.addActionListener(
                e -> findBooking()
        );
    }

    // =========================
    // FIND BOOKING
    // =========================

    private void findBooking() {

        String text =
                bookingIdField
                        .getText()
                        .trim();

        if (text.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Booking ID.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int bookingId =
                    Integer.parseInt(text);

            Booking booking =
                    bookingDAO.getBookingById(
                            bookingId
                    );

            if (booking == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking not found.",
                        "Not Found",
                        JOptionPane.ERROR_MESSAGE
                );

                clearDetails();

                return;
            }

            guestLabel.setText(
                    String.valueOf(
                            booking.getGuestId()
                    )
            );

            roomLabel.setText(
                    String.valueOf(
                            booking.getRoomId()
                    )
            );

            String checkIn =
                    booking.getCheckIn() != null
                            ? booking.getCheckIn()
                                    .format(
                                            dateFormatter
                                    )
                            : "";

            String checkOut =
                    booking.getCheckOut() != null
                            ? booking.getCheckOut()
                                    .format(
                                            dateFormatter
                                    )
                            : "";

            datesLabel.setText(
                    checkIn
                            + "  →  "
                            + checkOut
            );

            amountLabel.setText(
                    "₹"
                            + String.format(
                                    "%.2f",
                                    booking.getTotalAmount()
                            )
            );

            statusLabel.setText(
                    booking.getStatus()
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking ID must be a number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CHECK-IN
    // =========================

    private void checkInBooking() {

        String text =
                bookingIdField
                        .getText()
                        .trim();

        if (text.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Booking ID.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            int bookingId =
                    Integer.parseInt(text);

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

            if (!"CONFIRMED".equalsIgnoreCase(
                    booking.getStatus())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Only CONFIRMED bookings "
                                + "can be checked in.\n\n"
                                + "Current status: "
                                + booking.getStatus(),
                        "Cannot Check-in",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Check-in this booking?\n\n"
                                    + "Booking ID: "
                                    + bookingId
                                    + "\nGuest ID: "
                                    + booking.getGuestId()
                                    + "\nRoom ID: "
                                    + booking.getRoomId(),
                            "Confirm Check-in",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            boolean success =
                    bookingService.checkInGuest(
                            bookingId
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Guest checked in successfully!",
                        "Check-in Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                findBooking();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Check-in failed.",
                        "Check-in Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking ID must be a number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CLEAR DETAILS
    // =========================

    private void clearDetails() {

        guestLabel.setText("-");
        roomLabel.setText("-");
        datesLabel.setText("-");
        amountLabel.setText("-");
        statusLabel.setText("-");
    }
}