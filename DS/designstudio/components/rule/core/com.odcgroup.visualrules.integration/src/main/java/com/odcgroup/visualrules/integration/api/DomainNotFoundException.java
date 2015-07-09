package com.odcgroup.visualrules.integration.api;

public class DomainNotFoundException extends Exception {

	private static final long serialVersionUID = -5263477059509038230L;

	public DomainNotFoundException() {
    }

    public DomainNotFoundException(String message) {
        super(message);
    }

    public DomainNotFoundException(Throwable cause) {
        super(cause);
    }

    public DomainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
