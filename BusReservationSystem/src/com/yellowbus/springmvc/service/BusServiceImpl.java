package com.yellowbus.springmvc.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.model.Bus;
import com.yellowbus.springmvc.util.RandomUtil;

@Service("busService")
@Transactional
public class BusServiceImpl implements BusService {

	private static ArrayList<Bus> buses;

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
	public ArrayList<Bus> getAllBus() {
		// TODO Auto-generated method stub

		connectToDB();
		Bus b = null;
		buses = new ArrayList<Bus>();
		try {
			// StringBuffer query=null;

			// String query = new StringBuffer("select * from user_details");
			String query = "select * from bus_master";
			// pst=conn.prepareStatement(query.toString());
			pst = conn.createStatement();
			rst = pst.executeQuery(query);

			while (rst.next()) {
				b = new Bus();

				b.setBusID(rst.getInt("bus_id"));
				b.setBusNum(rst.getString("bus_num").toUpperCase());
				b.setBusType(rst.getInt("bus_type"));
				b.setBusName(randomUtil.capitaliseFirstLetter(rst
						.getString("bus_name")));
				b.setTotalSeats(rst.getInt("total_seats"));
				b.setRouteID(rst.getInt("route_id"));
				buses.add(b);
				// u.setId(rst.getInt("user_seq_no"));
				// System.out.println("aaaaaaa "+u.getId());
				//
				// u.setUsername(rst.getString("username"));
				// u.setPassword(rst.getString("password"));
				// u.setFirstName(rst.getString("first_name"));
				// u.setLastName(rst.getString("last_name"));
				//
				// u.setAddress(rst.getString("address"));
				// u.setEmail(rst.getString("email_id"));
				// u.setPincode(rst.getString("pincode"));
				// users.add(u);
				// System.out.println("list is =>>>>"+users);
			}
			// users.add(u);
			// System.out.println("list is =>>>>"+users);
			// return users;
		} catch (Exception e) {
			System.out.println("Error in getting all buses");
			e.printStackTrace();
		} finally {
			// rst.close();
			// pst.close();
			closeDBConn();
		}

		return buses;
	}

	@Override
	public int addBus(Bus bus) {
		RandomUtil randomUtil = new RandomUtil();
		String query = "INSERT INTO bus_master (bus_num, bus_type, total_seats, bus_name) values ('"
				+ bus.getBusNum().toUpperCase()
				+ "',"
				+ bus.getBusType()
				+ ","
				+ bus.getTotalSeats()
				+ ",'"
				+ randomUtil.capitaliseFirstLetter(bus.getBusName()) + "')";
		String query2 = "SELECT bus_id from bus_master where bus_num="
				+ bus.getBusNum();

		int busId;
		try {
			connectToDB();
			int r;
			Statement st = null;
			st = conn.createStatement();
			r = st.executeUpdate(query);
			// rst = st.executeQuery(query2);
			// busId = rst.getInt("bus_id");
			// return busId;

			Statement statement = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement
					.executeQuery("select bus_id from bus_master");
			resultSet.afterLast();
			if (resultSet.previous()) {
				busId = resultSet.getInt("bus_id");
				return busId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConn();
		}
		return -1;
	}

	@Override
	public Bus getBusByID(int busId) {
		// TODO Auto-generated method stub

		connectToDB();
		Bus bus = new Bus();
		String sql = "SELECT * FROM bus_master WHERE bus_id=" + busId;
		try {
			pst = conn.createStatement();
			rst = pst.executeQuery(sql);

			rst.next();
			bus.setBusID(rst.getInt("bus_id"));
			bus.setBusNum(rst.getString("bus_num").toUpperCase());
			bus.setBusType(rst.getInt("bus_type"));
			bus.setBusName(randomUtil.capitaliseFirstLetter(rst
					.getString("bus_name")));
			bus.setTotalSeats(rst.getInt("total_seats"));
			bus.setRouteID(rst.getInt("route_id"));
			// bus.setAuthor(rst.getString("author"));
			// book.setPublication(rst.getString("publication"));
			// book.setTitle(rst.getString("title"));
			// book.setIsbn(rst.getString("isbn"));
			// book.setPrice(rst.getInt("price"));
			// book.setGenre(rst.getString("genre"));
			// book.setStock(rst.getInt("stock"));
			// book.setBook_seq_no(rst.getInt("book_seq_no"));
			return bus;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeDBConn();
		return null;
	}

	@Override
	public boolean removeBus(int busID) {
		String query = "delete from bus_master where bus_id = " + busID;
		// String deletequery = "delete from bus_master where bus_id = ?";
		try {
			connectToDB();
			// PreparedStatement preparedStatement =
			// conn.prepareStatement(deletequery);
			// preparedStatement.setInt(1,busID);
			// preparedStatement.executeUpdate();
			int r;
			Statement st = null;
			st = conn.createStatement();
			r = st.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConn();
		}
		return false;
	}

}
