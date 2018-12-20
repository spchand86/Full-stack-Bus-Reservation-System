package com.yellowbus.springmvc.service;

import java.util.ArrayList;

import com.yellowbus.springmvc.model.BusSchedule;
import com.yellowbus.springmvc.model.Calculator;
import com.yellowbus.springmvc.model.SearchResult;

public interface ScheduleService {
	ArrayList<SearchResult> search(int routeID, String date);

	ArrayList<SearchResult> search(int routeID, String date, int busTypeID);

	BusSchedule viewBusSchedule(int busID);

	boolean addBusSchedule(BusSchedule busSchedule);
	
	int getRouteID(String fromLoc, String toLoc);
}
