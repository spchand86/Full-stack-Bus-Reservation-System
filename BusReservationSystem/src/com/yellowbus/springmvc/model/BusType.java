package com.yellowbus.springmvc.model;

public class BusType {
	private int busTypeID;
	private String busTypeName;
	private int speed;
	private boolean ac;
	private int farePerKM;
	
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
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public boolean isAc() {
		return ac;
	}
	
	public void setAc(boolean ac) {
		this.ac = ac;
	}
	
	public int getFarePerKM() {
		return farePerKM;
	}
	
	public void setFarePerKM(int farePerKM) {
		this.farePerKM = farePerKM;
	}
	
	@Override
	public String toString() {
		return "BusType [busTypeID=" + busTypeID + ", busTypeName="
				+ busTypeName + ", speed=" + speed + ", ac=" + ac
				+ ", farePerKM=" + farePerKM + "]";
	}
	
}
