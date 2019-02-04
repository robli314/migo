package com.migo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.migo.api.domain.ApplicationUser;
import com.migo.dao.UsersRepositoty;
import com.migo.exception.RecordNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepositoty usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws RecordNotFoundException {

		
		ApplicationUser applicationUser = usersRepository.findByEmail(email).orElseThrow(
				() -> new RecordNotFoundException("Ops! It seems there's no user with that E-Mail: " + email));

		/*
		 * https://stackoverflow.com/questions/46999940/spring-boot-passwordencoder-
		 * error
		 */
		return new User(applicationUser.getEmail(), "{noop}" + applicationUser.getPassword(),
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public ApplicationUser findUserById(String id) throws RecordNotFoundException {

		ApplicationUser applicationUser = usersRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Ops! It seems there's no user with ID: " + id));

		return applicationUser;
	}

}
