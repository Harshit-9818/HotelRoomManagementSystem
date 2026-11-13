package com.hotel.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.hotel.model.Room;
import com.hotel.util.DBConnection;

public class RoomDAO {
	public boolean addRoom(Room room) {
		String sql = "Insert into rooms " + "(room_number, room_type, price_per_day, status) " + "VALUES (?, ?, ?, ?)";
		try(Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setInt(1, room.getRoomNumber());
			statement.setString(2, room.getRoomType());
			statement.setDouble(3, room.getPricePerDay());
			statement.setString(4, room.getStatus());
			
			int rowsInserted = statement.executeUpdate();
			 
			return rowsInserted > 0;
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	public List<Room> getAllRooms(){
		List<Room> rooms = new ArrayList<>();
		String sql = "SELECT * FROM rooms";
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()){
			while(resultSet.next()) {
				Room room = new Room();
				room.setRoomId(resultSet.getInt("room_id"));
				room.setRoomNumber(resultSet.getInt("room_number"));
				room.setRoomType(resultSet.getString("room_type"));
				room.setPricePerDay(resultSet.getDouble("price_per_day"));
				room.setStatus(resultSet.getString("status"));
				rooms.add(room);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rooms;
	}
	public Room getRoomById(int roomId) {

	    String sql = "SELECT * FROM rooms WHERE room_id = ?";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, roomId);

	        try (ResultSet resultSet = statement.executeQuery()) {

	            if (resultSet.next()) {

	                Room room = new Room();

	                room.setRoomId(resultSet.getInt("room_id"));
	                room.setRoomNumber(resultSet.getInt("room_number"));
	                room.setRoomType(resultSet.getString("room_type"));
	                room.setPricePerDay(resultSet.getDouble("price_per_day"));
	                room.setStatus(resultSet.getString("status"));

	                return room;
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	public boolean updateRoom(Room room) {

	    String sql = "UPDATE rooms SET " +
	                 "room_number = ?, " +
	                 "room_type = ?, " +
	                 "price_per_day = ?, " +
	                 "status = ? " +
	                 "WHERE room_id = ?";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, room.getRoomNumber());
	        statement.setString(2, room.getRoomType());
	        statement.setDouble(3, room.getPricePerDay());
	        statement.setString(4, room.getStatus());
	        statement.setInt(5, room.getRoomId());

	        int rowsUpdated = statement.executeUpdate();

	        return rowsUpdated > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean deleteRoom(int roomId) {

	    String sql = "DELETE FROM rooms WHERE room_id = ?";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setInt(1, roomId);

	        int rowsDeleted = statement.executeUpdate();

	        return rowsDeleted > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public List<Room> getAvailableRooms() {

	    List<Room> rooms = new ArrayList<>();

	    String sql = "SELECT * FROM rooms WHERE status = ?";

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        statement.setString(1, "AVAILABLE");

	        try (ResultSet resultSet = statement.executeQuery()) {

	            while (resultSet.next()) {

	                Room room = new Room();

	                room.setRoomId(resultSet.getInt("room_id"));
	                room.setRoomNumber(resultSet.getInt("room_number"));
	                room.setRoomType(resultSet.getString("room_type"));
	                room.setPricePerDay(resultSet.getDouble("price_per_day"));
	                room.setStatus(resultSet.getString("status"));

	                rooms.add(room);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rooms;
	}
}