package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hotel.model.User;

public class ReceptionDashboard extends JFrame {

    private User loggedInUser;

    public ReceptionDashboard(User user) {

        loggedInUser = user;

        setTitle(
                "Hotel Management System - Reception"
        );

        setSize(750, 550);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
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
        // HEADER
        // =========================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        20,
                        10,
                        20
                )
        );

        JLabel titleLabel =
                new JLabel(
                        "HOTEL MANAGEMENT SYSTEM",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                titleLabel.getFont()
                        .deriveFont(24f)
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, "
                                + loggedInUser.getName()
                                + " - Reception",
                        SwingConstants.CENTER
                );

        welcomeLabel.setFont(
                welcomeLabel.getFont()
                        .deriveFont(16f)
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        headerPanel.add(
                welcomeLabel,
                BorderLayout.SOUTH
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                20,
                                20
                        )
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );

        JButton registerGuestButton =
                new JButton(
                        "Register Guest"
                );
        JButton viewBookingsButton =
                new JButton("View Bookings");

        JButton createBookingButton =
                new JButton(
                        "Create Booking"
                );

        JButton checkInButton =
                new JButton(
                        "Check-in"
                );

        JButton checkOutButton =
                new JButton(
                        "Check-out"
                );

        JButton generateBillButton =
                new JButton(
                        "Generate Bill"
                );

        JButton paymentButton =
                new JButton(
                        "Make Payment"
                );
        JButton roomAvailabilityButton =
                new JButton(
                        "Room Availability"
                );

        JButton logoutButton =
                new JButton(
                        "Logout"
                );

        // Empty placeholder
        JLabel emptyLabel =
                new JLabel();

        buttonPanel.add(
                registerGuestButton
        );
        
        buttonPanel.add(
                createBookingButton
        );

        buttonPanel.add(
                viewBookingsButton
        );
        
        buttonPanel.add(
                checkInButton
        );

        buttonPanel.add(
                checkOutButton
        );

        buttonPanel.add(
                generateBillButton
        );

        buttonPanel.add(
                paymentButton
        );
        buttonPanel.add(
                roomAvailabilityButton
        );

        buttonPanel.add(
                logoutButton
        );

        buttonPanel.add(
                emptyLabel
        );

        add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        registerGuestButton.addActionListener(
                e -> openGuestRegistration()
        );
        
        viewBookingsButton.addActionListener(
                e -> openBookingManagement()
        );
        
        createBookingButton.addActionListener(
                e -> openCreateBooking()
        );

        checkInButton.addActionListener(
                e -> openCheckIn()
        );

        checkOutButton.addActionListener(
                e -> openCheckOut()
        );

        generateBillButton.addActionListener(
                e -> openGenerateBill()
        );

        paymentButton.addActionListener(
                e -> openPayment()
        );
        roomAvailabilityButton.addActionListener(
                e -> openRoomAvailability()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    // =========================
    // REGISTER GUEST
    // =========================

    private void openGuestRegistration() {

        new GuestManagementFrame();
    }

    // =========================
    // CREATE BOOKING
    // =========================

    private void openCreateBooking() {

        new CreateBookingFrame();
    }

    private void openBookingManagement() {

        new BookingManagementFrame();
    }
    // =========================
    // CHECK-IN
    // =========================

    private void openCheckIn() {

        new CheckInFrame();
    }

    // =========================
    // CHECK-OUT
    // =========================

    private void openCheckOut() {

        new CheckOutFrame();
    }

    // =========================
    // GENERATE BILL
    // =========================

    private void openGenerateBill() {

        new GenerateBillFrame();
    }

    // =========================
    // MAKE PAYMENT
    // =========================

    private void openPayment() {

        new MakePaymentFrame();
    }
    private void openRoomAvailability() {

        new RoomAvailabilityFrame();
    }

    // =========================
    // LOGOUT
    // =========================

    private void logout() {

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation ==
                JOptionPane.YES_OPTION) {

            dispose();

            new LoginFrame().setVisible(true);
        }
    }
}