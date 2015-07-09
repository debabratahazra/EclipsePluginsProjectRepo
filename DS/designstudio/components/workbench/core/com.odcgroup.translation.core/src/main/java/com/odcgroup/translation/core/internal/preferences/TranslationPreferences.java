package com.odcgroup.translation.core.internal.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationPreferenceConstants;
import com.odcgroup.translation.core.preferences.BaseTranslationPreferences;
import com.odcgroup.translation.core.util.LanguageUtils;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public class TranslationPreferences extends BaseTranslationPreferences implements ITranslationPreferences {

	/** */
	private ConcurrentHashMap<String, Locale> locales = new ConcurrentHashMap<String, Locale>();

	/**
	 * @param language
	 * @return
	 */
	private Locale getLocale(String language) {
		Locale locale = locales.get(language);
		if (null == locale) {
			Locale newLocale =  LanguageUtils.getLocaleFromString(language);
			locale = locales.putIfAbsent(language, newLocale);
			if (locale == null) {
				locale = newLocale;
			}
		}
		return locale;
	}

	@Override
	public List<Locale> getAdditionalLocales() {
		List<Locale> locales = new ArrayList<Locale>();
		String languages = getPreferences().get(TranslationPreferenceConstants.PROPERTY_ADDITIONAL_LANGUAGES, null);
		StringTokenizer tokenizer = new StringTokenizer(languages, ",");
		while (tokenizer.hasMoreTokens()) {
			locales.add(getLocale(tokenizer.nextToken()));
		}
		return locales;
	}

	@Override
	public Locale getDefaultLocale() {
		String language = getPreferences().get(TranslationPreferenceConstants.PROPERTY_DEFAULT_LANGUAGE, null);
		return getLocale(language);
	}

	@Override
	public void addAdditionalLocale(Set<Locale> locales) {
		// read previous values
		ProjectPreferences prefs = getPreferences();
		String interestLanguages = prefs.get(TranslationPreferenceConstants.PROPERTY_ADDITIONAL_LANGUAGES, null);
		String defLanguage = prefs.get(TranslationPreferenceConstants.PROPERTY_DEFAULT_LANGUAGE, null);

		// add values
		for (Locale locale : locales) {
			String language = locale.toString();
			if (!language.equals(defLanguage) && interestLanguages.indexOf(language) == -1) {
				if (interestLanguages.length() != 0) {
					interestLanguages = interestLanguages + ",";
				}
				interestLanguages = interestLanguages + language;
			}
		}

		// store values
		prefs.put(TranslationPreferenceConstants.PROPERTY_ADDITIONAL_LANGUAGES, interestLanguages);
		prefs.flush();
	}

	/**
	 * @param project
	 */
	public TranslationPreferences(IProject project) {
		super(project);
	}

}
