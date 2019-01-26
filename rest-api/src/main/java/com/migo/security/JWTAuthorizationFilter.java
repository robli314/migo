package com.migo.security;

import static com.migo.security.SecurityConstants.HEADER_STRING;
import static com.migo.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.migo.service.CustomUserDetailsService;

/**
 * 
 * @author orobsonpires Jan 25, 2019
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	//private final IUserService userService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService userService) {
		super(authenticationManager);
		/*this.userService = userService;*/
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER_STRING);
		
		if(header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
	}
	
	//private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
		
	//}

}
