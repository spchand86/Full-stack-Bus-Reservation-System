package com.yellowbus.springmvc.service;

import java.util.ArrayList;

import com.yellowbus.springmvc.model.BusType;

public interface BusTypeService {
	
	ArrayList<BusType> getAllBusType();
	
	int addBusTypeID(BusType bustype);
	
	BusType getBusTypebyID(int busTypeID);
	
	boolean removeBusType(int busTypeID);

}
