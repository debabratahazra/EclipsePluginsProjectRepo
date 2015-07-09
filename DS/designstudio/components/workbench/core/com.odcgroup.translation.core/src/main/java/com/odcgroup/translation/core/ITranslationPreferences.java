package com.odcgroup.translation.core;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * This interface describes ...
 * <p>
 * 
 * @author atr
 */
public interface ITranslationPreferences {

	/**
	 * Add a <code>ITranslationPreferencesChangeListener</code> to the
	 * translation preferences change for all properties. Has no effect if an
	 * identical listener is already registered.
	 * <p>
	 * Translation preferences listener are informed about state changes that
	 * could affect the rendering of the viewer that display the translations
	 * </p>
	 * If <code>listener</code> is null, no exception is thrown and no action is
	 * taken.
	 * 
	 * @param listener
	 *            The ITranslationPreferenceChangesListener to be added
	 */
	void addPreferenceChangeListener(ITranslationPreferencesChangeListener listener);
	void addPreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener);

	/**
	 * Removes a listener to the translation preferences.
	 * 
	 * If <code>listener</code> is null, or was never added, no exception is
	 * thrown and no action is taken.
	 * 
	 * @param listener
	 *            The ITranslationPreferencesChangeListener to be removed
	 */
	void removePreferenceChangeListener(ITranslationPreferencesChangeListener listener);
	void removePreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener);
	
	/**
	 * 
	 */
	void release();

	/**
	 * @return the default locale
	 */
	Locale getDefaultLocale();

	/**
	 * @return the additional locales
	 */
	List<Locale> getAdditionalLocales();

	/**
	 * Add a set of additional locales to the preferences store.
	 * 
	 * @param locales
	 *            the locales to be added
	 */
	void addAdditionalLocale(Set<Locale> locales);

}
