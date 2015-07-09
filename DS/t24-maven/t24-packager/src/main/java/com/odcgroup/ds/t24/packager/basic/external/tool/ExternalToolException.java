package com.odcgroup.ds.t24.packager.basic.external.tool;

/**
 * Raised when a problem occurs during the external tool execution 
 * @author yan
 */
public class ExternalToolException extends Exception {

	private static final long serialVersionUID = 4691618580572879493L;

	public ExternalToolException(String message) {
		super(message);
	}
	
	public ExternalToolException(String message, Throwable cause) {
		super(message, cause);
	}

}
