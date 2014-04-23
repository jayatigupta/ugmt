package com.ugmt.common.dto;

public class AuthnResponse extends BaseDTO {
	
	private UserDTO user;
	private String authnToken;
	private boolean isEmailVerified;

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getAuthnToken() {
		return authnToken;
	}

	public void setAuthnToken(String authnToken) {
		this.authnToken = authnToken;
	}
}
