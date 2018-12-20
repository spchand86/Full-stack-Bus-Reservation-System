package com.yellowbus.springmvc.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.model.BusType;
import com.yellowbus.springmvc.util.RandomUtil;

@Service("bustypeService")
@Transactional
public class BusTypeServiceImpl implements BusTypeService {

	private static ArrayList<BusType> bustypes;

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
	public ArrayList<BusType> getAllBusType() {
		connectToDB();
		BusType bustype = null;
		bustypes = new ArrayList<BusType>();
		try {
			// StringBuffer query=null;

			// String query = new StringBuffer("select * from user_details");
			String query = "select * from bus_types";
			// pst=conn.prepareStatement(query.toString());
			pst = conn.createStatement();
			rst = pst.executeQuery(query);

			while (rst.next()) {
				bustype = new BusType();
				bustype.setBusTypeID(rst.getInt("bus_type_id"));
				bustype.setBusTypeName(rst.getString("bus_type").toUpperCase());
				bustype.setSpeed(rst.getInt("speed"));
				bustype.setAc(rst.getBoolean("ac"));
				bustype.setFarePerKM(rst.getInt("fare_per_km"));
				bustypes.add(bustype);
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

		return bustypes;
	}

	@Override
	public BusType getBusTypebyID(int busTypeID) {
		// TODO Auto-generated method stub
		connectToDB();
		BusType bustype = new BusType();
		String sql = "SELECT * FROM bus_types WHERE bus_type_id=" + busTypeID;
		try {
			pst = conn.createStatement();
			rst = pst.executeQuery(sql);

			rst.next();
			bustype.setBusTypeID(rst.getInt("bus_type_id"));
			bustype.setBusTypeName(rst.getString("bus_type").toUpperCase());
			bustype.setSpeed(rst.getInt("speed"));
			bustype.setAc(rst.getBoolean("ac"));
			bustype.setFarePerKM(rst.getInt("fare_per_km"));
			// bus.setAuthor(rst.getString("author"));
			// book.setPublication(rst.getString("publication"));
			// book.setTitle(rst.getString("title"));
			// book.setIsbn(rst.getString("isbn"));
			// book.setPrice(rst.getInt("price"));
			// book.setGenre(rst.getString("genre"));
			// book.setStock(rst.getInt("stock"));
			// book.setBook_seq_no(rst.getInt("book_seq_no"));
			return bustype;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public int addBusTypeID(BusType bustype) {
		int isac = bustype.isAc() ? 1 : 0;
		String query = "INSERT INTO bus_types (bus_type, speed, ac, fare_per_km) values ('"
				+ bustype.getBusTypeName()
				+ "','"
				+ bustype.getSpeed()
				+ "','"
				+ isac + "','" + bustype.getFarePerKM() + "')";
		// String query2 = "SELECT bus_id from bus_master where bus_num=" +
		// bustype.getBusNum();

		int bustypeId;
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
					.executeQuery("select bus_type_id from bus_types");
			resultSet.afterLast();
			if (resultSet.previous()) {
				bustypeId = resultSet.getInt("bus_type_id");
				return bustypeId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConn();
		}
		return -1;
	}

	@Override
	public boolean removeBusType(int busTypeID) {
		String query = "delete from bus_types where bus_type_id = " + busTypeID;
		String deletequery = "delete from bus_types where bus_type_id = ?";
		try {
			connectToDB();
			// PreparedStatement preparedStatement =
			// conn.prepareStatement(deletequery);
			// preparedStatement.setInt(1,busTypeID);
			// preparedStatement.executeQuery();
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
