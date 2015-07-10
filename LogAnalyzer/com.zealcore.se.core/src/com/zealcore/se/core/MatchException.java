package com.zealcore.se.core;

public class MatchException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4176432012472868795L;

    private final String message;

    public MatchException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + this.message;
    }
}
