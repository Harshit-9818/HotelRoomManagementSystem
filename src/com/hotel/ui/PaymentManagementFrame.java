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

import com.hotel.dao.PaymentDAO;
import com.hotel.model.Payment;

public class PaymentManagementFrame extends JFrame {

    private PaymentDAO paymentDAO;

    private JTable paymentTable;
    private DefaultTableModel tableModel;

    private DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public PaymentManagementFrame() {

        paymentDAO = new PaymentDAO();

        setTitle("Hotel Management System - Payments");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadPayments();

        setVisible(true);
    }

    private void createUI() {

        setLayout(new BorderLayout(10, 10));

        // =========================
        // BUTTON PANEL
        // =========================

        JPanel buttonPanel =
                new JPanel(new GridLayout(1, 2, 10, 10));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JButton refreshButton =
                new JButton("Refresh");

        JButton detailsButton =
                new JButton("View Details");

        JButton backButton =
                new JButton("Back");

        buttonPanel.add(refreshButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.NORTH);

        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "Payment ID",
                "Booking ID",
                "Amount",
                "Payment Method",
                "Payment Status",
                "Payment Date"
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

        paymentTable =
                new JTable(tableModel);

        paymentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        paymentTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(paymentTable);

        add(scrollPane, BorderLayout.CENTER);

        // =========================
        // BUTTON ACTIONS
        // =========================

        refreshButton.addActionListener(
                e -> loadPayments()
        );

        detailsButton.addActionListener(
                e -> viewPaymentDetails()
        );

        backButton.addActionListener(
                e -> dispose()
        );
    }

    // =========================
    // LOAD PAYMENTS
    // =========================

    private void loadPayments() {

        tableModel.setRowCount(0);

        List<Payment> payments =
                paymentDAO.getAllPayments();

        for (Payment payment : payments) {

            String paymentDate = "";

            if (payment.getPaymentDate() != null) {

                paymentDate =
                        payment.getPaymentDate()
                                .format(dateFormatter);
            }

            Object[] row = {

                    payment.getPaymentId(),

                    payment.getBookingId(),

                    "₹" + payment.getAmount(),

                    payment.getPaymentMethod(),

                    payment.getPaymentStatus(),

                    paymentDate
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // VIEW PAYMENT DETAILS
    // =========================

    private void viewPaymentDetails() {

        int selectedRow =
                paymentTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment first.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                paymentTable.convertRowIndexToModel(
                        selectedRow
                );

        int paymentId =
                (int) tableModel.getValueAt(
                        modelRow,
                        0
                );

        int bookingId =
                (int) tableModel.getValueAt(
                        modelRow,
                        1
                );

        String amount =
                tableModel.getValueAt(
                        modelRow,
                        2
                ).toString();

        String method =
                tableModel.getValueAt(
                        modelRow,
                        3
                ).toString();

        String status =
                tableModel.getValueAt(
                        modelRow,
                        4
                ).toString();

        String date =
                tableModel.getValueAt(
                        modelRow,
                        5
                ).toString();

        String details =
                "Payment ID: "
                + paymentId
                + "\n\n"

                + "Booking ID: "
                + bookingId
                + "\n\n"

                + "Amount: "
                + amount
                + "\n\n"

                + "Payment Method: "
                + method
                + "\n\n"

                + "Payment Status: "
                + status
                + "\n\n"

                + "Payment Date: "
                + date;

        JOptionPane.showMessageDialog(
                this,
                details,
                "Payment Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}