package com.yellowbus.springmvc.service;

import java.util.List;

import com.yellowbus.springmvc.model.User;

public interface UserService {
	User doesUserExist(String userName);

	User addUser(User user);

	boolean deleteUserByID(int userID);

	List<User> showAllUsers();

	User showUserByID(int userID);

}
