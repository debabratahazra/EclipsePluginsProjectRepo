package com.odcgroup.translation.core;

/**
 * This exception is raised by the <code>ITranslationExporter</code>.
 * 
 * @author atr
 */
public class TranslationExporterException extends Exception {

	private static final long serialVersionUID = 4958001268981722861L;

	public TranslationExporterException(String message, Throwable cause) {
		super(message, cause);
	}

	public TranslationExporterException(Throwable cause) {
		super(cause);
	}

	public TranslationExporterException(String message) {
		super(message);
	}

	public TranslationExporterException() {
	}
}
