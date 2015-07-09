package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

public class RemoteConnectionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RemoteConnectionException() {
        super();
    }

    public RemoteConnectionException(String message) {
        super(message);
    }

    public RemoteConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteConnectionException(Throwable cause) {
        super(cause);
    }
}
