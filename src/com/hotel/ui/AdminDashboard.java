package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hotel.model.User;

public class AdminDashboard extends JFrame {

    private User loggedInUser;

    public AdminDashboard(User user) {

        this.loggedInUser = user;

        setTitle("Hotel Management System - Admin");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 10, 20
                )
        );

        JLabel titleLabel = new JLabel(
                "HOTEL MANAGEMENT SYSTEM",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                titleLabel.getFont().deriveFont(24f)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + loggedInUser.getName(),
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                welcomeLabel.getFont().deriveFont(16f)
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

        // Buttons
        JPanel buttonPanel = new JPanel(
                new GridLayout(3, 2, 20, 20)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 50, 30, 50
                )
        );

        JButton roomsButton =
                new JButton("Manage Rooms");

        JButton guestsButton =
                new JButton("View Guests");

        JButton bookingsButton =
                new JButton("View Bookings");

        JButton paymentsButton =
                new JButton("View Payments");

        JButton reportsButton =
                new JButton("View Reports");

        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(roomsButton);
        buttonPanel.add(guestsButton);
        buttonPanel.add(bookingsButton);
        buttonPanel.add(paymentsButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(logoutButton);

        add(
                buttonPanel,
                BorderLayout.CENTER
        );

        // Button actions
        roomsButton.addActionListener(
                e -> openRoomManagement()
        );

        guestsButton.addActionListener(
                e -> openGuestManagement()
        );

        bookingsButton.addActionListener(
                e -> openBookingManagement()
        );

        paymentsButton.addActionListener(
                e -> openPaymentManagement()
        );

        reportsButton.addActionListener(
                e -> openReports()
        );

        logoutButton.addActionListener(
                e -> logout()
        );
    }

    private void openRoomManagement() {

        new RoomManagementFrame();
    }

    private void openGuestManagement() {

        new GuestManagementFrame();
    }

    private void openBookingManagement() {

    	new BookingManagementFrame();
    }

    private void openPaymentManagement() {

    	new PaymentManagementFrame();
    }

    private void openReports() {
    	new ReportFrame();
    }

    private void logout() {

        dispose();

        new LoginFrame().setVisible(true);
    }
}