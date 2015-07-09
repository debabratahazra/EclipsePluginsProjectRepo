package com.odcgroup.workbench.core.repository;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(Throwable cause) {
        super(cause);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
