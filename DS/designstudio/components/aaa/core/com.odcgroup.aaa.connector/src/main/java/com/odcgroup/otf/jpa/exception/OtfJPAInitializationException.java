package com.odcgroup.otf.jpa.exception;

/**
 * Thrown when something goes wrong during JPA initialization (like config problem)
 * It is not intended for the caller to be able to correct something, that's why
 * it is a runtime exception. 
 * 
 * @author yan
 */
public class OtfJPAInitializationException extends OtfJPARuntimeException {
	private static final long serialVersionUID = 6221521181960384061L;

	public OtfJPAInitializationException(String message) {
		super(message);
	}

	public OtfJPAInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

}
