package com.odcgroup.visualrules.integration.api;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 526842925962578917L;

	public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
