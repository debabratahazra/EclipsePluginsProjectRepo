package com.odcgroup.server.ui;

public class UnableToStopServerException extends Exception {

	private static final long serialVersionUID = 8420745243653132310L;
	
	public UnableToStopServerException(String message) {
		super(message);
	}
	
	public UnableToStopServerException(String message, Throwable t) {
		super(message, t);
	}

}
