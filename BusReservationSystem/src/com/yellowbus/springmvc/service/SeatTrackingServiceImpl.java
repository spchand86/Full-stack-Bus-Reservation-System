package com.yellowbus.springmvc.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.util.RandomUtil;

@Service("seatTrackingService")
@Transactional
public class SeatTrackingServiceImpl implements SeatTrackingService {

	public static String days[] = { "sunday", "monday", "tuesday", "wednesday",
			"thursday", "friday", "saturday" };

	public ArrayList<TrackerClass> tcs = new ArrayList<TrackerClass>();
	private java.sql.Connection conn = null;

	// private java.sql.Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "bus_reservation_system2";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root";
	String strQuery = "";
	Statement pst = null;
	// PreparedStatement pst=null;
	ResultSet rst = null;
	RandomUtil randomUtil = new RandomUtil();

	// PreparedStatement pst=null;
	// ResultSet rst=null;

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
	public boolean insertnewSchedule() {
		// TODO Auto-generated method stub
		// LocalDate today = LocalDate.now();
		// LocalDate endDate = today.plusDays(10);
		connectToDB();
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		TrackerClass tc;
		try {

			for (int i = 0; i < 16; i++) {
				// String day = date.getDayOfWeek().name();
				// day = day.toLowerCase();

				cal.add(Calendar.DAY_OF_MONTH, 1);
				// System.out.println(cal.toString());
				// String selectquery

				// StringBuffer query=null;

				// String query = new
				// StringBuffer("select * from user_details");
				String dayname = days[cal.get(Calendar.DAY_OF_WEEK) - 1];
				String dayofMonth = Integer.toString(cal
						.get(Calendar.DAY_OF_MONTH));
				String month = Integer.toString(cal.get(Calendar.MONTH) + 1);

				if (dayofMonth.length() == 1) {
					dayofMonth = "0" + dayofMonth;
				}

				if (month.length() == 1) {
					month = "0" + month;
				}
				String depdate = dayofMonth + month;
				String query = "select * from schedule where day = " + "'"
						+ dayname + "'";
				pst = conn.createStatement();
				rst = pst.executeQuery(query);
				int busid;
				String deptime;
				String journeyid = "";

				while (rst.next()) {
					busid = rst.getInt("bus_id");
					deptime = rst.getString("dep_time");
					journeyid = depdate + deptime;
					tc = new TrackerClass();
					tc.setBusid(busid);
					tc.setJourneyid(journeyid);
					tcs.add(tc);
					// b = new Bus();
					//
					// b.setBusID(rst.getInt("bus_id"));
					// b.setBusNum(rst.getString("bus_num").toUpperCase());
					// b.setBusType(rst.getInt("bus_type"));
					// b.setBusName(randomUtil.capitaliseFirstLetter(rst.getString("bus_name")));
					// b.setTotalSeats(rst.getInt("total_seats"));
					// b.setRouteID(rst.getInt("route_id"));
					// buses.add(b);
				}

				for (TrackerClass tracker : tcs) {
					String squery = "select * from seat_tracking where bus_id = "
							+ tracker.getBusid()
							+ " and journey_id = "
							+ tracker.getJourneyid();

					pst = conn.createStatement();
					rst = pst.executeQuery(squery);
					if (!rst.next()) {
						String insquery;
						int totalseats = 0;
						String seats = "";
						String seatsquery = "select total_seats from bus_master where bus_id = "
								+ tracker.getBusid();
						pst = conn.createStatement();
						rst = pst.executeQuery(seatsquery);
						if (rst.next()) {
							totalseats = rst.getInt("total_seats");
						}
						for (int ii = 0; ii < totalseats; ii++) {
							seats += "0";
						}
						insquery = "INSERT INTO seat_tracking (bus_id, journey_id, seats_available) values ('"
								+ tracker.getBusid()
								+ "','"
								+ tracker.getJourneyid() + "','" + seats + "')";
						int r;
						Statement st = null;
						st = conn.createStatement();
						r = st.executeUpdate(insquery);
					}
				}
				// users.add(u);
				// System.out.println("list is =>>>>"+users);
				// return users;

			}
			return true;
		} catch (Exception e) {
			System.out.println("Error in getting all schedule");
			e.printStackTrace();
			return false;
		} finally {
			// rst.close();
			// pst.close();
			closeDBConn();

		}
	}
	
	@Override
	public boolean initializeSeatTracker()
	{
		connectToDB();
		Date today = new Date();
		Date lastUpDate = new Date();
		boolean success=false;
		try {
			String query = "SELECT last_updated FROM extra_info";
			pst = conn.createStatement();
			rst = pst.executeQuery(query);
			if(rst.next())
			{
				lastUpDate=rst.getDate("last_updated");
				if(today.after(lastUpDate))
				{
					System.out.println("Initializing Seat Tracker");
					success = insertnewSchedule();
					if(success)
					{
						connectToDB();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						String tdate=simpleDateFormat.format(today);
						System.out.println("upDATE:"+tdate);
						query = "UPDATE extra_info SET last_updated = '" + tdate + "' WHERE id = 1";
						Statement pst1 = conn.createStatement();
						pst1.executeUpdate(query);
					}
				}
			}
			return success;
		} catch (Exception e) {
			System.out.println("Error in initializing");
			e.printStackTrace();
			return false;
		} finally
		{
			closeDBConn();
		}
	}
}

class TrackerClass {
	int busid;
	String journeyid;

	public int getBusid() {
		return busid;
	}

	public void setBusid(int busid) {
		this.busid = busid;
	}

	public String getJourneyid() {
		return journeyid;
	}

	public void setJourneyid(String journeyid) {
		this.journeyid = journeyid;
	}
}
