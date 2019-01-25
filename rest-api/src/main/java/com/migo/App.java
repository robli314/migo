/**
 * This class is the main Spring Boot application class.
 */
package com.migo;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.migo.security.JwtFilter;


@SpringBootApplication
public class App extends SpringBootServletInitializer {

	/*
	 * 
	 * Best way to initialize Logger discussion: https://bit.ly/2Hs64QJ
	 * 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	// used when launching a executable jar or war
	public static void main(String[] args) {
		LOG.debug("The application is starting!");
		SpringApplication.run(App.class, args);
	}

	// used when deploy to a standalone servlet container
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(App.class);
	}
	
	@Bean
	public FilterRegistrationBean<Filter> jwtFilter() {
		final FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>();
		
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/secure/*");

		return registrationBean;
	}
	
	
}
