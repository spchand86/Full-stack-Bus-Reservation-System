package com.yellowbus.springmvc.model;

public class Route {
	private int routeID;
	private String fromLocation;
	private String toLocation;
	private int distance;

	public Route() {
		super();
	}

	public Route(int routeID, String fromLocation, String toLocation,
			int distance) {
		super();
		this.routeID = routeID;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.distance = distance;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
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

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Route [routeID=" + routeID + ", fromLocation=" + fromLocation
				+ ", toLocation=" + toLocation + ", distance=" + distance + "]";
	}
}
