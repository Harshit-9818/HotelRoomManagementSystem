package com.hotel.model;
import java.time.LocalDateTime;
public class Bill {
	private int billId;
	private int bookingId;
	private double roomCharges;
	private double serviceCharges;
	private double tax;
	private double discount;
	private double totalAmount;
	private LocalDateTime generatedAt;
	
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public double getRoomCharges() {
		return roomCharges;
	}
	public void setRoomCharges(double roomCharges) {
		this.roomCharges = roomCharges;
	}
	public double getServiceCharges() {
		return serviceCharges;
	}
	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getGeneratedAt() {
		return generatedAt;
	}
	public void setGeneratedAt(LocalDateTime generatedAt) {
		this.generatedAt = generatedAt;
	}
	
	public Bill() {
	}
	public Bill(int bookingId, double roomCharges, double serviceCharges, double tax, double discount,
			double totalAmount) {
		this.bookingId = bookingId;
		this.roomCharges = roomCharges;
		this.serviceCharges = serviceCharges;
		this.tax = tax;
		this.discount = discount;
		this.totalAmount = totalAmount;
	}
	
}
