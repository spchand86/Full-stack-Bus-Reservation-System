package com.yellowbus.springmvc.model;



import java.util.ArrayList;

public class Ticket {
	private int userID;
	private int routeID;
	private int busID;
	private String depDate;
	private String depTime;
	private int numSeats;
	private int fare;
	private ArrayList<Integer> seatNums;
	private int pnr;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getBusID() {
		return busID;
	}

	public void setBusID(int busID) {
		this.busID = busID;
	}

	public String getDepDate() {
		return depDate;
	}

	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public ArrayList<Integer> getSeatNums() {
		return seatNums;
	}

	public void setSeatNums(ArrayList<Integer> seatNums) {
		this.seatNums = seatNums;
	}

	public int getPnr() {
		return pnr;
	}

	public void setPnr(int pnr) {
		this.pnr = pnr;
	}

	@Override
	public String toString() {
		return "Ticket [userID=" + userID + ", routeID=" + routeID + ", busID="
				+ busID + ", depDate=" + depDate + ", depTime=" + depTime
				+ ", numSeats=" + numSeats + ", fare=" + fare + ", seatNums="
				+ seatNums + ", pnr=" + pnr + "]";
	}

}
