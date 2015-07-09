package com.odcgroup.ds.t24.packager.t24project;

import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

/**
 * Thrown when a problem occurs in T24 project manipulation
 * @author yandenmatten
 */
@SuppressWarnings("serial")
public class T24ProjectException extends T24PackagerException {

	public T24ProjectException(String message) {
		super(message);
	}

	public T24ProjectException(String message, Throwable t) {
		super(message, t);
	}

}
