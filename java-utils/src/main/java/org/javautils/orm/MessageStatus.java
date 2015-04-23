package org.javautils.orm;

import javax.persistence.Embeddable;

@Embeddable
public class MessageStatus {

	private String module;
	private String status;
	private String description;
	
	public MessageStatus() {
	}
	
	public MessageStatus(String module, String status, String description) {
		super();
		this.module = module;
		this.status = status;
		this.description = description;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
