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

import com.hotel.model.Bill;
import com.hotel.model.Booking;
import com.hotel.service.BillingService;
import com.hotel.dao.BookingDAO;

public class GenerateBillFrame extends JFrame {

    private BookingDAO bookingDAO;
    private BillingService billingService;

    private JTextField bookingIdField;
    private JTextField serviceChargesField;
    private JTextField taxField;
    private JTextField discountField;

    private JLabel roomChargesLabel;
    private JLabel totalLabel;
    private JLabel statusLabel;

    public GenerateBillFrame() {

        bookingDAO = new BookingDAO();
        billingService = new BillingService();

        setTitle(
                "Hotel Management System - Generate Bill"
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
                        "GENERATE BILL",
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
                                7,
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

        serviceChargesField =
                new JTextField("0");

        taxField =
                new JTextField("0");

        discountField =
                new JTextField("0");

        roomChargesLabel =
                new JLabel("-");

        totalLabel =
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
                new JLabel("Room Charges:")
        );

        formPanel.add(
                roomChargesLabel
        );

        formPanel.add(
                new JLabel("Service Charges:")
        );

        formPanel.add(
                serviceChargesField
        );

        formPanel.add(
                new JLabel("Tax:")
        );

        formPanel.add(
                taxField
        );

        formPanel.add(
                new JLabel("Discount:")
        );

        formPanel.add(
                discountField
        );

        formPanel.add(
                new JLabel("Total Amount:")
        );

        formPanel.add(
                totalLabel
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

        JButton calculateButton =
                new JButton(
                        "Calculate"
                );

        JButton generateButton =
                new JButton(
                        "Generate Bill"
                );

        buttonPanel.add(
                findButton
        );

        buttonPanel.add(
                calculateButton
        );

        buttonPanel.add(
                generateButton
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

        calculateButton.addActionListener(
                e -> calculateTotal()
        );

        generateButton.addActionListener(
                e -> generateBill()
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

            roomChargesLabel.setText(
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
    // CALCULATE TOTAL
    // =========================

    private void calculateTotal() {

        try {

            double roomCharges =
                    getRoomCharges();

            double serviceCharges =
                    Double.parseDouble(
                            serviceChargesField
                                    .getText()
                                    .trim()
                    );

            double tax =
                    Double.parseDouble(
                            taxField
                                    .getText()
                                    .trim()
                    );

            double discount =
                    Double.parseDouble(
                            discountField
                                    .getText()
                                    .trim()
                    );

            if (serviceCharges < 0
                    || tax < 0
                    || discount < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Charges cannot be negative.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            double total =
                    roomCharges
                            + serviceCharges
                            + tax
                            - discount;

            if (total < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Discount cannot make the "
                                + "total negative.",
                        "Invalid Discount",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            totalLabel.setText(
                    "₹"
                            + String.format(
                                    "%.2f",
                                    total
                            )
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric "
                            + "amounts.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // GENERATE BILL
    // =========================

    private void generateBill() {

        try {

            int bookingId =
                    Integer.parseInt(
                            bookingIdField
                                    .getText()
                                    .trim()
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

            if (!"CHECKED_OUT".equalsIgnoreCase(
                    booking.getStatus())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill can only be generated "
                                + "after check-out.\n\n"
                                + "Current status: "
                                + booking.getStatus(),
                        "Cannot Generate Bill",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            double roomCharges =
                    getRoomCharges();

            double serviceCharges =
                    Double.parseDouble(
                            serviceChargesField
                                    .getText()
                                    .trim()
                    );

            double tax =
                    Double.parseDouble(
                            taxField
                                    .getText()
                                    .trim()
                    );

            double discount =
                    Double.parseDouble(
                            discountField
                                    .getText()
                                    .trim()
                    );

            if (serviceCharges < 0
                    || tax < 0
                    || discount < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Charges cannot be negative.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            double total =
                    roomCharges
                            + serviceCharges
                            + tax
                            - discount;

            if (total < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Discount cannot make the "
                                + "total negative.",
                        "Invalid Discount",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Generate bill?\n\n"
                                    + "Booking ID: "
                                    + bookingId
                                    + "\nRoom Charges: ₹"
                                    + String.format(
                                            "%.2f",
                                            roomCharges
                                    )
                                    + "\nService Charges: ₹"
                                    + String.format(
                                            "%.2f",
                                            serviceCharges
                                    )
                                    + "\nTax: ₹"
                                    + String.format(
                                            "%.2f",
                                            tax
                                    )
                                    + "\nDiscount: ₹"
                                    + String.format(
                                            "%.2f",
                                            discount
                                    )
                                    + "\n\nTotal: ₹"
                                    + String.format(
                                            "%.2f",
                                            total
                                    ),
                            "Confirm Bill",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            Bill bill =
                    new Bill();

            bill.setBookingId(bookingId);
            bill.setRoomCharges(roomCharges);
            bill.setServiceCharges(serviceCharges);
            bill.setTax(tax);
            bill.setDiscount(discount);
            bill.setTotalAmount(total);

            boolean success =
                    billingService.generateBill(
                    		bookingId,
                            serviceCharges,
                            tax,
                            discount
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill generated successfully!\n\n"
                                + "Booking ID: "
                                + bookingId
                                + "\nTotal Amount: ₹"
                                + String.format(
                                        "%.2f",
                                        total
                                ),
                        "Bill Generated",
                        JOptionPane.INFORMATION_MESSAGE
                );

                totalLabel.setText(
                        "₹"
                                + String.format(
                                        "%.2f",
                                        total
                                )
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to generate bill.",
                        "Billing Failed",
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

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Billing Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // GET ROOM CHARGES
    // =========================

    private double getRoomCharges() {

        String text =
                roomChargesLabel
                        .getText()
                        .replace("₹", "")
                        .trim();

        if (text.isEmpty()
                || text.equals("-")) {

            throw new NumberFormatException(
                    "Please find the booking first."
            );
        }

        return Double.parseDouble(text);
    }

    // =========================
    // CLEAR
    // =========================

    private void clearDetails() {

        roomChargesLabel.setText("-");
        totalLabel.setText("-");
        statusLabel.setText("-");
    }
}