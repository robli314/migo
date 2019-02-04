
package com.migo.security;

import static com.migo.security.SecurityUtils.EXPIRATION_TIME;
import static com.migo.security.SecurityUtils.HEADER_STRING;
import static com.migo.security.SecurityUtils.SECRET;
import static com.migo.security.SecurityUtils.TOKEN_PREFIX;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.migo.api.domain.ApplicationUser;
import com.migo.exception.RecordNotFoundException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This filter class will be used to protect our 'secure' endpoints, and it was
 * based on https://aboullaite.me/spring-boot-token-authentication-using-jwt/
 * 
 * @author orobsonpires Jan 25, 2019
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(),
					ApplicationUser.class);

			LOG.debug("Requested token for: " + applicationUser.getEmail());

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(applicationUser.getEmail(), applicationUser.getPassword()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			/*
			 * For while I keep that solution until find better. The issue is, the provider
			 * in Spring security is handler my RecordNotFoundException and re-thrown as
			 * InternalAuthenticationServiceException and I am not able to catch that
			 * exception in my CustomezedResponseEntityExcep, in order to delivery a better
			 * error response to the client, instead of only delivery some kind of generic
			 * server error.
			 * 
			 * The question was already raised 2+ in stackoverflow, but it seems there not
			 * really good solution for that situation.
			 * 
			 * Links:
			 * https://stackoverflow.com/questions/39789408/how-to-handle-spring-security-
			 * internalauthenticationserviceexception-thrown-in-s?answertab=votes#tab-top
			 * 
			 * https://stackoverflow.com/questions/54521967/not-able-to-handler-
			 * internalauthenticationserviceexception-in-spring-security
			 */
			if (e.getCause() instanceof RecordNotFoundException) {
				throw new RecordNotFoundException(e.getMessage());
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

		String token = Jwts.builder().setSubject(((User) authResult.getPrincipal()).getUsername())
				.setExpiration(Date.from(expirationTimeUTC.toInstant())).signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();

		LOG.debug("JWT generated: " + token);

		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

}