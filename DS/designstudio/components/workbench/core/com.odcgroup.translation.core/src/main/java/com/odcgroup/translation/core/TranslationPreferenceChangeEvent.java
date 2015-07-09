package com.odcgroup.translation.core;

import java.util.EventObject;
import java.util.Locale;

/**
 * An event object describing the details of a change to a preference
 * in the preference store.
 * 
 * @see ITranslationPreferences.ITranslationPreferencesChangeListener
 * @author atr
 */
public final class TranslationPreferenceChangeEvent extends EventObject {

	/** */
	private static final long serialVersionUID = 1L;

	private String key;
	private Locale newValue;
	private Locale oldValue;

	/**
	 * Constructor for a new translation preference change event. The source and
	 * the key must not be <code>null</code>. The old and new locale values must
	 * be either a <code>Locale</code> or <code>null</code>.
	 * 
	 * @param source
	 *            the source on which the change occurred
	 * @param key
	 *            the preference key
	 * @param oldValue
	 *            the old locale value, as a <code>Locale</code> or
	 *            <code>null</code>
	 * @param newValue
	 *            the new locale value, as a <code>Locale</code> or
	 *            <code>null</code>
	 */
	public TranslationPreferenceChangeEvent(ITranslationPreferences source, String key, Locale oldValue, Locale newValue) {
		super(source);
		if (key == null) {
			throw new IllegalArgumentException();
		}
		this.key = key;
		this.newValue = newValue;
		this.oldValue = oldValue;
	}

	/**
	 * Return the translation preferences on which the change occurred. Must not
	 * be <code>null</code>.
	 * 
	 * @return the translation preferences
	 */
	public ITranslationPreferences getPreferences() {
		return (ITranslationPreferences) source;
	}

	/**
	 * Return the key of the preference which was changed. Must not be
	 * <code>null</code>.
	 * 
	 * @return the preference key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Return the new locale for the preference or <code>null</code> if the
	 * preference was removed.
	 * 
	 * @return the new locale or <code>null</code>
	 */
	public Locale getNewLocale() {
		return newValue;
	}

	/**
	 * Return the old locale for the preference or <code>null</code> if the
	 * preference was removed or if it cannot be determined.
	 * 
	 * @return the old locale or <code>null</code>
	 */
	public Locale getOldLocale() {
		return oldValue;
	}

}
