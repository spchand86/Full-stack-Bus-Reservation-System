package com.yellowbus.springmvc.controller;

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
import com.yellowbus.springmvc.service.BusTypeService;
import com.yellowbus.springmvc.service.RouteService;
import com.yellowbus.springmvc.service.ScheduleService;

@RestController
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	BusService busService;
	@Autowired
	RouteService routeService;
	@Autowired
	BusTypeService busTypeService;

	@RequestMapping(value = "/api/controllers/schedule/end", method = RequestMethod.POST)
	public ResponseEntity<Void> addSchedule(@RequestBody BusSchedule busSchedule) {
		// Dummy Testing
		// System.out.println(busSchedule.getDuration());
		boolean added = scheduleService.addBusSchedule(busSchedule);
		if (!added) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/api/controllers/schedule/end/{busID}", method = RequestMethod.GET)
	public ResponseEntity<BusSchedule> getBusSchedule(
			@PathVariable("busID") int busID) {
		// Dummy Testing
		// BusSchedule busSchedule = new BusSchedule();
		// busSchedule.setBusID(busID);
		// busSchedule.setFare(999);
		BusSchedule busSchedule = scheduleService.viewBusSchedule(busID);
		if (busSchedule == null) {
			return new ResponseEntity<BusSchedule>(busSchedule,
					HttpStatus.CONFLICT);
		}
		return new ResponseEntity<BusSchedule>(busSchedule, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/controllers/schedule/calculate", method = RequestMethod.POST)
	public ResponseEntity<Calculator> Calculate(
			@RequestBody Calculator calculator) {
		// Dummy Testing
		// calculator.setDuration(74);
		// calculator.setFare(199);
		Bus bus = busService.getBusByID(calculator.getBusID());
		BusType busType = busTypeService.getBusTypebyID(bus.getBusType());
		Route route = routeService.getRouteByID(calculator.getRouteID());
		int distance = route.getDistance();
		int speed = busType.getSpeed();
		int duration = ((distance / speed) * 60) + (distance % speed);
		System.out.println(distance);
		System.out.println(speed);
		System.out.println(route);
		int farePerKm = busType.getFarePerKM();
		int fare = farePerKm * distance;
		calculator.setDuration(duration);
		calculator.setFare(fare);
		System.out.println(calculator);
		return new ResponseEntity<Calculator>(calculator, HttpStatus.OK);
	}
}
