package com.odcgroup.ds.t24.packager.data;

import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

@SuppressWarnings("serial")
public class TooMuchDataFilesException extends T24PackagerException {

	public TooMuchDataFilesException(String message) {
		super(message);
	}

}
