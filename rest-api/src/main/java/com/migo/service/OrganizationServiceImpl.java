/**
 * 
 */
package com.migo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migo.api.domain.Organization;
import com.migo.dao.IOrganizationRepository;

/**
 * @author orobsonpires Feb 8, 2019
 */
@Service
public class OrganizationServiceImpl implements IOrganizationService {

	@Autowired
	IOrganizationRepository organizationRepository;
	
	@Override
	public List<Organization> getAllOrganizations() {
	
		return this.organizationRepository.findAll();
	}

}
