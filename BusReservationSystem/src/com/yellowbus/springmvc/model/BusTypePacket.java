package com.yellowbus.springmvc.model;

public class BusTypePacket {
	private int busTypeID;
	private String busTypeName;

	public int getBusTypeID() {
		return busTypeID;
	}

	public void setBusTypeID(int busTypeID) {
		this.busTypeID = busTypeID;
	}

	public String getBusTypeName() {
		return busTypeName;
	}

	public void setBusTypeName(String busTypeName) {
		this.busTypeName = busTypeName;
	}

	@Override
	public String toString() {
		return "BusTypePacket [busTypeID=" + busTypeID + ", busTypeName="
				+ busTypeName + "]";
	}
}
