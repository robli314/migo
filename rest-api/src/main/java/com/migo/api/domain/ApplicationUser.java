package com.migo.api.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class ApplicationUser {
	@Id
	private ObjectId id;
	private String username;
	private String password;

	public ApplicationUser(ObjectId id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public ApplicationUser() {
		super();
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
