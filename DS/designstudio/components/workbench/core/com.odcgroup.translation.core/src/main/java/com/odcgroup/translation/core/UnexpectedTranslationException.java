package com.odcgroup.translation.core;

/**
 * Thrown when an unexpected problem occurs when manipulating the translation
 * @author yan
 */
public class UnexpectedTranslationException extends TranslationException {

	private static final long serialVersionUID = -8139996208731905678L;

	public UnexpectedTranslationException() {
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnexpectedTranslationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public UnexpectedTranslationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnexpectedTranslationException(Throwable cause) {
		super(cause);
	}

}
