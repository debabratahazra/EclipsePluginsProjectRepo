package com.odcgroup.translation.core.util;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class for Languages
 * 
 * @author atr
 * @since DS 1.40.0
 */
public final class LanguageUtils {

	private static Set<Locale> locales = new HashSet<Locale>();

	static {
		for(Locale locale : Locale.getAvailableLocales()) {
			locales.add(locale);
		}
		for(String lang : Locale.getISOLanguages()) {
			Locale locale = new Locale(lang);
			locales.add(locale);
		}		
	}
	
	/**
	 * Provides only static services
	 */
	private LanguageUtils() {
	}
	
		
	/**
	 * DS-472
	 * return the ISO 639 list of language Locales
	 * @return
	 */
	public static Locale[] getISOLocales() {
		return locales.toArray(new Locale[locales.size()]);
	}
	
	/**
	 * Gets the locale given the display language and the locale in which the language
	 * is expressed.
	 * For example, if:
	 * displayLocale = "en_US"
	 * displayLanguage = "French France"
	 * then this method returns the locale "fr_FR"
	 * 
	 * @param displayLanguage The language expressed in the display Locale
	 * @param displayLocale The displayLocale
	 * @return The Java locale for the language
	 * 
	 */
	public static Locale getLanguage(String displayLanguage, Locale displayLocale) {
		Locale[] locales = getISOLocales();
		if(displayLanguage.contains("(")) { 
			displayLanguage = displayLanguage.substring(0, displayLanguage.indexOf("(")-1);
		}
		
		String lang = "";
		String country = "";
		
		int firstWhiteSpace = displayLanguage.indexOf(" ");
		if(firstWhiteSpace == -1) {
			 lang = displayLanguage;
		} else {
			lang = displayLanguage.substring(0, firstWhiteSpace);
			country = displayLanguage.substring(firstWhiteSpace+1, displayLanguage.length());
		}

		for (int i = 0; i < locales.length; ++i) {
			Locale l = locales[i];
		
			if (l.getDisplayLanguage(displayLocale).equals(lang) && l.getDisplayCountry(displayLocale).equals(country)) {
				return l;
			}
		}	
		
		// Not found
		return null;
	}


	public static Locale getLocaleFromString(String localeString) {
		String[] parts = localeString.split("_");

		String lang = parts[0];
		String country = "";
		if (parts.length > 1) {
			country = parts[1];
		}
		return new Locale(lang, country);
	}


	public static String getFullDisplayStringFromLocale(Locale locale, Locale displayLocale) {
		if (StringUtils.isNotEmpty(locale.getCountry())) {
			return locale
					.getDisplayLanguage(displayLocale)
					+ " "
					+ locale.getDisplayCountry(displayLocale)
					+ " ("
					+ locale.getLanguage()
					+ "_"
					+ locale.getCountry() + ")";
		} else {
			return locale
					.getDisplayLanguage(displayLocale)
					+ " (" + locale.getLanguage() + ")";
		}
	}


	public static String getXmlLangCode(Locale locale) {
		if (StringUtils.isNotEmpty(locale.getCountry())) {
			return locale.getLanguage()
					+ "_"
					+ locale.getCountry();
		} else {
			return locale.getLanguage();
		}
	}


	public static String getShortDisplayString(Locale locale, Locale displayLocale) {
		if (StringUtils.isNotEmpty(locale.getCountry())) {
			return locale
					.getDisplayLanguage(displayLocale)
					+ " "
					+ locale.getDisplayCountry(displayLocale);
		} else {
			return locale
					.getDisplayLanguage(displayLocale);
		}
	}
}
