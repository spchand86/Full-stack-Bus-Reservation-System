package com.yellowbus.springmvc.service;

import java.util.ArrayList;

import com.yellowbus.springmvc.model.Route;

public interface RouteService {
	ArrayList<Route> getAllRoutes();

	boolean addRoute(Route route);

	Route getRouteByID(int routeID);

	boolean deleteRouteByID(int routeID);

	ArrayList<String> getUniqueCities();

	ArrayList<String> getToLocs(String fromLoc);

	int getID(String fromLoc, String toLoc);

	boolean isDuplicate(Route route);

	boolean isDuplicate(int routeID);
}
