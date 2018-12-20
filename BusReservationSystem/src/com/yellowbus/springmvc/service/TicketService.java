package com.yellowbus.springmvc.service;

import java.util.ArrayList;

import com.yellowbus.springmvc.model.Ticket;

public interface TicketService {
	
	ArrayList<Ticket> ViewBookingHistory(int userid);
	
	Ticket bookTicket(Ticket ticket);
	
	boolean cancelTicket(int pnr);
}
