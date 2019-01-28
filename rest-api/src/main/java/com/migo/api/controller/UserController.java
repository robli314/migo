package com.migo.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.migo.api.domain.ApplicationUser;
import com.migo.service.CustomUserDetailsService;

/**
 * 
 * @author orobsonpires Jan 25, 2019
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/private/{id}")
	public @ResponseBody ApplicationUser getUserById(@PathVariable String id) {

		LOG.debug(SecurityContextHolder.getContext().getAuthentication().toString());

		return customUserDetailsService.findUserById(id);

	}

}
