package com.migo.api.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class ApplicationUser extends BaseDomain{
	@Id
	private ObjectId id;
	// That's the username.
	@Indexed(unique = true)
	private String email;
	private String password;

	public ApplicationUser() {
		super();
	}

	public ApplicationUser(ObjectId id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
