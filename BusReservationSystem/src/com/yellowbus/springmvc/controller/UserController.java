package com.yellowbus.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.yellowbus.springmvc.model.*;
import com.yellowbus.springmvc.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/api/controllers/user/signup", method = RequestMethod.POST)
	public ResponseEntity<User> Signup(@RequestBody User user) {
		// Dummy Testing
		// user.setUserID(777);

		User user2 = userService.doesUserExist(user.getUserName());
		if (user2 == null) {
			User user3 = userService.addUser(user);
			return new ResponseEntity<User>(user3, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/api/controllers/user/login", method = RequestMethod.POST)
	public ResponseEntity<User> Login(@RequestBody User user) {
		// Dummy Testing
		// user.setUserID(777);
		User user2 = userService.doesUserExist(user.getUserName());
		if (user2 == null) {
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		} else if (!user.getPassword().equals(user2.getPassword())) {
			return new ResponseEntity<User>(user, HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<User>(user2, HttpStatus.OK);
		}
	}
}
