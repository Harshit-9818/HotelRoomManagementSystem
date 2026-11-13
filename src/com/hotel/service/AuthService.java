package com.hotel.service;
import com.hotel.dao.UserDAO;
import com.hotel.model.User;
import com.hotel.util.PasswordUtil;
public class AuthService {
	private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }
    public User login(String username, String password) {

        // Get user from database
        User user = userDAO.getUserByUsername(username);

        // User doesn't exist
        if (user == null) {
            return null;
        }

        // Check entered password against stored hash
        boolean passwordCorrect =
                PasswordUtil.checkPassword(
                        password,
                        user.getPassword()
                );

        if (passwordCorrect) {
            return user;
        }

        return null;
    }
}
