package com.hotel.model;
import java.time.LocalDate;
public class Booking {
	private int bookingId;
	private int guestId;
	private int roomId;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private double totalAmount;
	private String status;
	
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public LocalDate getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}
	public LocalDate getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Booking() {
	}
	public Booking(int guestId, int roomId, LocalDate checkIn, LocalDate checkOut, double totalAmount, String status) {
		this.guestId = guestId;
		this.roomId = roomId;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.totalAmount = totalAmount;
		this.status = status;
	}	
}
