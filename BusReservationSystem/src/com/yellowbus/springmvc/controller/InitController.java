package com.yellowbus.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yellowbus.springmvc.service.SeatTrackingService;

@RestController
public class InitController {
	@Autowired
	SeatTrackingService seatTrackingService;

	@RequestMapping(value = "/api/services/init", method = RequestMethod.GET)
	public ResponseEntity<Void> intialize() {
		boolean success = seatTrackingService.initializeSeatTracker();
		if (success) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/api/services/init/force", method = RequestMethod.GET)
	public ResponseEntity<Void> forcedupdate() {
		boolean success = seatTrackingService.insertnewSchedule();
		if (success) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}
}
