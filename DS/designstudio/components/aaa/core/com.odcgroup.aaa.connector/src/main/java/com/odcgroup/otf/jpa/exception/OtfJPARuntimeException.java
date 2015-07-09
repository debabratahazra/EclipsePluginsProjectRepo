package com.odcgroup.otf.jpa.exception;

/**
 * Base class for exceptions which cannot be corrected by the caller
 *   
 * @author yan
 */
public abstract class OtfJPARuntimeException extends RuntimeException {
	private static final long serialVersionUID = 3686753641379424741L;

	public OtfJPARuntimeException(String message) {
		super(message);
	}

    public OtfJPARuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
