package com.yellowbus.springmvc.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import com.yellowbus.springmvc.model.*;
import com.yellowbus.springmvc.service.RouteService;

@RestController
public class RouteController {
	@Autowired
	RouteService routeService;

	@RequestMapping(value = "/api/services/route/end", method = RequestMethod.POST)
	public ResponseEntity<Integer> addRoute(@RequestBody Route route) {
		// Dummy Testing
		// route.setRouteID(862);
		// System.out.println(route);
		boolean duplicate = routeService.isDuplicate(route);
		if (duplicate) {
			return new ResponseEntity<Integer>(-1, HttpStatus.CONFLICT);
		}
		boolean added = routeService.addRoute(route);
		if (!added) {
			return new ResponseEntity<Integer>(-1, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Integer>(routeService.getID(
				route.getFromLocation(), route.getToLocation()), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/route/end", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<Route>> getallRoute() {
		// Dummy Testing
		// ArrayList<Route> list=new ArrayList<Route>();
		// Route route=new Route();
		// route.setDistance(78);
		// route.setFromLocation("Chennai");
		// route.setToLocation("Kochi");
		// route.setRouteID(4545);
		// list.add(route);
		// route=new Route();
		// route.setDistance(36);
		// route.setFromLocation("Madras");
		// route.setToLocation("Cochin");
		// route.setRouteID(74);
		// list.add(route);
		ArrayList<Route> list = routeService.getAllRoutes();
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<Route>>(list,
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<Route>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/route/end/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRoute(@PathVariable("id") int routeID) {
		// Dummy Testing
		// System.out.println(routeID);
		boolean duplicate = routeService.isDuplicate(routeID);
		if (!duplicate) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		boolean deleted = routeService.deleteRouteByID(routeID);
		if (!deleted) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/route/end/{id}", method = RequestMethod.GET)
	public ResponseEntity<Route> getRoute(@PathVariable("id") int routeID) {
		// Dummy Testing
		// System.out.println(routeID);
		// Route route = new Route();
		// route.setDistance(78);
		// route.setFromLocation("Chennai");
		// route.setToLocation("Kochi");
		// route.setRouteID(4545);
		boolean duplicate = routeService.isDuplicate(routeID);
		Route route = routeService.getRouteByID(routeID);
		if (!duplicate) {
			return new ResponseEntity<Route>(route, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Route>(route, HttpStatus.OK);

	}

	@RequestMapping(value = "/api/services/route/getunq", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getUniqueRoute() {
		// Dummy Testing
		// String list[] = { "bla77h", "againblah", "Maire" };
		ArrayList<String> list = routeService.getUniqueCities();
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<String>>(list,
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/route/getunq/{fromLocation}", method = RequestMethod.GET)
	public ResponseEntity<ArrayList<String>> getToRoute(
			@PathVariable("fromLocation") String fromLocation) {
		// Dummy Testing
		// String list[] = { "bla22h", "ag222ainblah" };
		// System.out.println(fromLocation);
		ArrayList<String> list = routeService.getToLocs(fromLocation);
		if (list.isEmpty()) {
			return new ResponseEntity<ArrayList<String>>(list,
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArrayList<String>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/services/route/getunq/{fromLocation}/{toLocation}", method = RequestMethod.GET)
	public ResponseEntity<Integer> getRouteID(
			@PathVariable("fromLocation") String fromLocation,
			@PathVariable("toLocation") String toLocation) {
		// Dummy Testing
		// String list[] = { "bl44a22h", "ag222a666inblah" };
		// System.out.println(fromLocation + toLocation);
		int routeID = routeService.getID(fromLocation, toLocation);
		if (routeID == -1) {
			return new ResponseEntity<Integer>(routeID, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Integer>(routeID, HttpStatus.OK);
	}

}
