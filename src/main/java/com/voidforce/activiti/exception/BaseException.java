package com.voidforce.activiti.exception;

public class BaseException extends RuntimeException {
	private Object[] parameters = new Object[0];

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Object[] parameters) {
		super(message);
		this.parameters = parameters;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(String message, Object[] parameters, Throwable cause) {
		super(message, cause);
		this.parameters = parameters;
	}

	public Object[] getParameters() {
		return parameters;
	}
}