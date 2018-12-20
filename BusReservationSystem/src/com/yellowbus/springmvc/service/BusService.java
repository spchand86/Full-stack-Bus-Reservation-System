package com.yellowbus.springmvc.service;

import java.util.ArrayList;

import com.yellowbus.springmvc.model.Bus;

public interface BusService {
	ArrayList<Bus> getAllBus();
	
	int addBus(Bus bus);
	
	Bus getBusByID(int busId);
	
	boolean removeBus(int busId);
	
	
}
