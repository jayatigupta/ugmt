package com.ugmt.common.exception;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 5684956033694527988L;

	public InvalidDataException() {
	}

	public InvalidDataException(String msg) {
		super(msg);
	}

	public InvalidDataException(String msg, Exception e) {
		super(msg, e);
	}
}
