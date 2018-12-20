package com.yellowbus.springmvc.model;

public class Bus {
	private int busID;
	private int routeID;
	private String busNum;
	private int busType;
	private String busName;
	private int totalSeats;

	public Bus() {
		super();
	}

	public Bus(int busID, int routeID, String busNum, int busType,
			String busName, int totalSeats) {
		super();
		this.busID = busID;
		this.routeID = routeID;
		this.busNum = busNum;
		this.busType = busType;
		this.busName = busName;
		this.totalSeats = totalSeats;
	}

	public int getBusID() {
		return busID;
	}

	public void setBusID(int busID) {
		this.busID = busID;
	}

	public String getBusNum() {
		return busNum;
	}

	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

	public int getBusType() {
		return busType;
	}

	public void setBusType(int busType) {
		this.busType = busType;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	@Override
	public String toString() {
		return "Bus [busID=" + busID + ", routeID=" + routeID + ", busNum="
				+ busNum + ", busType=" + busType + ", busName=" + busName
				+ ", totalSeats=" + totalSeats + "]";
	}

}
