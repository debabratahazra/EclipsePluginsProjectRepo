package com.odcgroup.visualrules.integration.api;

public class RuleModelNotFoundException extends Exception {

	private static final long serialVersionUID = -3721024771449362656L;

	public RuleModelNotFoundException() {
    }

    public RuleModelNotFoundException(String message) {
        super(message);
    }

    public RuleModelNotFoundException(Throwable cause) {
        super(cause);
    }

    public RuleModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
