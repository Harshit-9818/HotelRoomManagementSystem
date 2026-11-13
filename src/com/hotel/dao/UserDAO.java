package com.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.User;
import com.hotel.util.DBConnection;

public class UserDAO {

    // 1. Add a new user
    public boolean addUser(User user) {

        String sql = "INSERT INTO users " +
                     "(name, username, password, role) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 2. Get user by username
    public User getUserByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    User user = new User();

                    user.setUserId(resultSet.getInt("user_id"));
                    user.setName(resultSet.getString("name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(resultSet.getString("role"));

                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // 3. Get all users
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getString("role"));

                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}