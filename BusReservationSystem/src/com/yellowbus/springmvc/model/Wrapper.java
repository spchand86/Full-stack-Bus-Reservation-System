package com.yellowbus.springmvc.model;

public class Wrapper {
	private String busNum;
	private String busName;
	private String busTypeName;
	private String fromLocation;
	private String toLocation;
	private Ticket ticket;
	private Bus bus;
	private int duration;

	public String getBusNum() {
		return busNum;
	}

	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusTypeName() {
		return busTypeName;
	}

	public void setBusTypeName(String busTypeName) {
		this.busTypeName = busTypeName;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Wrapper [busNum=" + busNum + ", busName=" + busName
				+ ", busTypeName=" + busTypeName + ", fromLocation="
				+ fromLocation + ", toLocation=" + toLocation + ", ticket="
				+ ticket + ", bus=" + bus + ", duration=" + duration + "]";
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

}
