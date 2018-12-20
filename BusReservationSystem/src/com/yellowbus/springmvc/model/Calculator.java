package com.yellowbus.springmvc.model;

public class Calculator {
	private int busID;
	private int routeID;
	private int duration;
	private int fare;

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

	@Override
	public String toString() {
		return "Calculator [busID=" + busID + ", routeID=" + routeID
				+ ", duration=" + duration + ", fare=" + fare + "]";
	}

}
