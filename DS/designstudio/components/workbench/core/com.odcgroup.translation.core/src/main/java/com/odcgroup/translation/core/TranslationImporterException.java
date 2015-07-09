package com.odcgroup.translation.core;

/**
 * This exception is raised by the <code>ITranslationImporter</code>.
 * 
 * @author atr
 */
public class TranslationImporterException extends Exception {

	private static final long serialVersionUID = 6135439546143671673L;

	public TranslationImporterException(String message, Throwable cause) {
		super(message, cause);
	}

	public TranslationImporterException(Throwable cause) {
		super(cause);
	}

	public TranslationImporterException(String message) {
		super(message);
	}

	public TranslationImporterException() {
	}
}
