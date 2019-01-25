package com.migo.api.controller;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.migo.api.domain.User;
import com.migo.service.IUserService;
import com.migo.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users")
@Api(tags = { "users" })
public class UserController {

	@Autowired
	private IUserService userService;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@RequestMapping(value = "/secure/{id}", method = RequestMethod.GET, produces = { "application/json" })
	// @ResponseStatus(HttpStatus.OK) means the request will return OK, if the
	// handling method returns normally. ( This
	// annotation is redundant in that case, as the default response status is
	// HttpStatus.OK)
	@ApiOperation(value = "Get a single user.", notes = "You have to provide a valid user ID.")
	public @ResponseBody User getUser(
			@ApiParam(value = "The ID of the user.", required = true) @PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) throws ServletException {

		LOG.debug("Requested user with ID: " + id);

		return this.userService.getUser(id);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody User login) throws ServletException {

		LOG.debug("Requested login with for: " + login.getName());

		String jwtToken = "";

		if (login.getName() == null || login.getPassword() == null) {
			throw new ServletException("Please fill in name and password");
		}

		String name = login.getName();
		String password = login.getPassword();

		User user = userService.findByName(name);

		if (user == null) {
			throw new ServletException("User name not found.");
		}

		String pwd = user.getPassword();

		if (!password.equals(pwd)) {
			throw new ServletException("Invalid login. Please check your name and password.");
		}

		jwtToken = Jwts.builder().setSubject(name).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

		return jwtToken;
	}
}
