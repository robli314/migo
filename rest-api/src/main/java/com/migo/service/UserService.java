package com.migo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.migo.api.domain.User;
import com.migo.dao.IUserRepository;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	public UserService() {
		LOG.debug("User service was injected.");
	}

	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}
}