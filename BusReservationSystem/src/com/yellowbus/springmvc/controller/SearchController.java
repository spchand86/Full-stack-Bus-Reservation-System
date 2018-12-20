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
import com.yellowbus.springmvc.service.ScheduleService;
import com.yellowbus.springmvc.service.TicketService;

@RestController
public class SearchController {
	
	@Autowired TicketService ticketService;
	
	@Autowired
	ScheduleService scheduleService;
	@RequestMapping(value="/api/controllers/search/book", method=RequestMethod.POST)
	public ResponseEntity<Ticket> bookTicket(@RequestBody Ticket ticket)
	{
		//Dummy Testing
//		ticket.setPnr(7548);
//		ArrayList<Integer> list=new ArrayList<Integer>();
//		list.add(23);
//		list.add(45);
//		ticket.setSeatNums(list);
//		System.out.println(ticket);
		ticket = ticketService.bookTicket(ticket);
		
		return new ResponseEntity<Ticket>(ticket,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/controllers/search/get/{fromLocation}/{toLocation}/{datestr}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<SearchResult>> getSearchResults(
			@PathVariable("fromLocation") String fromLocation,
			@PathVariable("toLocation") String toLocation,
			@PathVariable("datestr") String datestr) {
		// Dummy Testing
		// System.out.println(fromLocation+toLocation+datestr);
		// SearchResult searchResult=new SearchResult();
		// searchResult.setDepTime("4455");
		// searchResult.setDuration(85);
		// searchResult.setFare(45);
		// searchResult.setRemainingSeats(12);
		// Bus bus=new Bus();
		// bus.setBusID(798);
		// bus.setBusName("K1a1l1l1a1d1a");
		// bus.setBusNum("KKL785");
		// bus.setBusType(6);
		// bus.setRouteID(77);
		// bus.setTotalSeats(50);
		// searchResult.setBus(bus);
		// ArrayList<SearchResult> list=new ArrayList<SearchResult>();
		// list.add(searchResult);
		// searchResult=new SearchResult();
		// searchResult.setDepTime("4455");
		// searchResult.setDuration(85);
		// searchResult.setFare(45);
		// searchResult.setRemainingSeats(12);
		// bus=new Bus();
		// bus.setBusID(798);
		// bus.setBusName("K2a2l2lada");
		// bus.setBusNum("KKL785");
		// bus.setBusType(6);
		// bus.setRouteID(77);
		// bus.setTotalSeats(50);
		// searchResult.setBus(bus);
		// list.add(searchResult);
		int routeID = scheduleService.getRouteID(fromLocation, toLocation);
		ArrayList<SearchResult> list = scheduleService.search(routeID, datestr);
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<SearchResult>>(list,
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<ArrayList<SearchResult>>(list,
					HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/api/controllers/search/get/{fromLocation}/{toLocation}/{datestr}/{busTypeID}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<SearchResult>> getSearchResultsbytype(
			@PathVariable("fromLocation") String fromLocation,
			@PathVariable("toLocation") String toLocation,
			@PathVariable("datestr") String datestr,
			@PathVariable("busTypeID") String busTypeID) {
		// Dummy Testing
		// System.out.println(fromLocation+toLocation+datestr+busTypeID);
		// SearchResult searchResult=new SearchResult();
		// searchResult.setDepTime("4455");
		// searchResult.setDuration(85);
		// searchResult.setFare(45);
		// searchResult.setRemainingSeats(12);
		// Bus bus=new Bus();
		// bus.setBusID(798);
		// bus.setBusName("Kallada");
		// bus.setBusNum("KKL785");
		// bus.setBusType(6);
		// bus.setRouteID(77);
		// bus.setTotalSeats(50);
		// searchResult.setBus(bus);
		// ArrayList<SearchResult> list=new ArrayList<SearchResult>();
		// list.add(searchResult);

		int routeID = scheduleService.getRouteID(fromLocation, toLocation);
		ArrayList<SearchResult> list = scheduleService.search(routeID, datestr, Integer.parseInt(busTypeID));
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<SearchResult>>(list,
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<ArrayList<SearchResult>>(list,
					HttpStatus.OK);
		}
	}
}