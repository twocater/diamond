package com.twocater.diamond.core.server;

public class NotExistException extends Exception {

	private static final long serialVersionUID = -5445711978552052673L;

	public NotExistException() {
		super();
	}

	public NotExistException(String message) {
		super(message);
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotExistException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("NotExistException:  ").append(getMessage());
		return (sb.toString());
	}
}
