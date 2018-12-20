package com.yellowbus.springmvc.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.yellowbus.springmvc.model.*;
import com.yellowbus.springmvc.service.BusTypeService;

@RestController
public class BusTypeController {
	@Autowired
	BusTypeService bustypeService;
	
	@RequestMapping(value = "/api/controllers/bustype", method = RequestMethod.POST)
	public ResponseEntity<Integer> addBusType(@RequestBody BusType bustype) {
		// Dummy Testing
//		bustype.setBusTypeID(862);
//		System.out.println(bustype);
		int bustypeid = bustypeService.addBusTypeID(bustype);
		return new ResponseEntity<Integer>(bustypeid,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/api/controllers/bustype", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<BusType>> getallBusType() {
		// Dummy Testing
//		ArrayList<BusType> list = new ArrayList<BusType>();
//		BusType bustype = new BusType();
//		bustype.setBusTypeID(798);
//		bustype.setBusTypeName("Kallada");
//		bustype.setAc(true);
//		bustype.setFarePerKM(22);
//		bustype.setSpeed(66);
//		list.add(bustype);
//		bustype = new BusType();
//		bustype.setBusTypeID(798);
//		bustype.setBusTypeName("Kall1ada");
//		bustype.setAc(true);
//		bustype.setFarePerKM(212);
//		bustype.setSpeed(616);
//		list.add(bustype);
		
		ArrayList<BusType> list = bustypeService.getAllBusType();
		return new ResponseEntity<ArrayList<BusType>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "api/controllers/bustype/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBusType(@PathVariable("id") int bustypeID) {
		// Dummy Testing
//		System.out.println(bustypeID);
		boolean isDeleted = bustypeService.removeBusType(bustypeID);
		if(isDeleted)
			return new ResponseEntity<Void>(HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "api/controllers/bustype/{id}", method = RequestMethod.GET)
	public ResponseEntity<BusType> getBusType(@PathVariable("id") int bustypeID) {
//		// Dummy Testing
//		System.out.println(bustypeID);
//		BusType bustype = new BusType();
//		bustype.setBusTypeID(798);
//		bustype.setBusTypeName("Kallada");
//		bustype.setAc(true);
//		bustype.setFarePerKM(22);
//		bustype.setSpeed(66);
		BusType bustype = bustypeService.getBusTypebyID(bustypeID);
		return new ResponseEntity<BusType>(bustype, HttpStatus.OK);
	}
}
