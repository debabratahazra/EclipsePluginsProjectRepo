package com.odcgroup.translation.core;

/**
 * The base class for all exceptions related to translations
 *
 * @author atr
 */
public abstract class TranslationException extends Exception {

	/** */
	private static final long serialVersionUID = 2380172792364906222L;

	/**
	 * 
	 */
	public TranslationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TranslationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public TranslationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TranslationException(Throwable cause) {
		super(cause);
	}

}
