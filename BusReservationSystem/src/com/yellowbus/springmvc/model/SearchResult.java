package com.yellowbus.springmvc.model;

public class SearchResult {
	private Bus bus;
	private String depTime;
	private int duration;
	private int fare;
	private int remainingSeats;

	public SearchResult() {
		super();
	}

	public SearchResult(Bus bus, String depTime, int duration, int fare,
			int remainingSeats) {
		super();
		this.bus = bus;
		this.depTime = depTime;
		this.duration = duration;
		this.fare = fare;
		this.remainingSeats = remainingSeats;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
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

	public int getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(int remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

	@Override
	public String toString() {
		return "SearchResult [bus=" + bus + ", depTime=" + depTime
				+ ", duration=" + duration + ", fare=" + fare
				+ ", remainingSeats=" + remainingSeats + "]";
	}

}
