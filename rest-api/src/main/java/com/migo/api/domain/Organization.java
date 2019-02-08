/**
 * 
 */
package com.migo.api.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author orobsonpires Feb 7, 2019
 */
@Document(collection = "organizations")
public class Organization extends BaseDomain {
	@Id
	private ObjectId id;
	@Indexed(unique = true)
	private String name;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
