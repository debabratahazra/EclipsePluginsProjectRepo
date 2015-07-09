package com.odcgroup.ds.t24.packager.data;

import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

@SuppressWarnings("serial")
public class InvalidDataFilenameException extends T24PackagerException {

	public InvalidDataFilenameException(String message) {
		super(message);
	}
	
}
