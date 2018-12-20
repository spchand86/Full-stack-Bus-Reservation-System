package com.yellowbus.springmvc.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.yellowbus.springmvc.model.Ticket;

public class TicketUtil {
	
	public static ArrayList<Integer> convertStringTicketsToArrayList(String seatNos)
	{
		ArrayList<Integer> seats = new ArrayList<Integer>();
		StringTokenizer st1 = new StringTokenizer(seatNos,",");
		while(st1.hasMoreTokens())
		{
			seats.add(Integer.parseInt(st1.nextToken()));
		}
		return seats;
	}
	
	public static String convertArrayListtoString(ArrayList<Integer> tickets)
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<tickets.size();i++)
		{
			sb.append(tickets.get(i));
			if(i!=tickets.size()-1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public static String getJourneyID(Ticket ticket)
	{
		String d = ticket.getDepDate().substring(0,4);
		String t = ticket.getDepTime();
		t=t.substring(0,2)+t.substring(3,5);
		
		String journeyID = d + t;
		
		return journeyID;
	}
	
	// 0 means seat is not booked yet;
	public static int getSeatsAvailable(String seats)
	{
		int seatsAvail = 0;
		for(int i=0;i<seats.length();i++)
		{
			if(seats.charAt(i)=='0')
				seatsAvail++;
		}
		
		return seatsAvail;
	}
	
	
	public static ArrayList<Integer> assignSeatNumbers(String seatsString, int numseats)
	{
		int seats = 0;
		ArrayList<Integer> assignedSeats = new ArrayList<Integer>();
		
		for(int i=0;i<seatsString.length()&&seats<numseats;i++)
		{
			if(seatsString.charAt(i)=='0')
			{
				assignedSeats.add(i+1);
				seats++;
			}
		}
		
		return assignedSeats;
	
	}
	
	
	
	public static String changeSeatsString(String seatsString,int numseats)
	{
		StringBuilder newSeatString = new StringBuilder(seatsString);
		int count = 0;
		for(int i=0;i<newSeatString.length()&&count<numseats;i++)
		{
			if(newSeatString.charAt(i)=='0')
			{
				newSeatString.setCharAt(i, '1');
				count++;
			}
		}
		
		return newSeatString.toString();
	}

	public static String getSeatsStringAfterCancellation(Ticket tick,
			String currentSeatsString)
	{
		StringBuilder newSeatsString = new StringBuilder(currentSeatsString);
		
		ArrayList<Integer> ar = tick.getSeatNums();
		for(int i=0;i<ar.size();i++)
		{
			newSeatsString.setCharAt(ar.get(i)-1, '0');
		}
		// TODO Auto-generated method stub
		return newSeatsString.toString();
	}
}
