package com.odcgroup.translation.core;

/**
 * A listener used to receive changes to translation preferences.
 * <p>
 * 
 * @author atr
 */
public interface ITranslationPreferencesChangeListener {

	/**
	 * Notification that a translation preference value has changed. The given
	 * event object describes the change details and must not be
	 * <code>null</code>.
	 * 
	 * @param event
	 *            the event details
	 */
	public void preferenceChange(TranslationPreferenceChangeEvent event);

}
