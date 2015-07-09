package com.odcgroup.translation.core;

import java.util.Locale;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public interface TranslationPreferenceConstants {
	
	/** The default locale */
	static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	/** The first default locale of interest. */
	public static final Locale DEFAULT_FIRST_ADDITIONAL_LOCALE = Locale.FRENCH;

	/** The second default locale of interest. */
	public static final Locale DEFAULT_SECOND_ADDITIONAL_LOCALE = Locale.GERMAN;
	
	/** */
	static final String PROPERTY_DEFAULT_LANGUAGE = "defaultLanguage";	
	
	/** */
	static final String PROPERTY_ADDITIONAL_LANGUAGES = "additionalLanguages";

}
