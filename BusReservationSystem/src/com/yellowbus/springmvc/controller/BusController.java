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
import com.yellowbus.springmvc.service.BusService;
import com.yellowbus.springmvc.service.RouteService;

@RestController
public class BusController {

	@Autowired
	BusService busService;
	@Autowired
	RouteService routeService;

	@RequestMapping(value = "/api/controllers/bus", method = RequestMethod.POST)
	public ResponseEntity<Integer> addBus(@RequestBody Bus bus) {
		int busID = busService.addBus(bus);
		if (busID == -1) {
			return new ResponseEntity<Integer>(busID, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Integer>(busID, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/controllers/bus", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Wrapper>> getallBus() {
		ArrayList<Wrapper> list = new ArrayList<Wrapper>();
		ArrayList<Bus> list2 = busService.getAllBus();
		if (list2.isEmpty()) {
			return new ResponseEntity<ArrayList<Wrapper>>(list,
					HttpStatus.NOT_FOUND);
		}
		for (Bus bus : list2) {
			Wrapper temp = new Wrapper();
			temp.setBus(bus);
			if (bus.getRouteID() <= 0) {
				temp.setFromLocation("-");
				temp.setToLocation("-");
			} else {
				Route route = routeService.getRouteByID(bus.getRouteID());
				temp.setFromLocation(route.getFromLocation());
				temp.setToLocation(route.getToLocation());
			}
			list.add(temp);
		}
		return new ResponseEntity<ArrayList<Wrapper>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "api/controllers/bus/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteBus(@PathVariable("id") int busID) {
		System.out.println(busID);
		boolean isDeleted = busService.removeBus(busID);
		if (isDeleted)
			return new ResponseEntity<Void>(HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "api/controllers/bus/{id}", method = RequestMethod.GET)
	public ResponseEntity<Bus> getBus(@PathVariable("id") int busID) {
		Bus bus = busService.getBusByID(busID);
		if (bus == null)
			return new ResponseEntity<Bus>(bus, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Bus>(bus, HttpStatus.OK);
	}
}
