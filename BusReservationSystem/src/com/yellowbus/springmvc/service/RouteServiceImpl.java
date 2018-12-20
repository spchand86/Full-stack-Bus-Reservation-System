package com.yellowbus.springmvc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yellowbus.springmvc.model.Route;
import com.yellowbus.springmvc.util.RandomUtil;

@Service("routeService")
@Transactional
public class RouteServiceImpl implements RouteService {

	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/";
	String dbName = "bus_reservation_system2";
	String driver = "com.mysql.jdbc.Driver";
	String userName = "root";
	String password = "root";
	String strQuery = "";
	RandomUtil randomUtil = new RandomUtil();

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
	public ArrayList<Route> getAllRoutes() {
		try {
			connectToDB();
			ArrayList<Route> routeList = new ArrayList<Route>();
			strQuery = "SELECT * from route_master WHERE route_id % 2 = 1;";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			while (rs.next()) {
				Route temp = new Route();
				temp.setRouteID(rs.getInt(1));
				temp.setFromLocation(randomUtil.capitalizeFirstLetter(rs
						.getString(2)));
				temp.setToLocation(randomUtil.capitalizeFirstLetter(rs
						.getString(3)));
				temp.setDistance(rs.getInt(4));
				routeList.add(temp);
			}
			System.out.println("ALL ROUTES RETURNED!!");
			return routeList;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<Route> routeList = new ArrayList<Route>();
			Route rt = new Route();
			routeList.add(rt);
			return routeList;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean addRoute(Route route) {
		try {
			connectToDB();
			strQuery = "INSERT into route_master VALUES (NULL,'"
					+ route.getFromLocation() + "','" + route.getToLocation()
					+ "'," + route.getDistance() + " );";
			Statement statement = conn.createStatement();
			statement.executeUpdate(strQuery);
			// preparedStatement.setString(1, route.getFromLocation());
			// preparedStatement.setString(2, route.getToLocation());
			// preparedStatement.setInt(3, route.getDistance());
			// preparedStatement.executeUpdate();
			// preparedStatement.setString(1, route.getToLocation());
			// preparedStatement.setString(2, route.getFromLocation());
			// preparedStatement.setInt(3, route.getDistance());
			// preparedStatement.executeUpdate();
			strQuery = "INSERT into route_master VALUES (NULL,'"
					+ route.getToLocation() + "','" + route.getFromLocation()
					+ "'," + route.getDistance() + " );";
			statement = conn.createStatement();
			statement.executeUpdate(strQuery);
			System.out.println("Routes ADDED");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDBConn();
		}

	}

	@Override
	public Route getRouteByID(int routeID) {
		try {
			connectToDB();
			strQuery = "SELECT * from route_master WHERE route_id = " + routeID
					+ ";";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			rs.next();
			Route route = new Route();
			route.setRouteID(rs.getInt(1));
			route.setFromLocation(randomUtil.capitalizeFirstLetter(rs
					.getString(2)));
			route.setToLocation(randomUtil.capitalizeFirstLetter(rs
					.getString(3)));
			route.setDistance(rs.getInt(4));
			System.out.println("Route RETURNED by ID!!");
			return route;
		} catch (Exception e) {
			e.printStackTrace();
			Route route = new Route();
			return route;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean deleteRouteByID(int routeID) {
		try {
			connectToDB();
			strQuery = "DELETE from route_master where route_id = " + routeID
					+ ";";
			Statement statement = conn.createStatement();
			statement.executeUpdate(strQuery);
			strQuery = "DELETE from route_master where route_id = "
					+ (routeID + 1) + ";";
			statement.executeUpdate(strQuery);
			System.out.println("TWO ROUTES DELETED!!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDBConn();
		}

	}

	@Override
	public ArrayList<String> getUniqueCities() {
		try {
			connectToDB();
			strQuery = "SELECT DISTINCT from_loc FROM route_master;";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			ArrayList<String> fromCities = new ArrayList<String>();
			while (rs.next()) {
				fromCities
						.add(randomUtil.capitalizeFirstLetter(rs.getString(1)));
			}
			return fromCities;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> fromCities = new ArrayList<String>();
			return fromCities;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public ArrayList<String> getToLocs(String fromLoc) {
		try {
			connectToDB();
			strQuery = "SELECT DISTINCT to_loc FROM route_master WHERE from_loc = '"
					+ fromLoc + "';";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			ArrayList<String> toLocs = new ArrayList<String>();
			while (rs.next()) {
				toLocs.add(randomUtil.capitalizeFirstLetter(rs.getString(1)));
			}
			return toLocs;
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> toLocs = new ArrayList<String>();
			return toLocs;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public int getID(String fromLoc, String toLoc) {
		try {
			connectToDB();
			strQuery = "SELECT route_id FROM route_master WHERE from_loc = '"
					+ fromLoc + "' AND to_loc = '" + toLoc + "';";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			resultSet.next();
			return resultSet.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean isDuplicate(Route route) {
		try {
			connectToDB();
			strQuery = "SELECT * FROM route_master WHERE from_loc = '"
					+ route.getFromLocation() + "' AND to_loc = '"
					+ route.getToLocation() + "';";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean isDuplicate(int routeID) {
		try {
			connectToDB();
			strQuery = "SELECT * FROM route_master WHERE route_id = " + routeID
					+ ";";
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(strQuery);
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			closeDBConn();
		}
	}

}
