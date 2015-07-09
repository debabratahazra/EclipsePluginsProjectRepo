package com.odcgroup.translation.core.migration;

public class TranslationMigrationException extends Exception {

	private static final long serialVersionUID = 5645409472805005030L;

    public TranslationMigrationException(String message) {
        super(message);
    }

    public TranslationMigrationException(Throwable cause) {
        super(cause);
    }

    public TranslationMigrationException(String message, Throwable cause) {
        super(message, cause);
    }

}
