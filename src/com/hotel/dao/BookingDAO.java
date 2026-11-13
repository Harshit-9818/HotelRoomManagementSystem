package com.hotel.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.hotel.model.Booking;
import com.hotel.util.DBConnection;
import java.time.LocalDate;
public class BookingDAO {
	public boolean addBooking(Booking booking) {

	    String sql = "INSERT INTO bookings " +
	                 "(guest_id, room_id, check_in, check_out, " +
	                 "total_amount, status) " +
	                 "VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement =
	                 connection.prepareStatement(
	                         sql,
	                         java.sql.Statement.RETURN_GENERATED_KEYS)) {

	        statement.setInt(1, booking.getGuestId());

	        statement.setInt(2, booking.getRoomId());

	        statement.setDate(
	                3,
	                java.sql.Date.valueOf(
	                        booking.getCheckIn()));

	        statement.setDate(
	                4,
	                java.sql.Date.valueOf(
	                        booking.getCheckOut()));

	        statement.setDouble(
	                5,
	                booking.getTotalAmount());

	        statement.setString(
	                6,
	                booking.getStatus());

	        int rowsInserted =
	                statement.executeUpdate();

	        if (rowsInserted > 0) {

	            try (ResultSet resultSet =
	                         statement.getGeneratedKeys()) {

	                if (resultSet.next()) {

	                    booking.setBookingId(
	                            resultSet.getInt(1));
	                }
	            }

	            return true;
	        }

	    } catch (SQLException e) {

	        e.printStackTrace();
	    }

	    return false;
	}
	 public Booking getBookingById(int bookingId) {

		    String sql = "SELECT * FROM bookings WHERE booking_id = ?";

		    try (Connection connection = DBConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {

		        statement.setInt(1, bookingId);

		        try (var resultSet = statement.executeQuery()) {

		            if (resultSet.next()) {

		                Booking booking = new Booking();

		                booking.setBookingId(
		                        resultSet.getInt("booking_id"));

		                booking.setGuestId(
		                        resultSet.getInt("guest_id"));

		                booking.setRoomId(
		                        resultSet.getInt("room_id"));

		                booking.setCheckIn(
		                        resultSet.getDate("check_in").toLocalDate());

		                booking.setCheckOut(
		                        resultSet.getDate("check_out").toLocalDate());

		                booking.setTotalAmount(
		                        resultSet.getDouble("total_amount"));

		                booking.setStatus(
		                        resultSet.getString("status"));

		                return booking;
		            }

		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return null;
		}
	 
	 public List<Booking> getAllBookings() {

		    List<Booking> bookings = new ArrayList<>();

		    String sql = "SELECT * FROM bookings";

		    try (Connection connection = DBConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql);
		         ResultSet resultSet = statement.executeQuery()) {

		        while (resultSet.next()) {

		            Booking booking = new Booking();

		            booking.setBookingId(
		                    resultSet.getInt("booking_id"));

		            booking.setGuestId(
		                    resultSet.getInt("guest_id"));

		            booking.setRoomId(
		                    resultSet.getInt("room_id"));

		            booking.setCheckIn(
		                    resultSet.getDate("check_in").toLocalDate());

		            booking.setCheckOut(
		                    resultSet.getDate("check_out").toLocalDate());

		            booking.setTotalAmount(
		                    resultSet.getDouble("total_amount"));

		            booking.setStatus(
		                    resultSet.getString("status"));

		            bookings.add(booking);
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return bookings;
		}
	 public List<Booking> getBookingsByGuest(int guestId) {

		    List<Booking> bookings = new ArrayList<>();

		    String sql = "SELECT * FROM bookings WHERE guest_id = ?";

		    try (Connection connection = DBConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {

		        statement.setInt(1, guestId);

		        try (ResultSet resultSet = statement.executeQuery()) {

		            while (resultSet.next()) {

		                Booking booking = new Booking();

		                booking.setBookingId(
		                        resultSet.getInt("booking_id"));

		                booking.setGuestId(
		                        resultSet.getInt("guest_id"));

		                booking.setRoomId(
		                        resultSet.getInt("room_id"));

		                booking.setCheckIn(
		                        resultSet.getDate("check_in").toLocalDate());

		                booking.setCheckOut(
		                        resultSet.getDate("check_out").toLocalDate());

		                booking.setTotalAmount(
		                        resultSet.getDouble("total_amount"));

		                booking.setStatus(
		                        resultSet.getString("status"));

		                bookings.add(booking);
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return bookings;
		}
	 public boolean updateBookingStatus(int bookingId, String status) {

		    String sql = "UPDATE bookings SET status = ? " +
		                 "WHERE booking_id = ?";

		    try (Connection connection = DBConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {

		        statement.setString(1, status);
		        statement.setInt(2, bookingId);

		        int rowsUpdated = statement.executeUpdate();

		        return rowsUpdated > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 public boolean cancelBooking(int bookingId) {

		    String sql = "UPDATE bookings SET status = ? " +
		                 "WHERE booking_id = ?";

		    try (Connection connection = DBConnection.getConnection();
		         PreparedStatement statement = connection.prepareStatement(sql)) {

		        statement.setString(1, "CANCELLED");
		        statement.setInt(2, bookingId);

		        int rowsUpdated = statement.executeUpdate();

		        return rowsUpdated > 0;

		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 
	 public boolean isRoomAvailable(int roomId,
             LocalDate checkIn,
             LocalDate checkOut) {

		 String sql = "SELECT COUNT(*) FROM bookings " +
				 "WHERE room_id = ? " +
				 "AND status IN ('CONFIRMED', 'CHECKED_IN') " +
				 "AND check_in < ? " +
				 "AND check_out > ?";

		 try (Connection connection = DBConnection.getConnection();
				 PreparedStatement statement = connection.prepareStatement(sql)) {

			 statement.setInt(1, roomId);
			 statement.setDate(2, java.sql.Date.valueOf(checkOut));
			 statement.setDate(3, java.sql.Date.valueOf(checkIn));

			 try (ResultSet resultSet = statement.executeQuery()) {

				 if (resultSet.next()) {
					 int count = resultSet.getInt(1);

					 return count == 0;
				 }
			 }

		 } catch (SQLException e) {
			 e.printStackTrace();
		 }

		 return false;
	 }
}
