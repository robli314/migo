package com.migo.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.migo.api.domain.User;
import com.migo.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users")
@Api(tags = { "users" })
public class UserController {

	@Autowired
	private UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	public UserController() {

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	//@ResponseStatus(HttpStatus.OK) means the request will return OK, if the handling method returns normally. ( This
	// annotation is redundant in that cases, as the default response status is HttpStatus.OK)
	@ApiOperation(value = "Get a single user.", notes = "You have to provide a valid user ID.")
	public @ResponseBody User getUser(
			@ApiParam(value = "The ID of the user.", required = true) @PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		LOG.debug("Requested user with ID: " + id);
		
		return this.userService.getUser(id);
	}

	@RequestMapping("/hello")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return "Hi, there!";
	}

}
