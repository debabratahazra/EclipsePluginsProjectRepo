package com.temenos.t24.tools.eclipse.basic;

public class PluginException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public PluginException (String message) {
        super(message);
    }
    
    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PluginException (Throwable cause) {
        super(cause);
    }
}
