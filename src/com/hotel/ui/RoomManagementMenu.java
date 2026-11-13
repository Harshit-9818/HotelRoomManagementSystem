package com.hotel.ui;

import java.util.List;
import java.util.Scanner;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;

public class RoomManagementMenu {

    private Scanner scanner;
    private RoomDAO roomDAO;

    public RoomManagementMenu() {
        scanner = new Scanner(System.in);
        roomDAO = new RoomDAO();
    }

    private void addRoom() {

        System.out.println();
        System.out.println("================================");
        System.out.println("           ADD ROOM");
        System.out.println("================================");

        try {

            System.out.print("Enter Room Number: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Room Type: ");
            String roomType = scanner.nextLine().toUpperCase();

            System.out.print("Enter Price Per Day: ₹");
            double pricePerDay = scanner.nextDouble();
            scanner.nextLine();

            Room room = new Room(
                    roomNumber,
                    roomType,
                    pricePerDay,
                    "AVAILABLE"
            );

            boolean added = roomDAO.addRoom(room);

            if (added) {

                System.out.println();
                System.out.println(
                        "Room added successfully!");

            } else {

                System.out.println();
                System.out.println(
                        "Failed to add room.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Invalid input!");

            scanner.nextLine();
        }
    }

    private void viewAllRooms() {

        System.out.println();
        System.out.println("================================");
        System.out.println("          ALL ROOMS");
        System.out.println("================================");

        List<Room> rooms = roomDAO.getAllRooms();

        if (rooms.isEmpty()) {

            System.out.println("No rooms found.");
            return;
        }

        for (Room room : rooms) {

            System.out.println(
                    "Room ID       : " + room.getRoomId());

            System.out.println(
                    "Room Number   : " + room.getRoomNumber());

            System.out.println(
                    "Room Type     : " + room.getRoomType());

            System.out.println(
                    "Price Per Day : ₹" +
                    room.getPricePerDay());

            System.out.println(
                    "Status        : " + room.getStatus());

            System.out.println("--------------------------------");
        }
    }

    private void updateRoom() {

        System.out.println();
        System.out.println("================================");
        System.out.println("         UPDATE ROOM");
        System.out.println("================================");

        try {

            System.out.print("Enter Room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine();

            Room room = roomDAO.getRoomById(roomId);

            if (room == null) {

                System.out.println(
                        "Room not found.");

                return;
            }

            System.out.println();
            System.out.println("Current Room Details:");
            System.out.println(
                    "Room Number: " +
                    room.getRoomNumber());

            System.out.println(
                    "Room Type: " +
                    room.getRoomType());

            System.out.println(
                    "Price: ₹" +
                    room.getPricePerDay());

            System.out.println(
                    "Status: " +
                    room.getStatus());

            System.out.println();

            System.out.print("Enter New Room Number: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter New Room Type: ");
            String roomType = scanner.nextLine().toUpperCase();

            System.out.print("Enter New Price Per Day: ₹");
            double pricePerDay = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter New Status: ");
            String status = scanner.nextLine().toUpperCase();

            room.setRoomNumber(roomNumber);
            room.setRoomType(roomType);
            room.setPricePerDay(pricePerDay);
            room.setStatus(status);

            boolean updated =
                    roomDAO.updateRoom(room);

            if (updated) {

                System.out.println(
                        "Room updated successfully!");

            } else {

                System.out.println(
                        "Failed to update room.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Invalid input!");

            scanner.nextLine();
        }
    }

    private void deleteRoom() {

        System.out.println();
        System.out.println("================================");
        System.out.println("          DELETE ROOM");
        System.out.println("================================");

        try {

            System.out.print("Enter Room ID: ");
            int roomId = scanner.nextInt();
            scanner.nextLine();

            System.out.print(
                    "Are you sure you want to delete this room? (Y/N): ");

            String confirmation =
                    scanner.nextLine();

            if (!confirmation.equalsIgnoreCase("Y")) {

                System.out.println(
                        "Delete operation cancelled.");

                return;
            }

            boolean deleted =
                    roomDAO.deleteRoom(roomId);

            if (deleted) {

                System.out.println(
                        "Room deleted successfully!");

            } else {

                System.out.println(
                        "Failed to delete room.");
            }

        } catch (Exception e) {

            System.out.println(
                    "Invalid input!");

            scanner.nextLine();
        }
    }

    private void viewAvailableRooms() {

        System.out.println();
        System.out.println("================================");
        System.out.println("       AVAILABLE ROOMS");
        System.out.println("================================");

        List<Room> rooms =
                roomDAO.getAvailableRooms();

        if (rooms.isEmpty()) {

            System.out.println(
                    "No rooms are currently available.");

            return;
        }

        for (Room room : rooms) {

            System.out.println(
                    "Room " + room.getRoomNumber()
                    + " | "
                    + room.getRoomType()
                    + " | ₹"
                    + room.getPricePerDay()
                    + " per day");
        }
    }

    public void showMenu() {

        while (true) {

            System.out.println();
            System.out.println("================================");
            System.out.println("       ROOM MANAGEMENT");
            System.out.println("================================");

            System.out.println("1. Add Room");
            System.out.println("2. View All Rooms");
            System.out.println("3. Update Room");
            System.out.println("4. Delete Room");
            System.out.println("5. View Available Rooms");
            System.out.println("6. Back");

            System.out.println("================================");

            System.out.print("Enter your choice: ");

            try {

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        addRoom();
                        break;

                    case 2:
                        viewAllRooms();
                        break;

                    case 3:
                        updateRoom();
                        break;

                    case 4:
                        deleteRoom();
                        break;

                    case 5:
                        viewAvailableRooms();
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println(
                                "Invalid choice!");
                }

            } catch (Exception e) {

                System.out.println(
                        "Invalid input! Please enter a number.");

                scanner.nextLine();
            }
        }
    }
}