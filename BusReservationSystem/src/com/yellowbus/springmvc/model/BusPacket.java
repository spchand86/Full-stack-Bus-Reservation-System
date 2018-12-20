package com.yellowbus.springmvc.model;

public class BusPacket {
	private int busID;
	private String busName;
	private String busNum;

	public int getBusID() {
		return busID;
	}

	public void setBusID(int busID) {
		this.busID = busID;
	}

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public String getBusNum() {
		return busNum;
	}

	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

	@Override
	public String toString() {
		return "BusPacket [busID=" + busID + ", busName=" + busName
				+ ", busNum=" + busNum + "]";
	}
}
