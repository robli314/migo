package com.migo.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.migo.api.domain.ApplicationUser;

@Repository
public interface IUsersRepositoty extends MongoRepository<ApplicationUser, String> {
	Optional<ApplicationUser> findByEmail(String email);
}
