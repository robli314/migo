package com.migo.service;

import com.migo.api.domain.User;

public interface IUserService {
	
	User getUser(Long id);
	
	User findByName(String name);
}
