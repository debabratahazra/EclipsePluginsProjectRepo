package com.odcgroup.server.ui;

public class UnableToStartServerException extends Exception {

	private static final long serialVersionUID = 8420745243653132310L;
	
	public UnableToStartServerException(String message) {
		super(message);
	}
	
	public UnableToStartServerException(String message, Throwable t) {
		super(message, t);
	}

}
