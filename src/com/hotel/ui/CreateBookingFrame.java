package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;
import com.hotel.service.BookingService;

public class CreateBookingFrame extends JFrame {

    private JTextField guestIdField;
    private JTextField roomIdField;
    private JTextField checkInField;
    private JTextField checkOutField;

    private JLabel roomInfoLabel;
    private JLabel totalLabel;

    private BookingService bookingService;

    private DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public CreateBookingFrame() {

        bookingService = new BookingService();

        setTitle(
                "Hotel Management System - Create Booking"
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

        setLayout(new BorderLayout(10, 10));

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "CREATE NEW BOOKING",
                        JLabel.CENTER
                );

        titleLabel.setFont(
                titleLabel.getFont().deriveFont(20f)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        10,
                        10
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

        guestIdField =
                new JTextField();

        roomIdField =
                new JTextField();

        checkInField =
                new JTextField();

        checkOutField =
                new JTextField();

        roomInfoLabel =
                new JLabel(" ");

        totalLabel =
                new JLabel(" ");

        formPanel.add(
                new JLabel("Guest ID:")
        );

        formPanel.add(
                guestIdField
        );

        formPanel.add(
                new JLabel("Room ID:")
        );

        formPanel.add(
                roomIdField
        );

        formPanel.add(
                new JLabel(
                        "Check-in (dd-MM-yyyy):"
                )
        );

        formPanel.add(
                checkInField
        );

        formPanel.add(
                new JLabel(
                        "Check-out (dd-MM-yyyy):"
                )
        );

        formPanel.add(
                checkOutField
        );

        formPanel.add(
                new JLabel("Room Information:")
        );

        formPanel.add(
                roomInfoLabel
        );

        formPanel.add(
                new JLabel("Estimated Total:")
        );

        formPanel.add(
                totalLabel
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

        JButton calculateButton =
                new JButton(
                        "Calculate Total"
                );

        JButton createButton =
                new JButton(
                        "Create Booking"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );

        buttonPanel.add(
                calculateButton
        );

        buttonPanel.add(
                createButton
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

        calculateButton.addActionListener(
                e -> calculateTotal()
        );

        createButton.addActionListener(
                e -> createBooking()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // CALCULATE TOTAL
    // =========================

    private void calculateTotal() {

        try {

            int guestId =
                    Integer.parseInt(
                            guestIdField
                                    .getText()
                                    .trim()
                    );

            int roomId =
                    Integer.parseInt(
                            roomIdField
                                    .getText()
                                    .trim()
                    );

            LocalDate checkIn =
                    LocalDate.parse(
                            checkInField
                                    .getText()
                                    .trim(),
                            dateFormatter
                    );

            LocalDate checkOut =
                    LocalDate.parse(
                            checkOutField
                                    .getText()
                                    .trim(),
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

            // Validate guest
            Guest guest =
                    bookingService
                            .getGuestById(guestId);

            if (guest == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Guest ID does not exist.",
                        "Invalid Guest",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // Validate room
            Room room =
                    bookingService
                            .getRoomById(roomId);

            if (room == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room ID does not exist.",
                        "Invalid Room",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if ("MAINTENANCE".equalsIgnoreCase(
                    room.getStatus())) {

                JOptionPane.showMessageDialog(
                        this,
                        "This room is under maintenance.",
                        "Room Unavailable",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean available =
                    bookingService.isRoomAvailable(
                            roomId,
                            checkIn,
                            checkOut
                    );

            if (!available) {

                JOptionPane.showMessageDialog(
                        this,
                        "Room is not available for "
                                + "the selected dates.",
                        "Room Unavailable",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            long nights =
                    java.time.temporal.ChronoUnit
                            .DAYS
                            .between(
                                    checkIn,
                                    checkOut
                            );

            double total =
                    nights *
                    room.getPricePerDay();

            roomInfoLabel.setText(
                    room.getRoomNumber()
                            + " - "
                            + room.getRoomType()
                            + " (₹"
                            + room.getPricePerDay()
                            + "/day)"
            );

            totalLabel.setText(
                    "₹" + String.format(
                            "%.2f",
                            total
                    )
                    + " for "
                    + nights
                    + " night(s)"
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest ID and Room ID must be "
                            + "valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates in "
                            + "dd-MM-yyyy format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CREATE BOOKING
    // =========================

    private void createBooking() {

        try {

            int guestId =
                    Integer.parseInt(
                            guestIdField
                                    .getText()
                                    .trim()
                    );

            int roomId =
                    Integer.parseInt(
                            roomIdField
                                    .getText()
                                    .trim()
                    );

            LocalDate checkIn =
                    LocalDate.parse(
                            checkInField
                                    .getText()
                                    .trim(),
                            dateFormatter
                    );

            LocalDate checkOut =
                    LocalDate.parse(
                            checkOutField
                                    .getText()
                                    .trim(),
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

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Create booking for:\n\n"
                                    + "Guest ID: "
                                    + guestId
                                    + "\nRoom ID: "
                                    + roomId
                                    + "\nCheck-in: "
                                    + checkIn.format(
                                            dateFormatter
                                    )
                                    + "\nCheck-out: "
                                    + checkOut.format(
                                            dateFormatter
                                    )
                                    + "\n\nConfirm?",
                            "Confirm Booking",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            Booking booking = new Booking();

            booking.setGuestId(guestId);
            booking.setRoomId(roomId);
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);

            boolean success =
                    bookingService.createBooking(booking);

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking created successfully!",
                        "Booking Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to create booking.",
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

            if (booking != null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking created successfully!\n\n"
                                + "Booking ID: "
                                + booking.getBookingId()
                                + "\n"
                                + "Total Amount: ₹"
                                + booking.getTotalAmount(),
                        "Booking Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearFields();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to create booking.",
                        "Booking Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Guest ID and Room ID must be "
                            + "valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter dates in "
                            + "dd-MM-yyyy format.",
                    "Invalid Date",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CLEAR
    // =========================

    private void clearFields() {

        guestIdField.setText("");
        roomIdField.setText("");
        checkInField.setText("");
        checkOutField.setText("");

        roomInfoLabel.setText(" ");
        totalLabel.setText(" ");

        guestIdField.requestFocus();
    }
}