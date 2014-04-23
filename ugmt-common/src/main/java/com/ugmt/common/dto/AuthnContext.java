package com.ugmt.common.dto;

public class AuthnContext {

	private String loginUid;
	private String password;

	public String getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(String loginUid) {
		this.loginUid = loginUid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
