package com.migo.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	/*
	 * That is an unique reference to auto-incremented sequence for the user
	 * collection. It's annotated with @Transient in order to avoid being persisted
	 * alongside other properties.
	 */
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";

	@Id
	private Long id;
	private String name;
	private String password;

	public User() {
	}

	public User(Long id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
