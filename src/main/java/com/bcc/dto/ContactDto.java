package com.bcc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ContactDto {
	
	@NotNull
    @Size(min = 1, max=255)
	public String name;
	
	@NotNull
    @Size(min = 1, max=255)
	public String email;
	
	@NotNull
    @Size(min = 1, max=255)
	public String subject;
	
	@NotNull
    @Size(min = 1, max=255)
	public String message;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
