/**
 * 
 */
package com.migo.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.migo.api.domain.Organization;
import com.migo.service.OrganizationServiceImpl;

/**
 * @author orobsonpires Feb 8, 2019
 */
@RestController
@RequestMapping("api/organizations")
public class OrganizationController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	private OrganizationServiceImpl organizationService;

	@GetMapping("/public")
	public @ResponseBody ResponseEntity<Map<String, List<Organization>>> getAllOrganizations() {

		Map<String, List<Organization>> result = new HashMap<String, List<Organization>>();

		result.put("organizations", this.organizationService.getAllOrganizations());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
