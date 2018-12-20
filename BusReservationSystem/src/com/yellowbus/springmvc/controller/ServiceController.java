package com.yellowbus.springmvc.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yellowbus.springmvc.model.*;
import com.yellowbus.springmvc.service.BusService;
import com.yellowbus.springmvc.service.BusTypeService;

@RestController
public class ServiceController {
	@Autowired
	BusTypeService busTypeService;
	@Autowired
	BusService busService;

	@RequestMapping(value = "/api/services/bustype", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<BusTypePacket>> getallBusTypePackets() {
		ArrayList<BusTypePacket> list = new ArrayList<BusTypePacket>();
		ArrayList<BusType> list2 = busTypeService.getAllBusType();
		for (BusType busType : list2) {
			BusTypePacket temp = new BusTypePacket();
			temp.setBusTypeID(busType.getBusTypeID());
			temp.setBusTypeName(busType.getBusTypeName());
			list.add(temp);
		}
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<BusTypePacket>>(list,
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<BusTypePacket>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/bustype/{busTypeID}", method = RequestMethod.GET)
	public ResponseEntity<BusTypePacket> getBusTypePacket(
			@PathVariable("busTypeID") int busTypeID) {
		BusTypePacket busTypePacket = new BusTypePacket();
		BusType busType = busTypeService.getBusTypebyID(busTypeID);
		if (busType == null) {
			return new ResponseEntity<BusTypePacket>(busTypePacket,
					HttpStatus.NOT_FOUND);
		}
		busTypePacket.setBusTypeID(busTypeID);
		busTypePacket.setBusTypeName(busType.getBusTypeName());
		return new ResponseEntity<BusTypePacket>(busTypePacket, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/bus/{busID}", method = RequestMethod.GET)
	public ResponseEntity<BusPacket> getBusPacket(
			@PathVariable("busID") int busID) {
		// System.out.println(busID);
		BusPacket busPacket = new BusPacket();
		Bus bus = busService.getBusByID(busID);
		if (bus == null) {
			return new ResponseEntity<BusPacket>(busPacket,
					HttpStatus.NOT_FOUND);
		}
		busPacket.setBusID(busID);
		busPacket.setBusName(bus.getBusName());
		busPacket.setBusNum(bus.getBusNum());
		return new ResponseEntity<BusPacket>(busPacket, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/bus", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<BusPacket>> getallBusPackets() {
		// System.out.println("Getting all bus packets");
		// ArrayList<BusPacket> list = new ArrayList<BusPacket>();
		// BusPacket busPacket = new BusPacket();
		// busPacket.setBusID(111);
		// busPacket.setBusName("ABC");
		// busPacket.setBusNum("KL07");
		// list.add(busPacket);
		// busPacket = new BusPacket();
		// busPacket.setBusID(222);
		// busPacket.setBusName("DEF");
		// busPacket.setBusNum("KL08");
		ArrayList<BusPacket> list = new ArrayList<BusPacket>();
		ArrayList<Bus> list2 = busService.getAllBus();
		for (Bus bus : list2) {
			BusPacket temp = new BusPacket();
			temp.setBusID(bus.getBusID());
			temp.setBusName(bus.getBusName());
			temp.setBusNum(bus.getBusNum());
			list.add(temp);
		}
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<BusPacket>>(list,
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<BusPacket>>(list, HttpStatus.OK);
	}
}
