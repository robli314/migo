/**
 * 
 */
package com.migo.api.domain;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author orobsonpires Feb 8, 2019
 */
public class BaseDomain {
	@Field("created")
	private Long created = (new Date().getTime()) / 1000;

	@Field("modified")
	private Long modified = (new Date().getTime()) / 1000;

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getModified() {
		return modified;
	}

	public void setModified(Long modified) {
		this.modified = modified;
	}

}
