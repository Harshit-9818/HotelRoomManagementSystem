package com.hotel.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hotel.dao.BillDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.model.Bill;
import com.hotel.model.Payment;
import com.hotel.service.PaymentService;

public class MakePaymentFrame extends JFrame {

    private BillDAO billDAO;
    private PaymentDAO paymentDAO;
    private PaymentService paymentService;

    private JTextField bookingIdField;
    private JTextField amountField;

    private JComboBox<String> paymentMethodBox;

    private JLabel billAmountLabel;
    private JLabel paidAmountLabel;
    private JLabel remainingAmountLabel;

    public MakePaymentFrame() {

        billDAO = new BillDAO();
        paymentDAO = new PaymentDAO();
        paymentService = new PaymentService();

        setTitle(
                "Hotel Management System - Payment"
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
                        "MAKE PAYMENT",
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

        amountField =
                new JTextField();

        paymentMethodBox =
                new JComboBox<>(
                        new String[] {
                                "CASH",
                                "CARD",
                                "UPI"
                        }
                );

        billAmountLabel =
                new JLabel("-");

        paidAmountLabel =
                new JLabel("-");

        remainingAmountLabel =
                new JLabel("-");

        formPanel.add(
                new JLabel("Booking ID:")
        );

        formPanel.add(
                bookingIdField
        );

        formPanel.add(
                new JLabel("Bill Amount:")
        );

        formPanel.add(
                billAmountLabel
        );

        formPanel.add(
                new JLabel("Already Paid:")
        );

        formPanel.add(
                paidAmountLabel
        );

        formPanel.add(
                new JLabel("Remaining Amount:")
        );

        formPanel.add(
                remainingAmountLabel
        );

        formPanel.add(
                new JLabel("Payment Amount:")
        );

        formPanel.add(
                amountField
        );

        formPanel.add(
                new JLabel("Payment Method:")
        );

        formPanel.add(
                paymentMethodBox
        );

        formPanel.add(
                new JLabel("")
        );

        formPanel.add(
                new JLabel("")
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
                        "Find Bill"
                );

        JButton payButton =
                new JButton(
                        "Make Payment"
                );

        JButton backButton =
                new JButton(
                        "Back"
                );

        buttonPanel.add(
                findButton
        );

        buttonPanel.add(
                payButton
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
                e -> findBill()
        );

        payButton.addActionListener(
                e -> makePayment()
        );

        backButton.addActionListener(
                e -> dispose()
        );

        bookingIdField.addActionListener(
                e -> findBill()
        );
    }

    // =========================
    // FIND BILL
    // =========================

    private void findBill() {

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

            Bill bill =
                    billDAO.getBillByBookingId(
                            bookingId
                    );

            if (bill == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill not found.\n"
                                + "Generate the bill first.",
                        "Bill Not Found",
                        JOptionPane.WARNING_MESSAGE
                );

                clearDetails();

                return;
            }

            double totalPaid =
                    getTotalPaid(bookingId);

            double remaining =
                    bill.getTotalAmount()
                            - totalPaid;

            if (remaining < 0) {
                remaining = 0;
            }

            billAmountLabel.setText(
                    "₹"
                            + String.format(
                                    "%.2f",
                                    bill.getTotalAmount()
                            )
            );

            paidAmountLabel.setText(
                    "₹"
                            + String.format(
                                    "%.2f",
                                    totalPaid
                            )
            );

            remainingAmountLabel.setText(
                    "₹"
                            + String.format(
                                    "%.2f",
                                    remaining
                            )
            );

            amountField.setText(
                    String.format(
                            "%.2f",
                            remaining
                    )
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
    // MAKE PAYMENT
    // =========================

    private void makePayment() {

        try {

            int bookingId =
                    Integer.parseInt(
                            bookingIdField
                                    .getText()
                                    .trim()
                    );

            double amount =
                    Double.parseDouble(
                            amountField
                                    .getText()
                                    .trim()
                    );

            String paymentMethod =
                    paymentMethodBox
                            .getSelectedItem()
                            .toString();

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment amount must be "
                                + "greater than zero.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            Bill bill =
                    billDAO.getBillByBookingId(
                            bookingId
                    );

            if (bill == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Bill not found.\n"
                                + "Generate the bill first.",
                        "Bill Not Found",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            double totalPaid =
                    getTotalPaid(bookingId);

            double remaining =
                    bill.getTotalAmount()
                            - totalPaid;

            if (remaining <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "This bill has already been "
                                + "fully paid.",
                        "Already Paid",
                        JOptionPane.INFORMATION_MESSAGE
                );

                return;
            }

            if (amount > remaining) {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment exceeds the "
                                + "remaining amount.\n\n"
                                + "Remaining Amount: ₹"
                                + String.format(
                                        "%.2f",
                                        remaining
                                ),
                        "Invalid Payment",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            int confirmation =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Confirm payment?\n\n"
                                    + "Booking ID: "
                                    + bookingId
                                    + "\n"
                                    + "Amount: ₹"
                                    + String.format(
                                            "%.2f",
                                            amount
                                    )
                                    + "\n"
                                    + "Method: "
                                    + paymentMethod
                                    + "\n\n"
                                    + "Remaining after "
                                    + "payment: ₹"
                                    + String.format(
                                            "%.2f",
                                            remaining - amount
                                    ),
                            "Confirm Payment",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

            if (confirmation !=
                    JOptionPane.YES_OPTION) {

                return;
            }

            boolean success =
                    paymentService.makePayment(
                            bookingId,
                            amount,
                            paymentMethod
                    );

            if (success) {

                double newTotalPaid =
                        totalPaid + amount;

                double newRemaining =
                        bill.getTotalAmount()
                                - newTotalPaid;

                JOptionPane.showMessageDialog(
                        this,
                        "Payment successful!\n\n"
                                + "Booking ID: "
                                + bookingId
                                + "\nAmount Paid: ₹"
                                + String.format(
                                        "%.2f",
                                        amount
                                )
                                + "\nPayment Method: "
                                + paymentMethod
                                + "\nTotal Paid: ₹"
                                + String.format(
                                        "%.2f",
                                        newTotalPaid
                                )
                                + "\nRemaining: ₹"
                                + String.format(
                                        "%.2f",
                                        newRemaining
                                ),
                        "Payment Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                findBill();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Payment failed.\n"
                                + "Please check the details.",
                        "Payment Failed",
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
                    "Payment Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // GET TOTAL PAID
    // =========================

    private double getTotalPaid(int bookingId) {

        List<Payment> payments =
                paymentDAO.getPaymentsByBooking(
                        bookingId
                );

        double totalPaid = 0;

        for (Payment payment : payments) {

            if ("PAID".equalsIgnoreCase(
                    payment.getPaymentStatus())) {

                totalPaid += payment.getAmount();
            }
        }

        return totalPaid;
    }

    // =========================
    // CLEAR
    // =========================

    private void clearDetails() {

        billAmountLabel.setText("-");
        paidAmountLabel.setText("-");
        remainingAmountLabel.setText("-");
        amountField.setText("");
    }
}