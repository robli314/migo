package com.migo.security;

import static com.migo.security.SecurityUtils.HEADER_STRING;
import static com.migo.security.SecurityUtils.SECRET;
import static com.migo.security.SecurityUtils.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.migo.service.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;

/**
 * 
 * @author orobsonpires Jan 25, 2019
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final CustomUserDetailsService customUserDetailsService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
			CustomUserDetailsService customUserDetailsService) {
		super(authenticationManager);
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(request));
		
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
				.getSubject();

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
