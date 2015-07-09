package com.odcgroup.aaa.connector.mdfmml;

import com.odcgroup.aaa.connector.internal.TANGIJException;

/**
 * MML Creation problem.
 * Exception throw if something went wrong in the "T'A to MML" translation logic, e.g. something unexpected was encountered in the Meta Dictionary.
 * 
 * @author Michael Vorburger
 */
public class TA2MMLException extends TANGIJException {

	private static final long serialVersionUID = -9211579596652852788L;

	public TA2MMLException(String message) {
		super(message);
	}

	public TA2MMLException(String message, Throwable cause) {
		super(message, cause);
	}
}
