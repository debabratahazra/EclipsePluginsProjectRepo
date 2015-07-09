package com.odcgroup.aaa.connector.internal;

/**
 * Thrown when the Triple A version is not the expected one. 
 * @author yan
 */
public class InvalidTripleAVersionException extends TANGIJException {

	private static final long serialVersionUID = -2872479521146448228L;

	/**
	 * @param message
	 */
	public InvalidTripleAVersionException(String message) {
		super(message);
	}

}
