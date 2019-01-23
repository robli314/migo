package com.migo.service;

import org.springframework.stereotype.Service;

import com.migo.api.domain.User;

@Service
public class UserService {

	public UserService() {
		
	}

	public User getUser(Long id) {
		
		return new User(23L, "Rob", "123");
	}

}
