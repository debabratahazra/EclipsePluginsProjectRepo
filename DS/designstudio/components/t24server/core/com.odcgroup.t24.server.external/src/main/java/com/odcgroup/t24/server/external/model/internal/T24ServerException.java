package com.odcgroup.t24.server.external.model.internal;

public class T24ServerException extends Exception {

	public T24ServerException() {
		super();
	}

	public T24ServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public T24ServerException(String message) {
		super(message);
	}

	public T24ServerException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public Throwable getCause() {
		return super.getCause();
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

}
