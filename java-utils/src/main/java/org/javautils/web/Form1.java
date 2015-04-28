package org.javautils.web;

import javax.validation.constraints.NotNull;

public class Form1 {

	@NotNull(message="Id may not be empty")
	private Long id;

	public Form1() {
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
