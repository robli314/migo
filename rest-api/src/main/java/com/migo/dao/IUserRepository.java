package com.migo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.migo.api.domain.User;

public interface IUserRepository extends MongoRepository<User, Long>{
	User findUserByName(String name);
}
