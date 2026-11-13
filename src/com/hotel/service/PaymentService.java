package com.hotel.service;

import java.util.List;

import com.hotel.dao.BillDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.model.Bill;
import com.hotel.model.Payment;

public class PaymentService {

    private PaymentDAO paymentDAO;
    private BillDAO billDAO;

    public PaymentService() {

        paymentDAO = new PaymentDAO();
        billDAO = new BillDAO();
    }

    public boolean makePayment(int bookingId,
                               double amount,
                               String paymentMethod) {

        // Get bill
        Bill bill = billDAO.getBillByBookingId(bookingId);

        if (bill == null) {

            System.out.println(
                    "Bill not found. Generate the bill first.");

            return false;
        }

        // Validate amount
        if (amount <= 0) {

            System.out.println(
                    "Payment amount must be greater than zero.");

            return false;
        }

        // Validate payment method
        if (!paymentMethod.equals("CASH") &&
            !paymentMethod.equals("CARD") &&
            !paymentMethod.equals("UPI")) {

            System.out.println(
                    "Invalid payment method.");

            return false;
        }

        // Get previous payments
        List<Payment> payments =
                paymentDAO.getPaymentsByBooking(bookingId);

        double totalPaid = 0;

        for (Payment payment : payments) {

            if (payment.getPaymentStatus().equals("PAID")) {

                totalPaid += payment.getAmount();
            }
        }

        double remainingAmount =
                bill.getTotalAmount() - totalPaid;

        // Already fully paid
        if (remainingAmount <= 0) {

            System.out.println(
                    "This bill has already been fully paid.");

            return false;
        }

        // Payment cannot exceed remaining amount
        if (amount > remainingAmount) {

            System.out.println(
                    "Payment exceeds the remaining bill amount.");

            System.out.println(
                    "Remaining Amount: ₹" + remainingAmount);

            return false;
        }

        // Create payment
        Payment payment = new Payment(
                bookingId,
                amount,
                paymentMethod,
                "PAID"
        );

        boolean saved =
                paymentDAO.addPayment(payment);

        if (!saved) {

            System.out.println(
                    "Failed to process payment.");

            return false;
        }

        double newTotalPaid =
                totalPaid + amount;

        double newRemaining =
                bill.getTotalAmount() - newTotalPaid;

        System.out.println();
        System.out.println("================================");
        System.out.println("        PAYMENT SUCCESSFUL");
        System.out.println("================================");

        System.out.println(
                "Payment ID: " + payment.getPaymentId());

        System.out.println(
                "Booking ID: " + bookingId);

        System.out.println(
                "Amount Paid: ₹" + amount);

        System.out.println(
                "Payment Method: " + paymentMethod);

        System.out.println(
                "Total Paid: ₹" + newTotalPaid);

        System.out.println(
                "Remaining Amount: ₹" + newRemaining);

        if (newRemaining == 0) {

            System.out.println(
                    "Payment Status: FULLY PAID");

        } else {

            System.out.println(
                    "Payment Status: PARTIALLY PAID");
        }

        System.out.println("================================");

        return true;
    }

    public List<Payment> getPaymentsByBooking(int bookingId) {

        return paymentDAO.getPaymentsByBooking(bookingId);
    }

    public List<Payment> getAllPayments() {

        return paymentDAO.getAllPayments();
    }
}