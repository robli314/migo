package com.migo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.migo.api.domain.ApplicationUser;
import com.migo.dao.UsersRepositoty;
import com.migo.exception.RecordNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepositoty usersRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ApplicationUser applicationUser = usersRepository.findByUsername(username);

		/*
		 * https://stackoverflow.com/questions/46999940/spring-boot-passwordencoder-
		 * error
		 */
		return new User(applicationUser.getUsername(), "{noop}" + applicationUser.getPassword(),
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	public ApplicationUser findUserById(String id) throws RecordNotFoundException {

		Optional<ApplicationUser> applicationUser = usersRepository.findById(id);

		if (!applicationUser.isPresent()) {
			throw new RecordNotFoundException("Ops! It seems there's no user with ID: " + id);
		}

		return applicationUser.get();
	}

}
