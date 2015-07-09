package com.odcgroup.aaa.connector.internal;

/**
 * Occurs when the connection to the triple A database is not correct
 * (include testing if a triple A specific table exists).
 *
 * @author yan
 */
public class InvalidDatabaseConnectionException extends TANGIJException {

	private static final long serialVersionUID = -1120633426212685260L;

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidDatabaseConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
