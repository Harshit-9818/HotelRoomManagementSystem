package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Payment;
import com.hotel.model.Room;

public class ReportFrame extends JFrame {

    private RoomDAO roomDAO;
    private GuestDAO guestDAO;
    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;

    private JTextArea reportArea;

    public ReportFrame() {

        roomDAO = new RoomDAO();
        guestDAO = new GuestDAO();
        bookingDAO = new BookingDAO();
        paymentDAO = new PaymentDAO();

        setTitle("Hotel Management System - Reports");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();

        generateReport();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // HEADER
        // =========================

        JLabel titleLabel =
                new JLabel(
                        "HOTEL MANAGEMENT REPORT",
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 10, 10
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        // =========================
        // REPORT AREA
        // =========================

        reportArea =
                new JTextArea();

        reportArea.setEditable(false);

        reportArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14
                )
        );

        reportArea.setMargin(
                new java.awt.Insets(
                        15, 15, 15, 15
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(reportArea);

        add(
                scrollPane,
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
                        10, 20, 15, 20
                )
        );

        JButton refreshButton =
                new JButton("Refresh Report");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        refreshButton.addActionListener(
                e -> generateReport()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // GENERATE REPORT
    // =========================

    private void generateReport() {

        StringBuilder report =
                new StringBuilder();

        // =========================
        // ROOM INFORMATION
        // =========================

        List<Room> rooms =
                roomDAO.getAllRooms();

        int availableRooms = 0;
        int occupiedRooms = 0;
        int maintenanceRooms = 0;

        for (Room room : rooms) {

            String status =
                    room.getStatus();

            if ("AVAILABLE".equalsIgnoreCase(status)) {

                availableRooms++;

            } else if (
                    "OCCUPIED".equalsIgnoreCase(status)) {

                occupiedRooms++;

            } else if (
                    "MAINTENANCE".equalsIgnoreCase(status)) {

                maintenanceRooms++;
            }
        }

        // =========================
        // GUEST INFORMATION
        // =========================

        int totalGuests =
                guestDAO.getAllGuests().size();

        // =========================
        // BOOKING INFORMATION
        // =========================

        List<Booking> bookings =
                bookingDAO.getAllBookings();

        int confirmedBookings = 0;
        int checkedInBookings = 0;
        int checkedOutBookings = 0;
        int cancelledBookings = 0;

        double totalBookingAmount = 0;

        for (Booking booking : bookings) {

            String status =
                    booking.getStatus();

            if ("CONFIRMED".equalsIgnoreCase(status)) {

                confirmedBookings++;

            } else if (
                    "CHECKED_IN".equalsIgnoreCase(status)) {

                checkedInBookings++;

            } else if (
                    "CHECKED_OUT".equalsIgnoreCase(status)) {

                checkedOutBookings++;

            } else if (
                    "CANCELLED".equalsIgnoreCase(status)) {

                cancelledBookings++;
            }

            if (!"CANCELLED".equalsIgnoreCase(status)) {

                totalBookingAmount +=
                        booking.getTotalAmount();
            }
        }

        // =========================
        // PAYMENT INFORMATION
        // =========================

        List<Payment> payments =
                paymentDAO.getAllPayments();

        double totalPaid = 0;

        for (Payment payment : payments) {

            if ("PAID".equalsIgnoreCase(
                    payment.getPaymentStatus())) {

                totalPaid +=
                        payment.getAmount();
            }
        }

        double pendingAmount =
                totalBookingAmount - totalPaid;

        if (pendingAmount < 0) {
            pendingAmount = 0;
        }

        // =========================
        // BUILD REPORT
        // =========================

        report.append(
                "================================================\n"
        );

        report.append(
                "             HOTEL MANAGEMENT REPORT\n"
        );

        report.append(
                "================================================\n\n"
        );

        // ROOMS

        report.append(
                "ROOM STATISTICS\n"
        );

        report.append(
                "------------------------------------------------\n"
        );

        report.append(
                String.format(
                        "Total Rooms        : %d%n",
                        rooms.size()
                )
        );

        report.append(
                String.format(
                        "Available Rooms    : %d%n",
                        availableRooms
                )
        );

        report.append(
                String.format(
                        "Occupied Rooms     : %d%n",
                        occupiedRooms
                )
        );

        report.append(
                String.format(
                        "Maintenance Rooms  : %d%n",
                        maintenanceRooms
                )
        );

        report.append("\n");

        // GUESTS

        report.append(
                "GUEST STATISTICS\n"
        );

        report.append(
                "------------------------------------------------\n"
        );

        report.append(
                String.format(
                        "Total Guests       : %d%n",
                        totalGuests
                )
        );

        report.append("\n");

        // BOOKINGS

        report.append(
                "BOOKING STATISTICS\n"
        );

        report.append(
                "------------------------------------------------\n"
        );

        report.append(
                String.format(
                        "Total Bookings     : %d%n",
                        bookings.size()
                )
        );

        report.append(
                String.format(
                        "Confirmed          : %d%n",
                        confirmedBookings
                )
        );

        report.append(
                String.format(
                        "Checked In         : %d%n",
                        checkedInBookings
                )
        );

        report.append(
                String.format(
                        "Checked Out        : %d%n",
                        checkedOutBookings
                )
        );

        report.append(
                String.format(
                        "Cancelled          : %d%n",
                        cancelledBookings
                )
        );

        report.append("\n");

        // FINANCIAL

        report.append(
                "FINANCIAL STATISTICS\n"
        );

        report.append(
                "------------------------------------------------\n"
        );

        report.append(
                String.format(
                        "Booking Revenue    : ₹%.2f%n",
                        totalBookingAmount
                )
        );

        report.append(
                String.format(
                        "Amount Paid        : ₹%.2f%n",
                        totalPaid
                )
        );

        report.append(
                String.format(
                        "Pending Amount     : ₹%.2f%n",
                        pendingAmount
                )
        );

        report.append("\n");

        report.append(
                "================================================\n"
        );

        report.append(
                "              END OF REPORT\n"
        );

        report.append(
                "================================================\n"
        );

        reportArea.setText(
                report.toString()
        );
    }
}