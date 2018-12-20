package com.yellowbus.springmvc.service;

import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Connection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.model.Bus;
import com.yellowbus.springmvc.model.BusSchedule;
import com.yellowbus.springmvc.model.BusType;
import com.yellowbus.springmvc.model.Calculator;
import com.yellowbus.springmvc.model.Route;
import com.yellowbus.springmvc.model.SearchResult;
import com.yellowbus.springmvc.util.TicketUtil;

@Service("scheduleService")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "bus_reservation_system2";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root";
	String strQuery = "";

	private void connectToDB() {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager
					.getConnection(url + dbName, userName, password);
		} catch (Exception ex) {

			System.out
					.println("CandidateSchemeDAO Error: could not connect to db");
			System.out.println("- Exception: " + ex.toString());
		}
	}

	public void closeDBConn() {
		try {
			if (!conn.isClosed())
				conn.close();
		} catch (Exception ex) {

			System.out.println("- Exception: " + ex.toString());
		}
	}

	@Override
	public ArrayList<SearchResult> search(int routeID, String date) {
		try {
			connectToDB();
			StringBuilder stringBuilder = new StringBuilder(date);
			stringBuilder.insert(2, "/");
			stringBuilder.insert(5, "/");
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dt1 = format1.parse(stringBuilder.toString());
			DateFormat format2 = new SimpleDateFormat("EEEE");
			String day = format2.format(dt1).toLowerCase();
			strQuery = "SELECT * FROM schedule WHERE route_id = " + routeID
					+ " AND day = '" + day + "';";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			ArrayList<SearchResult> list = new ArrayList<SearchResult>();
			while (resultSet.next()) {
				SearchResult searchResult = new SearchResult();
				searchResult.setDepTime(resultSet.getString(3));
				searchResult.setDuration(resultSet.getInt(4));
				searchResult.setFare(resultSet.getInt(7));
				String journeyID = date.substring(0, 4)
						+ resultSet.getString(3);
				System.out.println(journeyID);
				int busID = resultSet.getInt(6);
				strQuery = "SELECT * FROM bus_master WHERE bus_id = " + busID
						+ ";";
				Statement statement1 = conn.createStatement();
				ResultSet resultSet1 = statement1.executeQuery(strQuery);
				Bus bus = new Bus();
				resultSet1.next();
				bus.setBusID(resultSet1.getInt(1));
				bus.setBusNum(resultSet1.getString(2));
				bus.setBusType(resultSet1.getInt(3));
				bus.setTotalSeats(resultSet1.getInt(4));
				bus.setBusName(resultSet1.getString(5));
				bus.setRouteID(resultSet1.getInt(6));
				searchResult.setBus(bus);
				System.out.println("aaaa");
				strQuery = "SELECT seats_available FROM seat_tracking WHERE bus_id = "
						+ busID + " AND journey_id = '" + journeyID + "';";
				Statement statement2 = conn.createStatement();
				ResultSet resultSet2 = statement2.executeQuery(strQuery);
				resultSet2.next();
				String seatsString = resultSet2.getString(1);
				searchResult.setRemainingSeats(TicketUtil
						.getSeatsAvailable(seatsString));
				list.add(searchResult);
				System.out.println("bbb"+searchResult);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<SearchResult> list = new ArrayList<SearchResult>();
			System.out.println("error occured in scheduleserviceimpl");
			return list;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public ArrayList<SearchResult> search(int routeID, String date,
			int busTypeID) {
		try {
			connectToDB();
			StringBuilder stringBuilder = new StringBuilder(date);
			stringBuilder.insert(2, "/");
			stringBuilder.insert(5, "/");
			SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
			Date dt1 = format1.parse(stringBuilder.toString());
			DateFormat format2 = new SimpleDateFormat("EEEE");
			String day = format2.format(dt1);
			strQuery = "SELECT * FROM schedule WHERE route_id = " + routeID
					+ " AND day = '" + day + "' AND bus_type = " + busTypeID
					+ ";";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			ArrayList<SearchResult> list = new ArrayList<SearchResult>();
			while (resultSet.next()) {
				SearchResult searchResult = new SearchResult();
				searchResult.setDepTime(resultSet.getString(3));
				searchResult.setDuration(resultSet.getInt(4));
				searchResult.setFare(resultSet.getInt(7));
				String journeyID = date.substring(0, 4)
						+ resultSet.getString(3);
				System.out.println(journeyID);
				int busID = resultSet.getInt(6);
				strQuery = "SELECT * FROM bus_master WHERE bus_id = " + busID
						+ ";";
				Statement statement1 = conn.createStatement();
				ResultSet resultSet1 = statement1.executeQuery(strQuery);
				Bus bus = new Bus();
				resultSet1.next();
				bus.setBusID(resultSet1.getInt(1));
				bus.setBusNum(resultSet1.getString(2));
				bus.setBusType(resultSet1.getInt(3));
				bus.setTotalSeats(resultSet1.getInt(4));
				bus.setBusName(resultSet1.getString(5));
				bus.setRouteID(resultSet1.getInt(6));
				searchResult.setBus(bus);
				strQuery = "SELECT seats_available FROM seat_tracking WHERE bus_id = "
						+ busID + " AND journey_id = '" + journeyID + "';";
				Statement statement2 = conn.createStatement();
				ResultSet resultSet2 = statement2.executeQuery(strQuery);
				resultSet2.next();
				String seatsString = resultSet2.getString(1);
				searchResult.setRemainingSeats(TicketUtil
						.getSeatsAvailable(seatsString));
				list.add(searchResult);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<SearchResult> list = new ArrayList<SearchResult>();
			return list;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public BusSchedule viewBusSchedule(int busID) {
		try {
			connectToDB();
			BusSchedule busSchedule = new BusSchedule();
			strQuery = "SELECT * FROM schedule WHERE bus_id = " + busID + " ;";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			resultSet.next();
			busSchedule.setRouteID(resultSet.getInt(1));
			busSchedule.setDuration(resultSet.getInt(4));
			busSchedule.setFare(resultSet.getInt(7));
			busSchedule.setBusID(resultSet.getInt(6));
			ArrayList<ArrayList<String>> depTimings = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < 7; i++) {
				depTimings.add(new ArrayList<String>());
			}
			do {
				String depTime = resultSet.getString(3);
				String day = resultSet.getString(2);
				// System.out.println("Day : " + day + "\nDeparture Time : "
				// + depTime);
				if (day.equals("sunday")) {
					depTimings.get(0).add(depTime);
				} else if (day.equals("monday")) {
					depTimings.get(1).add(depTime);
				} else if (day.equals("tuesday")) {
					depTimings.get(2).add(depTime);
				} else if (day.equals("wednesday")) {
					depTimings.get(3).add(depTime);
				} else if (day.equals("thursday")) {
					depTimings.get(4).add(depTime);
				} else if (day.equals("friday")) {
					depTimings.get(5).add(depTime);
				} else if (day.equals("saturday")) {
					depTimings.get(6).add(depTime);
				}
			} while (resultSet.next());
			busSchedule.setDepTimings(depTimings);
			return busSchedule;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean addBusSchedule(BusSchedule busSchedule) {
		try {
			connectToDB();
			strQuery = "SELECT bus_type FROM bus_master WHERE bus_id = "
					+ busSchedule.getBusID() + ";";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			resultSet.next();
			int busType = resultSet.getInt(1);
			for (int i = 0; i < busSchedule.getDepTimings().size(); i++) {
				String day = "";
				if (i == 0) {
					day = "sunday";
				} else if (i == 1) {
					day = "monday";
				} else if (i == 2) {
					day = "tuesday";
				} else if (i == 3) {
					day = "wednesday";
				} else if (i == 4) {
					day = "thursday";
				} else if (i == 5) {
					day = "friday";
				} else if (i == 6) {
					day = "saturday";
				}
				for (int j = 0; j < busSchedule.getDepTimings().get(i).size(); j++) {
					strQuery = "INSERT INTO schedule VALUES("
							+ busSchedule.getRouteID() + ", '" + day + "', '"
							+ busSchedule.getDepTimings().get(i).get(j) + "', "
							+ busSchedule.getDuration() + ", " + busType + ", "
							+ busSchedule.getBusID() + ", "
							+ busSchedule.getFare() + ");";
					statement.executeUpdate(strQuery);
				}
			}
			strQuery = "UPDATE bus_master SET route_id = "
					+ busSchedule.getRouteID() + " WHERE bus_id = "
					+ busSchedule.getBusID() + ";";
			statement.executeUpdate(strQuery);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public int getRouteID(String fromLoc, String toLoc) {
		try {
			connectToDB();
			strQuery = "SELECT route_id FROM route_master WHERE from_loc = '"
					+ fromLoc + "' AND to_loc = '" + toLoc + "';";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			closeDBConn();
		}
	}

}
