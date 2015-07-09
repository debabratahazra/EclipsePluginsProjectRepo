package com.odcgroup.ds.t24.packager.basic;

import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

/**
 * 
 * @author yandenmatten
 */
@SuppressWarnings("serial")
public class BasicException extends T24PackagerException {

	public BasicException(String message) {
		super(message);
	}

	public BasicException(String message, Throwable cause) {
		super(message, cause);
	}

}
