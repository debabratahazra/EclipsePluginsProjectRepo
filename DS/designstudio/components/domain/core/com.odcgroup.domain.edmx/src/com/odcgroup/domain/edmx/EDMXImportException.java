package com.odcgroup.domain.edmx;

/**
 * Exception for problems in EDMX Import.
 *
 * @author Initial skeleton by Michael Vorburger, for Stéphane Mellinet 
 */
@SuppressWarnings("serial")
public class EDMXImportException extends Exception {

	public EDMXImportException(String message, Throwable cause) {
		super(message, cause);
	}

	public EDMXImportException(String message) {
		super(message);
	}

}
