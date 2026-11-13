package com.hotel;

import javax.swing.SwingUtilities;

import com.hotel.ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);
        });
    }
}
//package com.hotel;
//import java.sql.Connection;
//import java.util.List;
//import com.hotel.dao.RoomDAO;
//import com.hotel.model.Room;
//import com.hotel.model.User;
//import com.hotel.util.DBConnection;
//import com.hotel.dao.GuestDAO;
//import com.hotel.model.Guest;
//import com.hotel.util.PasswordUtil;
//import com.hotel.service.AuthService;
//import com.hotel.ui.LoginMenu;
//import com.hotel.ui.AdminMenu;
//import com.hotel.ui.ReceptionMenu;
//import com.hotel.ui.RegistrationMenu;
//import java.util.Scanner;
//public class Main {
//	public static void main(String[] args) {
//		LoginMenu loginMenu = new LoginMenu();
////		==================================================
////	 					  REGISTRATION
////		==================================================
////		Scanner scanner = new Scanner(System.in);
////		RegistrationMenu registrationMenu = new RegistrationMenu();
////		while (true) {
////
////            System.out.println();
////            System.out.println("================================");
////            System.out.println("      HOTEL MANAGEMENT SYSTEM");
////            System.out.println("================================");
////
////            System.out.println("1. Login");
////            System.out.println("2. Register");
////            System.out.println("3. Exit");
////
////            System.out.print("Enter your choice: ");
////
////            String choice = scanner.nextLine();
////
////            switch (choice) {
////
////                case "1":
////
////                    User user =
////                            loginMenu.showLogin();
////
////                    if (user != null) {
////
////                        if (user.getRole()
////                                .equals("ADMIN")) {
////
////                            AdminMenu adminMenu =
////                                    new AdminMenu(user);
////
////                            adminMenu.showMenu();
////
////                        } else if (user.getRole()
////                                .equals("RECEPTIONIST")) {
////
////                            ReceptionMenu receptionMenu =
////                                    new ReceptionMenu(user);
////
////                            receptionMenu.showMenu();
////
////                        } else {
////
////                            System.out.println(
////                                    "Unknown user role!");
////                        }
////                    }
////
////                    break;
////
////                case "2":
////
////                    registrationMenu.showRegistration();
////
////                    break;
////
////                case "3":
////
////                    System.out.println();
////                    System.out.println(
////                            "Thank you for using " +
////                            "Hotel Management System!");
////
////                    scanner.close();
////
////                    return;
////
////                default:
////
////                    System.out.println(
////                            "Invalid choice! " +
////                            "Please choose 1, 2 or 3.");
////            }
////        }
////		===============================
////			  REGISTRATION ENDS
////      ===============================
//        User user = loginMenu.showLogin();
//
//        if (user != null) {
//        	if (user.getRole().equals("ADMIN")) {
//
//                AdminMenu adminMenu = new AdminMenu(user);
//                adminMenu.showMenu();
//
//            } else if (user.getRole().equals("RECEPTIONIST")) {
//            	ReceptionMenu receptionMenu = new ReceptionMenu(user);
//                receptionMenu.showMenu();
//
//            } else {
//
//                System.out.println("Unknown user role!");
//
//            }
//
////            System.out.println();
////            System.out.println("Opening " + user.getRole() + " dashboard...");
//
//        }
////		 AuthService authService = new AuthService();
//
//	        // Correct login
////	        User user = authService.login("adm", "admin123");
////
////	        if (user != null) {
////
////	            System.out.println("Login Successful!");
////	            System.out.println("Welcome, " + user.getName());
////	            System.out.println("Role: " + user.getRole());
////
////	        } else {
////
////	            System.out.println("Invalid username or password!");
////	        }
////		String adminHash =
////                PasswordUtil.hashPassword("admin123");
////
////        String receptionHash =
////                PasswordUtil.hashPassword("reception123");
////
////        System.out.println("Admin Hash:");
////        System.out.println(adminHash);
////
////        System.out.println();
////
////        System.out.println("Receptionist Hash:");
////        System.out.println(receptionHash);
////		String password = "admin123";
////
////        // Generate hash
////        String hashedPassword = PasswordUtil.hashPassword(password);
////
////        System.out.println("Original Password: " + password);
////        System.out.println("Hashed Password: " + hashedPassword);
////
////        // Correct password
////        boolean correct = PasswordUtil.checkPassword(
////                "admin123",
////                hashedPassword
////        );
////
////        // Wrong password
////        boolean wrong = PasswordUtil.checkPassword(
////                "wrong123",
////                hashedPassword
////        );
////
////        System.out.println("Correct Password: " + correct);
////        System.out.println("Wrong Password: " + wrong);
////		Guest guest = new Guest(
////	            "Harshit Jain",
////	            "9876543210",
////	            "Harshit@gmail.com",
////	            "AADHAAR",
////	            "123456789012"
////	        );
////
////	        GuestDAO guestDAO = new GuestDAO();
////
////	        boolean result = guestDAO.addGuest(guest);
////
////	        if (result) {
////	            System.out.println("Guest added successfully!");
////	        } else {
////	            System.out.println("Failed to add guest!");
////	        }
////		Room room = new Room (103, "Deluxe", 3500, "Available");
////		RoomDAO roomDAO = new RoomDAO();
////		List<Room> rooms = roomDAO.getAvailableRooms();
////
////        System.out.println("Available Rooms:");
////
////        for (Room room : rooms) {
////
////            System.out.println(
////                room.getRoomNumber() + " | " +
////                room.getRoomType() + " | " +
////                room.getPricePerDay()
////            );
////        }
////		 boolean result = roomDAO.deleteRoom(9);
////
////	        if (result) {
////	            System.out.println("Room deleted successfully!");
////	        } else {
////	            System.out.println("Room deletion failed!");
////	        }
////		Room room = roomDAO.getRoomById(3);
//
////        if (room != null) {
////
////        	room.setPricePerDay(4000);
////            room.setStatus("AVAILABLE");
////
////            boolean result = roomDAO.updateRoom(room);
////
////            if (result) {
////                System.out.println("Room updated successfully!");
////            } else {
////                System.out.println("Room update failed!");
////            }
////
////        } else {
////            System.out.println("Room not found!");
////        }
////		List<Room> rooms = roomDAO.getAllRooms();
////		for(Room room: rooms) {
////			System.out.println(
////					room.getRoomId() + " | " + room.getRoomNumber() + " | " + 
////			room.getRoomType() + " | " + room.getPricePerDay() + " | " + room.getStatus());
////		}
////		boolean result = roomDAO.addRoom(room);
////		if(result) {
////			System.out.println("Room added");
////		} else {
////			System.out.println("Failed to add Room");
////		}
//	}
//}
