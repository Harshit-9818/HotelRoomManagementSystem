package com.hotel.service;

import java.util.List;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.PaymentDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Payment;
import com.hotel.model.Room;

public class ReportService {

    private RoomDAO roomDAO;
    private GuestDAO guestDAO;
    private BookingDAO bookingDAO;
    private PaymentDAO paymentDAO;

    public ReportService() {

        roomDAO = new RoomDAO();
        guestDAO = new GuestDAO();
        bookingDAO = new BookingDAO();
        paymentDAO = new PaymentDAO();
    }

    public void generateReport() {

        System.out.println();
        System.out.println("================================");
        System.out.println("         HOTEL REPORT");
        System.out.println("================================");

        // -----------------------------
        // ROOM REPORT
        // -----------------------------

        List<Room> rooms =
                roomDAO.getAllRooms();

        int totalRooms = rooms.size();
        int availableRooms = 0;
        int occupiedRooms = 0;
        int maintenanceRooms = 0;
        int reservedRooms = 0;

        for (Room room : rooms) {

            switch (room.getStatus()) {

                case "AVAILABLE":
                    availableRooms++;
                    break;

                case "OCCUPIED":
                    occupiedRooms++;
                    break;

                case "MAINTENANCE":
                    maintenanceRooms++;
                    break;

                case "RESERVED":
                    reservedRooms++;
                    break;
            }
        }

        // -----------------------------
        // GUEST REPORT
        // -----------------------------

        List<Guest> guests =
                guestDAO.getAllGuests();

        int totalGuests = guests.size();

        // -----------------------------
        // BOOKING REPORT
        // -----------------------------

        List<Booking> bookings =
                bookingDAO.getAllBookings();

        int totalBookings = bookings.size();

        int confirmedBookings = 0;
        int checkedInBookings = 0;
        int checkedOutBookings = 0;
        int cancelledBookings = 0;

        double totalBookingAmount = 0;

        for (Booking booking : bookings) {

            totalBookingAmount +=
                    booking.getTotalAmount();

            switch (booking.getStatus()) {

                case "CONFIRMED":
                    confirmedBookings++;
                    break;

                case "CHECKED_IN":
                    checkedInBookings++;
                    break;

                case "CHECKED_OUT":
                    checkedOutBookings++;
                    break;

                case "CANCELLED":
                    cancelledBookings++;
                    break;
            }
        }

        // -----------------------------
        // PAYMENT REPORT
        // -----------------------------

        List<Payment> payments =
                paymentDAO.getAllPayments();

        double totalPaid = 0;

        for (Payment payment : payments) {

            if (payment.getPaymentStatus()
                    .equals("PAID")) {

                totalPaid += payment.getAmount();
            }
        }

        double pendingAmount =
                totalBookingAmount - totalPaid;

        if (pendingAmount < 0) {
            pendingAmount = 0;
        }

        // -----------------------------
        // DISPLAY REPORT
        // -----------------------------

        System.out.println();

        System.out.println("ROOM SUMMARY");
        System.out.println("--------------------------------");

        System.out.println(
                "Total Rooms       : " + totalRooms);

        System.out.println(
                "Available Rooms   : " + availableRooms);

        System.out.println(
                "Occupied Rooms    : " + occupiedRooms);

        System.out.println(
                "Reserved Rooms    : " + reservedRooms);

        System.out.println(
                "Maintenance Rooms : " + maintenanceRooms);

        System.out.println();

        System.out.println("GUEST SUMMARY");
        System.out.println("--------------------------------");

        System.out.println(
                "Total Guests      : " + totalGuests);

        System.out.println();

        System.out.println("BOOKING SUMMARY");
        System.out.println("--------------------------------");

        System.out.println(
                "Total Bookings       : " + totalBookings);

        System.out.println(
                "Confirmed Bookings   : " +
                confirmedBookings);

        System.out.println(
                "Checked-in Bookings  : " +
                checkedInBookings);

        System.out.println(
                "Checked-out Bookings : " +
                checkedOutBookings);

        System.out.println(
                "Cancelled Bookings   : " +
                cancelledBookings);

        System.out.println();

        System.out.println("FINANCIAL SUMMARY");
        System.out.println("--------------------------------");

        System.out.println(
                "Total Booking Amount : ₹" +
                totalBookingAmount);

        System.out.println(
                "Total Paid           : ₹" +
                totalPaid);

        System.out.println(
                "Pending Amount       : ₹" +
                pendingAmount);

        System.out.println();

        System.out.println("================================");
        System.out.println("          END OF REPORT");
        System.out.println("================================");
    }
}