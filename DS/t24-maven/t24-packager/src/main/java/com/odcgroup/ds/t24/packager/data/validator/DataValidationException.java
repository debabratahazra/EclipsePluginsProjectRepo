package com.odcgroup.ds.t24.packager.data.validator;

import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

@SuppressWarnings("serial")
public class DataValidationException extends T24PackagerException {

	public DataValidationException(String message) {
		super(message);
	}

	public DataValidationException(String message, Exception e) {
		super(message, e);
	}

}
