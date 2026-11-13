package com.hotel.service;

import com.hotel.dao.BillDAO;
import com.hotel.dao.BookingDAO;
import com.hotel.model.Bill;
import com.hotel.model.Booking;

public class BillingService {

    private BillDAO billDAO;
    private BookingDAO bookingDAO;

    public BillingService() {

        billDAO = new BillDAO();
        bookingDAO = new BookingDAO();
    }

    public boolean generateBill(int bookingId,
                                double serviceCharges,
                                double tax,
                                double discount) {

        // Get booking
        Booking booking =
                bookingDAO.getBookingById(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return false;
        }

        // Bill should be generated after checkout
        if (!booking.getStatus().equals("CHECKED_OUT")) {

            System.out.println(
                    "Bill can only be generated after check-out.");

            return false;
        }

        // Check if bill already exists
        Bill existingBill =
                billDAO.getBillByBookingId(bookingId);

        if (existingBill != null) {

            System.out.println(
                    "Bill has already been generated for this booking.");

            return false;
        }

        // Validate charges
        if (serviceCharges < 0 ||
            tax < 0 ||
            discount < 0) {

            System.out.println(
                    "Charges, tax and discount cannot be negative.");

            return false;
        }

        // Room charges come from booking
        double roomCharges =
                booking.getTotalAmount();

        // Calculate final amount
        double totalAmount =
                roomCharges
                + serviceCharges
                + tax
                - discount;

        if (totalAmount < 0) {

            System.out.println(
                    "Final bill amount cannot be negative.");

            return false;
        }

        // Create Bill object
        Bill bill = new Bill(
                bookingId,
                roomCharges,
                serviceCharges,
                tax,
                discount,
                totalAmount
        );

        // Save bill
        boolean saved =
                billDAO.addBill(bill);

        if (saved) {

            System.out.println();
            System.out.println("================================");
            System.out.println("        BILL GENERATED");
            System.out.println("================================");

            System.out.println(
                    "Bill ID: " + bill.getBillId());

            System.out.println(
                    "Booking ID: " + bookingId);

            System.out.println(
                    "Room Charges: ₹" + roomCharges);

            System.out.println(
                    "Service Charges: ₹" + serviceCharges);

            System.out.println(
                    "Tax: ₹" + tax);

            System.out.println(
                    "Discount: ₹" + discount);

            System.out.println("--------------------------------");

            System.out.println(
                    "TOTAL AMOUNT: ₹" + totalAmount);

            System.out.println("================================");

            return true;
        }

        System.out.println(
                "Failed to generate bill.");

        return false;
    }

    public Bill getBill(int bookingId) {

        return billDAO.getBillByBookingId(bookingId);
    }
}