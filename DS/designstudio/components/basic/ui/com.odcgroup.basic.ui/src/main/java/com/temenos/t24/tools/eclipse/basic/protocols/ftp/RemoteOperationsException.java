package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

public class RemoteOperationsException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Remote operation failed. ";

    public RemoteOperationsException() {
        super();
    }

    public RemoteOperationsException(String message) {
        super(DEFAULT_MESSAGE + message);
    }

    public RemoteOperationsException(String message, Throwable cause) {
        super(DEFAULT_MESSAGE + message, cause);
    }

    public RemoteOperationsException(Throwable cause) {
        super(cause);
    }
}
