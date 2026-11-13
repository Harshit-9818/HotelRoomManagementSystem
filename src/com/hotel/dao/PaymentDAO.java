package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Payment;
import com.hotel.util.DBConnection;

public class PaymentDAO {

    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                     "(booking_id, amount, payment_method, payment_status) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, payment.getBookingId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentMethod());
            statement.setString(4, payment.getPaymentStatus());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet resultSet =
                             statement.getGeneratedKeys()) {

                    if (resultSet.next()) {

                        payment.setPaymentId(
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

    public List<Payment> getPaymentsByBooking(int bookingId) {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments " +
                     "WHERE booking_id = ? " +
                     "ORDER BY payment_date";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Payment payment = new Payment();

                    payment.setPaymentId(
                            resultSet.getInt("payment_id"));

                    payment.setBookingId(
                            resultSet.getInt("booking_id"));

                    payment.setAmount(
                            resultSet.getDouble("amount"));

                    payment.setPaymentMethod(
                            resultSet.getString("payment_method"));

                    payment.setPaymentStatus(
                            resultSet.getString("payment_status"));

                    if (resultSet.getTimestamp("payment_date") != null) {

                        payment.setPaymentDate(
                                resultSet.getTimestamp("payment_date")
                                        .toLocalDateTime());
                    }

                    payments.add(payment);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return payments;
    }

    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT * FROM payments " +
                     "ORDER BY payment_date DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Payment payment = new Payment();

                payment.setPaymentId(
                        resultSet.getInt("payment_id"));

                payment.setBookingId(
                        resultSet.getInt("booking_id"));

                payment.setAmount(
                        resultSet.getDouble("amount"));

                payment.setPaymentMethod(
                        resultSet.getString("payment_method"));

                payment.setPaymentStatus(
                        resultSet.getString("payment_status"));

                if (resultSet.getTimestamp("payment_date") != null) {

                    payment.setPaymentDate(
                            resultSet.getTimestamp("payment_date")
                                    .toLocalDateTime());
                }

                payments.add(payment);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return payments;
    }
}