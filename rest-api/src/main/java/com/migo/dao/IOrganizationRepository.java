/**
 * 
 */
package com.migo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.migo.api.domain.Organization;

/**
 * @author orobsonpires Feb 8, 2019
 */
public interface IOrganizationRepository extends MongoRepository<Organization, String> {

}
