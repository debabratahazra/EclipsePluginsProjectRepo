package com.odcgroup.translation.core;

import java.util.Locale;

/**
 * Raised when a translation is not found
 * @author yan
 */
public class TranslationNotFoundException extends TranslationException {

	private static final long serialVersionUID = 7285505453502849150L;

	private String element;
	private ITranslationKind kind;
	private Locale locale;

	public TranslationNotFoundException(String element) {
		this.element = element;
	}
	
	/**
	 * @param message
	 */
	public TranslationNotFoundException(String element, ITranslationKind kind, Locale locale) {
		this.element = element;
		this.kind = kind;
		this.locale = locale;
	}
	
	public String toString() {
		if (kind != null && locale != null) {
			return "The " + element + " domain element doesn't have a " + 
					kind + " translation for the " + locale.getISO3Language() + "locale";
		} else {
			return "The " + element + " domain element doesn't have a translation";
		}
	}

}
