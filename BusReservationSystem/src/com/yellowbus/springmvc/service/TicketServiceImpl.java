package com.yellowbus.springmvc.service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.model.Ticket;
import com.yellowbus.springmvc.util.TicketUtil;

@Service("TicketService")
@Transactional
public class TicketServiceImpl implements TicketService {


	ArrayList<Ticket> tickets;
	private java.sql.Connection conn = null;

	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "bus_reservation_system2";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root";
	String strQuery= "";
	Statement pst = null;
	//PreparedStatement pst=null;
	ResultSet rst=null;

	private void connectToDB()
	{
		try
		{
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+dbName,userName,password);
		}
		catch (Exception ex)
		{

			System.out.println("CandidateSchemeDAO Error: could not connect to db");
			System.out.println("- Exception: " + ex.toString());
		}
	}

	public void closeDBConn()
	{
		try
		{
			if (!conn.isClosed())
				conn.close();
		}
		catch (Exception ex)
		{

			System.out.println("- Exception: " + ex.toString());
		}
	}

	@Override
	public ArrayList<Ticket> ViewBookingHistory(int userid)
	{
		connectToDB();
		Ticket ticket;
		tickets = new ArrayList<Ticket>();
		try
		{
			String query ="select * from booking_history where user_id="+userid;
			//pst=conn.prepareStatement(query.toString());
			pst = conn.createStatement();
			rst = pst.executeQuery(query);

			while(rst.next())
			{
				ticket = new Ticket();
				ticket.setUserID(rst.getInt("user_id"));
				ticket.setRouteID(rst.getInt("route_id"));
				ticket.setBusID(rst.getInt("bus_id"));
				ticket.setDepDate(rst.getString("date"));
				ticket.setDepTime(rst.getString("dep_time"));
				ticket.setNumSeats(rst.getInt("num_of_seats"));
				ticket.setFare(rst.getInt("fare"));
				ticket.setSeatNums(TicketUtil.convertStringTicketsToArrayList(rst.getString("seat_nums")));
				ticket.setPnr(rst.getInt("pnr"));
				tickets.add(ticket);
			}

			return tickets;
		}
		catch(Exception e)
		{
			System.out.println("Error in getting all buses");
			e.printStackTrace();
		}
		finally
		{
			//rst.close();
			//pst.close();
			closeDBConn();
		}

		return tickets;
	}

	 


	public Ticket bookTicket(Ticket ticket)
	{
		PreparedStatement preparedStatement = null;
		String newSeatsString = "";
		String journeyID = TicketUtil.getJourneyID(ticket);
		System.out.println("busid:"+ticket.getBusID()+" JID:"+journeyID);
//		String query = " select seats_available from seat_tracking where bus_id = " + ticket.getBusID() + " and journey_id = " + journeyID;
		//String selectQuery = "SELECT seats_available FROM seat_tracking WHERE bus_id = " + ticket.getBusID() + " AND journey_id = " + journeyID + "';";
		String selectQuery = "SELECT seats_available FROM seat_tracking WHERE bus_id = " + ticket.getBusID() + " AND journey_id = '" + journeyID + "';";
//		String updateQuery = "update seat_tracking set seats_available = ? where bus_id = ? and journey_id = ?";
		String insertToBookingHistoryQuery = "insert into booking_history(user_id,dep_time,route_id,date,fare,bus_id,seat_nums,num_of_seats,journey_id) values(?,?,?,?,?,?,?,?,?)";
		try
		{
//			pst = conn.createStatement();
//			rst = pst.executeQuery(query);`
			//goes to seat_tracker
			connectToDB();
			
			
			pst = conn.createStatement();
			rst = pst.executeQuery(selectQuery);
			//pst = conn.createStatement();
			//rst = pst.executeQuery(selectQuery);
//			preparedStatement = conn.prepareStatement(selectQuery);
//			preparedStatement.setInt(1,ticket.getBusID());
//			preparedStatement.setString(2,journeyID);
//			rst = preparedStatement.executeQuery();
			while(rst.next())
			{
				//System.out.println("NinteThantha");
				String seatsString = rst.getString("seats_available");
				//System.out.println("1111a:"+seatsString);
				int seatsAvailable = TicketUtil.getSeatsAvailable(seatsString);

				if(seatsAvailable < ticket.getNumSeats())
				{
					return null;
				}

				ticket.setSeatNums(TicketUtil.assignSeatNumbers(seatsString,ticket.getNumSeats()));
				newSeatsString = TicketUtil.changeSeatsString(seatsString, ticket.getNumSeats());
			}
			System.out.println("newSeatsString:"+newSeatsString);
			String updatequery = "UPDATE seat_tracking SET seats_available = " + newSeatsString + " WHERE bus_id = " + ticket.getBusID() + " AND journey_id = " + journeyID;
			pst = conn.createStatement();
			pst.executeUpdate(updatequery);
			//updating seats information in seat_tracker
//			preparedStatement = conn.prepareStatement(updateQuery);
//			preparedStatement.setString(1, newSeatsString);
//			preparedStatement.setInt(2,ticket.getBusID());
//			preparedStatement.setString(3, journeyID);
//			preparedStatement.executeUpdate();
			
			//insert a ticket into booking_history
//			String query ="INSERT INTO bus_master (bus_num, bus_type, total_seats, bus_name,route_id) values ('"+ bus.getBusNum()+"','"+bus.getBusType()+"','"+bus.getTotalSeats() +"','"+ bus.getBusName()+"','"+bus.getRouteID()+"')";
			String insertIntoBookingHistory = "insert into booking_history(user_id,dep_time,route_id,date,fare,bus_id,seat_nums,num_of_seats) values ('"+ ticket.getUserID()+"','"+ticket.getDepTime()+"','"+ ticket.getRouteID() +"','"+ ticket.getDepDate()+"','"+ticket.getFare()+"','"+ticket.getBusID()+"','"+TicketUtil.convertArrayListtoString(ticket.getSeatNums())+"','"+ticket.getNumSeats()+"')";
//			preparedStatement = conn.prepareStatement(insertToBookingHistoryQuery);
//			preparedStatement.setInt(1,ticket.getUserID());
//			preparedStatement.setString(2,ticket.getDepTime());
//			preparedStatement.setInt(3,ticket.getRouteID());
//			preparedStatement.setString(4,ticket.getDepDate());
//			preparedStatement.setInt(5,ticket.getFare());
//			preparedStatement.setInt(6,ticket.getBusID());
//			preparedStatement.setString(7,TicketUtil.convertArrayListtoString(ticket.getSeatNums()));
//			preparedStatement.setInt(8,ticket.getNumSeats());
			int r;
			Statement st = null;
			st = conn.createStatement();
			r = st.executeUpdate(insertIntoBookingHistory);
			//insert pnr into ticket
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery("select pnr from booking_history");
			resultSet.afterLast();
			if (resultSet.previous()) {
			  ticket.setPnr(resultSet.getInt("pnr"));
			}
			return ticket;

		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		finally
		{
			closeDBConn();
		}	

		return new Ticket();
	}

	@Override
	public boolean cancelTicket(int pnr) {
		// TODO Auto-generated method stub
		String selquery = "select * from booking_history where pnr = " + pnr;
		String delquery = "delete from booking_history where pnr = " + pnr;
		
//		String seat_tracker_select_query = "select * from seat_tracking where  "
		String journeyID = "";
		String newSeatsString;
		Ticket tick = new Ticket();
		try { 
			connectToDB();
			pst = conn.createStatement();
			rst = pst.executeQuery(selquery);
			if(rst.next())
			{
				tick.setBusID(rst.getInt("bus_id"));
				tick.setNumSeats(rst.getInt("num_of_seats"));
				tick.setSeatNums(TicketUtil.convertStringTicketsToArrayList(rst.getString("seat_nums")));
				tick.setDepDate(rst.getString("date"));
				tick.setDepTime(rst.getString("dep_time"));
				journeyID = TicketUtil.getJourneyID(tick);
			}
			if(journeyID==null)return true;
			System.out.println("JOURNEYID:"+journeyID);
//			PreparedStatement preparedStatement = conn.prepareStatement(deletequery);
//			preparedStatement.setInt(1,busID);
//			preparedStatement.executeUpdate();
			int r;
			Statement st = null;
			st = conn.createStatement();
			r = st.executeUpdate(delquery);
			//"SELECT seats_available FROM seat_tracking WHERE bus_id = " + ticket.getBusID() + " AND journey_id = '" + journeyID + "';";
			String seat_tracker_select_query = "SELECT seats_available FROM seat_tracking where bus_id = " + tick.getBusID() + " AND journey_id = " + journeyID;
			String currentSeatsString = "";
			pst = conn.createStatement();
			rst = pst.executeQuery(seat_tracker_select_query);
			if(rst.next())
			{
				currentSeatsString = rst.getString("seats_available");
			}
			newSeatsString = TicketUtil.getSeatsStringAfterCancellation(tick,currentSeatsString);
			System.out.println("NewSeats:"+newSeatsString);
			
			System.out.println("BusID:"+tick.getBusID()+" journeyid:"+journeyID);
			String updatequery = "UPDATE seat_tracking SET seats_available = " + newSeatsString + " WHERE bus_id = " + tick.getBusID() + " AND journey_id = " + journeyID;
			pst = conn.createStatement();
			pst.executeUpdate(updatequery);
			
			/*String seat_tracker_update_query = "UPDATE seat_tracking SET seats_available = " + newSeatsString + " WHERE bus_id = " + tick.getBusID() + " AND journey_id = " + journeyID;
			pst = conn.createStatement();
			pst.executeQuery(seat_tracker_select_query);*/
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			closeDBConn();
		}
		return false;
	}
	
	
	
	
	
	


}
