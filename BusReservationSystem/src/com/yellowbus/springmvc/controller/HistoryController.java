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
import com.yellowbus.springmvc.service.RouteService;
import com.yellowbus.springmvc.service.TicketService;

@RestController
public class HistoryController {
	
	@Autowired TicketService ticketService;
	@Autowired BusService busService;
	@Autowired RouteService routeService;
	@Autowired BusTypeService busTypeService;
	
	@RequestMapping(value = "/api/controllers/history/{userID}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Wrapper>> getHistory(@PathVariable("userID")int userID)
	{
		/*ArrayList<Wrapper> list=new ArrayList<Wrapper>();
		Wrapper wrapper = new Wrapper();
		Ticket ticket = new Ticket();
		ticket.setBusID(123);
		ticket.setDepDate("12062018");
		ticket.setDepTime("1234");
		ticket.setFare(44);
		ticket.setNumSeats(2);
		ticket.setPnr(7777);
		ticket.setRouteID(999);
		ArrayList<Integer> seat=new ArrayList<Integer>();
		seat.add(12);
		seat.add(13);
		ticket.setSeatNums(seat);
		ticket.setUserID(778);
		wrapper.setTicket(ticket);
		wrapper.setBusName("Kallada");
		wrapper.setBusTypeName("AC-SF");
		wrapper.setDuration(90);
		wrapper.setBusNum("KL63");
		wrapper.setFromLocation("Mumbai");
		wrapper.setToLocation("Panjim");
		list.add(wrapper);
		ticket =new Ticket();
		wrapper = new Wrapper();
		ticket.setBusID(456);
		ticket.setDepDate("12092018");
		ticket.setDepTime("1234");
		ticket.setFare(44);
		ticket.setNumSeats(2);
		ticket.setPnr(888);
		ticket.setRouteID(999);
		seat=new ArrayList<Integer>();
		seat.add(14);
		seat.add(15);
		ticket.setSeatNums(seat);
		ticket.setUserID(778);
		wrapper.setTicket(ticket);
		wrapper.setBusName("Sisira");
		wrapper.setBusTypeName("NAC-SF");
		wrapper.setDuration(80);
		wrapper.setBusNum("KL683");
		wrapper.setFromLocation("Mumbai");
		wrapper.setToLocation("Thane");
		list.add(wrapper);*/
		
		ArrayList<Wrapper> list=new ArrayList<Wrapper>();
		ArrayList<Ticket> rawlist=new ArrayList<Ticket>();
		rawlist=ticketService.ViewBookingHistory(userID);
		if (rawlist.isEmpty()) {
			return new ResponseEntity<ArrayList<Wrapper>>(list,
					HttpStatus.OK);
		}
		for(Ticket ticket : rawlist)
		{
			Wrapper wrapper=new Wrapper();
			wrapper.setTicket(ticket);
			Bus bus=new Bus();
			bus=busService.getBusByID(ticket.getBusID());
			wrapper.setBusName(bus.getBusName());
			wrapper.setBusNum(bus.getBusNum());
			BusType busType=new BusType();
			busType=busTypeService.getBusTypebyID(bus.getBusType());
			wrapper.setBusTypeName(busType.getBusTypeName());
			Route route=new Route();
			route=routeService.getRouteByID(ticket.getRouteID());
			wrapper.setFromLocation(route.getFromLocation());
			wrapper.setToLocation(route.getToLocation());
			int distance = route.getDistance();
			int speed = busType.getSpeed();
			int duration = ((distance / speed) * 60) + (distance % speed);
			wrapper.setDuration(duration);
			list.add(wrapper);
		}
		return new ResponseEntity<ArrayList<Wrapper>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value="/api/controllers/history/{pnr}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> cancel(@PathVariable("pnr")int pnr)
	{
		ticketService.cancelTicket(pnr);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
