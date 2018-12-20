package com.yellowbus.springmvc.model;

public class User {
	private int userID;
	private String password;
	private String userName;
	private String name;

	public User() {
		super();
	}

	public User(int userID, String password, String userName, String name) {
		super();
		this.userID = userID;
		this.password = password;
		this.userName = userName;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", password=" + password
				+ ", userName=" + userName + ", name=" + name + "]";
	}

}
