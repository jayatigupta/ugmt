package com.ugmt.common.dto;

public class BaseDTO {

	public static enum AuthnStatus {
		SUCCESS, FAILURE
	}

	private String status = AuthnStatus.SUCCESS.name();
	private String msg = "";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
