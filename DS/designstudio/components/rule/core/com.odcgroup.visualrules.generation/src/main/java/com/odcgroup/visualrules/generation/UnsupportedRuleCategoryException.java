package com.odcgroup.visualrules.generation;

public class UnsupportedRuleCategoryException extends Exception {

	private static final long serialVersionUID = 2238171809019792145L;

	public UnsupportedRuleCategoryException() {
	}

	public UnsupportedRuleCategoryException(String message) {
		super(message);
	}

	public UnsupportedRuleCategoryException(Throwable cause) {
		super(cause);
	}

	public UnsupportedRuleCategoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
