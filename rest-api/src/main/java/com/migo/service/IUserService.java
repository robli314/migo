package com.migo.service;

import com.migo.api.domain.User;

/**
 * 
 * @author orobsonpires Jan 25, 2019
 */
public interface IUserService {

	User getUser(Long id);

	User findByName(String name);
}
