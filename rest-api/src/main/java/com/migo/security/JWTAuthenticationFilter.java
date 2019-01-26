
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
			ApplicationUser applicationUser = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);

			LOG.debug("Requested token for: " + applicationUser.getUsername());

			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));
		//TODO Is that the best way to handler exceptions?
		} catch (IOException e) {
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

		response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}

}