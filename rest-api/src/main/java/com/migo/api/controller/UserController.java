package com.migo.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author orobsonpires Jan 25, 2019
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/private/{id}")
	public ResponseEntity<String> getUserById(@RequestBody String id) {

		// log information regarding authenticated user
		LOG.debug(SecurityContextHolder.getContext().getAuthentication().toString());

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
