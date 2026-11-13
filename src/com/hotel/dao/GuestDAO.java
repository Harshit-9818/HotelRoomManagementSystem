package com.hotel.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hotel.model.Guest;
import com.hotel.util.DBConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
	public boolean addGuest(Guest guest) {

	    String sql = "INSERT INTO guests " +
	                 "(name, phone, email, id_type, id_number) " +
	                 "VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement =
	                 connection.prepareStatement(
	                         sql,
	                         java.sql.Statement.RETURN_GENERATED_KEYS)) {

	        statement.setString(1, guest.getName());
	        statement.setString(2, guest.getPhone());
	        statement.setString(3, guest.getEmail());
	        statement.setString(4, guest.getIdType());
	        statement.setString(5, guest.getIdNumber());

	        int rowsInserted = statement.executeUpdate();

	        if (rowsInserted > 0) {

	            try (ResultSet resultSet =
	                         statement.getGeneratedKeys()) {

	                if (resultSet.next()) {

	                    guest.setGuestId(
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
	
	public Guest getGuestById(int guestId) {

        String sql = "SELECT * FROM guests WHERE guest_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, guestId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Guest guest = new Guest();

                    guest.setGuestId(resultSet.getInt("guest_id"));
                    guest.setName(resultSet.getString("name"));
                    guest.setPhone(resultSet.getString("phone"));
                    guest.setEmail(resultSet.getString("email"));
                    guest.setIdType(resultSet.getString("id_type"));
                    guest.setIdNumber(resultSet.getString("id_number"));

                    return guest;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
	
	public List<Guest> getAllGuests() {

        List<Guest> guests = new ArrayList<>();

        String sql = "SELECT * FROM guests";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Guest guest = new Guest();

                guest.setGuestId(resultSet.getInt("guest_id"));
                guest.setName(resultSet.getString("name"));
                guest.setPhone(resultSet.getString("phone"));
                guest.setEmail(resultSet.getString("email"));
                guest.setIdType(resultSet.getString("id_type"));
                guest.setIdNumber(resultSet.getString("id_number"));

                guests.add(guest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guests;
    }
	
	public boolean updateGuest(Guest guest) {

        String sql = "UPDATE guests SET " +
                     "name = ?, " +
                     "phone = ?, " +
                     "email = ?, " +
                     "id_type = ?, " +
                     "id_number = ? " +
                     "WHERE guest_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, guest.getName());
            statement.setString(2, guest.getPhone());
            statement.setString(3, guest.getEmail());
            statement.setString(4, guest.getIdType());
            statement.setString(5, guest.getIdNumber());
            statement.setInt(6, guest.getGuestId());

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
}
