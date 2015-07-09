package com.odcgroup.ocs.packager.launcher;

public class UnableToStartPackagerException extends Exception {

	private static final long serialVersionUID = 7741989652810524452L;

	public UnableToStartPackagerException(String message, Exception e) {
		super(message, e);
	}
	
}
