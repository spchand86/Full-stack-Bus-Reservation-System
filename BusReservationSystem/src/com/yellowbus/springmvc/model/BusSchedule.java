package com.yellowbus.springmvc.model;

import java.util.ArrayList;

public class BusSchedule {
	private int busID;
	private int routeID;
	private int duration;
	private int fare;
	private ArrayList<ArrayList<String>> depTimings = new ArrayList<ArrayList<String>>();

	public BusSchedule() {
		super();
	}

	public BusSchedule(int busID, int routeID, int duration, int fare,
			ArrayList<ArrayList<String>> depTimings) {
		super();
		this.busID = busID;
		this.routeID = routeID;
		this.duration = duration;
		this.fare = fare;
		this.depTimings = depTimings;
	}

	public int getBusID() {
		return busID;
	}

	public void setBusID(int busID) {
		this.busID = busID;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public ArrayList<ArrayList<String>> getDepTimings() {
		return depTimings;
	}

	public void setDepTimings(ArrayList<ArrayList<String>> depTimings) {
		this.depTimings = depTimings;
	}

	@Override
	public String toString() {
		return "BusSchedule [busID=" + busID + ", routeID=" + routeID
				+ ", duration=" + duration + ", fare=" + fare + ", depTimings="
				+ depTimings + "]";
	}

}
