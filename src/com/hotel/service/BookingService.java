package com.hotel.service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;
public class BookingService {
	private BookingDAO bookingDAO;
    private GuestDAO guestDAO;
    private RoomDAO roomDAO;

    public BookingService() {

        bookingDAO = new BookingDAO();
        guestDAO = new GuestDAO();
        roomDAO = new RoomDAO();
    }

    // Create a new booking
    public boolean createBooking(Booking booking) {

        // 1. Validate dates
        if (booking.getCheckIn() == null ||
            booking.getCheckOut() == null) {

            System.out.println("Check-in and check-out dates are required.");
            return false;
        }

        if (!booking.getCheckOut().isAfter(booking.getCheckIn())) {

            System.out.println(
                    "Check-out date must be after check-in date.");

            return false;
        }

        // 2. Check whether guest exists
        Guest guest = guestDAO.getGuestById(
                booking.getGuestId());

        if (guest == null) {

            System.out.println("Guest not found.");
            return false;
        }

        // 3. Check whether room exists
        Room room = roomDAO.getRoomById(
                booking.getRoomId());

        if (room == null) {

            System.out.println("Room not found.");
            return false;
        }

        // 4. Check room's current status
        if (room.getStatus().equals("MAINTENANCE")) {

            System.out.println(
                    "Room is currently under maintenance.");

            return false;
        }

        // 5. Check room availability for requested dates
        boolean available = bookingDAO.isRoomAvailable(
                booking.getRoomId(),
                booking.getCheckIn(),
                booking.getCheckOut());

        if (!available) {

            System.out.println(
                    "Room is not available for the selected dates.");

            return false;
        }

        // 6. Calculate number of nights
        long numberOfNights = ChronoUnit.DAYS.between(
                booking.getCheckIn(),
                booking.getCheckOut());

        // 7. Calculate room charges
        double totalAmount =
                numberOfNights * room.getPricePerDay();

        // 8. Set booking details
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");

        // 9. Save booking
        boolean bookingCreated =
                bookingDAO.addBooking(booking);

        if (bookingCreated) {

            System.out.println();
            System.out.println("Booking created successfully!");
            System.out.println("Booking ID: " +
                    booking.getBookingId());
            System.out.println("Room Number: " +
                    room.getRoomNumber());
            System.out.println("Guest: " +
                    guest.getName());
            System.out.println("Number of Nights: " +
                    numberOfNights);
            System.out.println("Total Amount: ₹" +
                    totalAmount);

            return true;
        }

        System.out.println("Failed to create booking.");

        return false;
    }

    // Get booking by ID
    public Booking getBooking(int bookingId) {

        return bookingDAO.getBookingById(bookingId);
    }
    public Guest getGuestById(int guestId) {
        return guestDAO.getGuestById(guestId);
    }

    public Room getRoomById(int roomId) {
        return roomDAO.getRoomById(roomId);
    }

    // Get all bookings
    public java.util.List<Booking> getAllBookings() {

        return bookingDAO.getAllBookings();
    }

    // Get bookings of a guest
    public java.util.List<Booking> getBookingsByGuest(
            int guestId) {

        return bookingDAO.getBookingsByGuest(guestId);
    }

    // Check whether a room is available
    public boolean isRoomAvailable(
            int roomId,
            LocalDate checkIn,
            LocalDate checkOut) {

        if (checkIn == null || checkOut == null) {
            return false;
        }

        if (!checkOut.isAfter(checkIn)) {
            return false;
        }

        Room room = roomDAO.getRoomById(roomId);

        if (room == null) {
            return false;
        }

        if (room.getStatus().equals("MAINTENANCE")) {
            return false;
        }

        return bookingDAO.isRoomAvailable(
                roomId,
                checkIn,
                checkOut);
    }

    // Check-in guest
    public boolean checkInGuest(int bookingId) {

        Booking booking =
                bookingDAO.getBookingById(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return false;
        }

        if (!booking.getStatus().equals("CONFIRMED")) {

            System.out.println(
                    "Guest cannot be checked in. " +
                    "Booking status is: " +
                    booking.getStatus());

            return false;
        }

        boolean updated =
                bookingDAO.updateBookingStatus(
                        bookingId,
                        "CHECKED_IN");

        if (updated) {

            Room room =
                    roomDAO.getRoomById(
                            booking.getRoomId());

            if (room != null) {

                room.setStatus("OCCUPIED");

                roomDAO.updateRoom(room);
            }

            System.out.println(
                    "Guest checked in successfully.");

            return true;
        }

        return false;
    }

    // Check-out guest
    public boolean checkOutGuest(int bookingId) {

        Booking booking =
                bookingDAO.getBookingById(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return false;
        }

        if (!booking.getStatus().equals("CHECKED_IN")) {

            System.out.println(
                    "Guest cannot be checked out. " +
                    "Booking status is: " +
                    booking.getStatus());

            return false;
        }

        boolean updated =
                bookingDAO.updateBookingStatus(
                        bookingId,
                        "CHECKED_OUT");

        if (updated) {

            Room room =
                    roomDAO.getRoomById(
                            booking.getRoomId());

            if (room != null) {

                room.setStatus("AVAILABLE");

                roomDAO.updateRoom(room);
            }

            System.out.println(
                    "Guest checked out successfully.");

            return true;
        }

        return false;
    }

    // Cancel booking
    public boolean cancelBooking(int bookingId) {

        Booking booking =
                bookingDAO.getBookingById(bookingId);

        if (booking == null) {

            System.out.println("Booking not found.");
            return false;
        }

        if (booking.getStatus().equals("CHECKED_OUT")) {

            System.out.println(
                    "A checked-out booking cannot be cancelled.");

            return false;
        }

        if (booking.getStatus().equals("CANCELLED")) {

            System.out.println(
                    "Booking is already cancelled.");

            return false;
        }

        boolean cancelled =
                bookingDAO.cancelBooking(bookingId);

        if (cancelled) {

            System.out.println(
                    "Booking cancelled successfully.");

            return true;
        }

        return false;
    }
}
