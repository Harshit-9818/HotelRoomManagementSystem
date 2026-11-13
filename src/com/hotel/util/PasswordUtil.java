package com.hotel.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash password before storing it
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    // Check entered password against stored hash
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}