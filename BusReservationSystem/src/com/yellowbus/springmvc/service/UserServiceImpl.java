package com.yellowbus.springmvc.service;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.Connection;
import com.yellowbus.springmvc.model.User;
import com.yellowbus.springmvc.util.RandomUtil;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
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
			conn = (Connection) DriverManager.getConnection(url + dbName,
					userName, password);
			// conn.setReadOnly(false);
			// conn.setAutoCommit(true);
			// conn.setCachePreparedStatements(true);
			// conn.setcach
			// conn.setPreparedStatementCacheSize(250);
			// conn.setPreparedStatementCacheSqlLimit(2048);
			// conn.setUseServerPreparedStmts(true);
			// conn.setUseServerPrepStmts(true);
			// conn.setCachePrepStmts(true);
			// conn.setPrepStmtCacheSize(250);
			// conn.setPrepStmtCacheSqlLimit(2048);
			// conn.setUseServerPrepStmts(false);
			// conn.setUseServerPreparedStmts(false);
			// conn.setCachePreparedStatements(true);
			// conn.setPreparedStatementCacheSqlLimit(2048);
			// conn.setPreparedStatementCacheSize(250);
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
	public User addUser(User user) {
		try {
			connectToDB();
			strQuery = "INSERT INTO user VALUES(NULL, '" + user.getUserName()
					+ "', '" + user.getPassword() + "','"
					+ randomUtil.capitaliseFirstLetter(user.getName()) + "');";
			Statement statement = conn.createStatement();
			// PreparedStatement preparedStatement = conn
			// .prepareStatement(strQuery);
			// preparedStatement.setString(1, user.getUserName());
			// preparedStatement.setString(2, user.getPassword());
			// preparedStatement.setString(3,
			// randomUtil.capitaliseFirstLetter(user.getName()));
			statement.executeUpdate(strQuery);
			strQuery = "SELECT * FROM user;";
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			rs.afterLast();
			rs.previous();
			user.setUserID(rs.getInt(1));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return new User();
		} finally {
			closeDBConn();
		}
	}

	@Override
	public boolean deleteUserByID(int userID) {
		try {
			connectToDB();
			strQuery = "DELETE from user WHERE user_id = " + userID + ";";
			Statement statement = conn.createStatement();
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
	public List<User> showAllUsers() {
		try {
			connectToDB();
			List<User> userList = new ArrayList<User>();
			strQuery = "SELECT * from user;";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			while (rs.next()) {
				User temp = new User();
				temp.setUserID(rs.getInt(1));
				temp.setUserName(rs.getString(2));
				temp.setPassword(rs.getString(3));
				temp.setName(randomUtil.capitaliseFirstLetter(rs.getString(4)));
				userList.add(temp);
			}
			closeDBConn();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			List<User> userList = new ArrayList<User>();
			return userList;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public User showUserByID(int userID) {
		try {
			connectToDB();
			strQuery = "SELECT * from user WHERE user_id = " + userID + ";";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			rs.next();
			User user = new User();
			user.setUserID(rs.getInt(1));
			user.setUserName(rs.getString(2));
			user.setPassword(rs.getString(3));
			user.setName(randomUtil.capitaliseFirstLetter(rs.getString(4)));
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			User user = new User();
			return user;
		} finally {
			closeDBConn();
		}
	}

	@Override
	public User doesUserExist(String userName) {
		try {
			connectToDB();
			strQuery = "SELECT * FROM user WHERE username = '" + userName
					+ "';";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(strQuery);
			if (rs.next()) {
				return new User(rs.getInt(1), rs.getString(3), userName,
						randomUtil.capitaliseFirstLetter(rs.getString(4)));
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			closeDBConn();
		}
	}

}
