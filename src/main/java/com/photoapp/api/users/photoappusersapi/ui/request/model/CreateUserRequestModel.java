package com.photoapp.api.users.photoappusersapi.ui.request.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

	@NotNull(message="firstName can't be Null")
	@Size(min=2)
	private String firstName;
	@NotNull(message="lastName can't be Null")
	@Size(min=2)
	private String lastName;
	@NotNull(message="emailId can't be Null")
	@Email
	private String emailId;
	@NotNull(message="password can't be Null")
	@Size(min=8,max=15,message="password should be min of 2 and max of 8 characters")
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
