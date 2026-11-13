package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hotel.model.Bill;
import com.hotel.util.DBConnection;

public class BillDAO {

    public boolean addBill(Bill bill) {

        String sql = "INSERT INTO bills " +
                     "(booking_id, room_charges, service_charges, " +
                     "tax, discount, total_amount) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, bill.getBookingId());
            statement.setDouble(2, bill.getRoomCharges());
            statement.setDouble(3, bill.getServiceCharges());
            statement.setDouble(4, bill.getTax());
            statement.setDouble(5, bill.getDiscount());
            statement.setDouble(6, bill.getTotalAmount());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet resultSet =
                             statement.getGeneratedKeys()) {

                    if (resultSet.next()) {

                        bill.setBillId(
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

    public Bill getBillByBookingId(int bookingId) {

        String sql = "SELECT * FROM bills " +
                     "WHERE booking_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {

                    Bill bill = new Bill();

                    bill.setBillId(
                            resultSet.getInt("bill_id"));

                    bill.setBookingId(
                            resultSet.getInt("booking_id"));

                    bill.setRoomCharges(
                            resultSet.getDouble("room_charges"));

                    bill.setServiceCharges(
                            resultSet.getDouble("service_charges"));

                    bill.setTax(
                            resultSet.getDouble("tax"));

                    bill.setDiscount(
                            resultSet.getDouble("discount"));

                    bill.setTotalAmount(
                            resultSet.getDouble("total_amount"));

                    if (resultSet.getTimestamp("generated_at") != null) {

                        bill.setGeneratedAt(
                                resultSet.getTimestamp("generated_at")
                                        .toLocalDateTime());
                    }

                    return bill;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }
}