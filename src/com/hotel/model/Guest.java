package com.hotel.model;

public class Guest {
	private int guestId;
	private String name;
	private String phone;
	private String email;
	private String idType;
	private String idNumber;
	
	public int getGuestId() {
		return guestId;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Guest() {
	}
	public Guest(String name, String phone, String email, String idType, String idNumber) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.idType = idType;
		this.idNumber = idNumber;
	}
}
