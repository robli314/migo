package com.migo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.migo.api.domain.User;

/**
 * 
 * @author orobsonpires
 * Jan 25, 2019
 */
public interface IUserRepository extends MongoRepository<User, Long>{
	User findUserByName(String name);
}
