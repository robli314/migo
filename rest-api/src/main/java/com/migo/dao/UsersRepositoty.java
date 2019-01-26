package com.migo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.migo.api.domain.ApplicationUser;

@Repository
public interface UsersRepositoty extends MongoRepository<ApplicationUser, String> {
	ApplicationUser findByUsername(String username);
}
