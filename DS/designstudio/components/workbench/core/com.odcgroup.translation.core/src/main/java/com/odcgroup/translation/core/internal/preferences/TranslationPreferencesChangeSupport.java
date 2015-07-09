package com.odcgroup.translation.core.internal.preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.ITranslationPreferencesChangeListener;
import com.odcgroup.translation.core.TranslationPreferenceChangeEvent;

/**
 * @author atr
 */
public class TranslationPreferencesChangeSupport {

	/** */
	private transient ITranslationPreferences source;

	/**
	 * 
	 */
	private List<ITranslationPreferencesChangeListener> listeners;

	/**
	 * 
	 */
	private Map<String, TranslationPreferencesChangeSupport> keyListeners;

	/**
	 * Fire an existing <code>TranslationPreferenceChangeEvent</code> to any
	 * registered listeners. No event is fired if the given event's old and new
	 * values are equal and non-null.
	 * 
	 * @param event
	 *            The <code>TranslationPreferenceChangeEvent</code> object.
	 */
	protected void fireTranslationPreferenceChange(TranslationPreferenceChangeEvent event) {
		if (listeners != null) {
			for (ITranslationPreferencesChangeListener target : listeners) {
				target.preferenceChange(event);
			}
		}
		if (keyListeners != null) {
			String key = event.getKey();
			if (key != null) {
				TranslationPreferencesChangeSupport support = keyListeners.get(key);
				if (support != null) {
					support.fireTranslationPreferenceChange(event);
				}
			}
		}
	}

	/**
	 * @param listener
	 */
	public synchronized void addPreferenceChangeListener(ITranslationPreferencesChangeListener listener) {

		if (listener == null) {
			return;
		}

		if (listeners == null) {
			listeners = new ArrayList<ITranslationPreferencesChangeListener>();
		}

		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * @param key
	 * @param listener
	 */
	public synchronized void addPreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener) {
		if (listener == null || key == null) {
			return;
		}
		if (keyListeners == null) {
			keyListeners = new HashMap<String, TranslationPreferencesChangeSupport>();
		}
		TranslationPreferencesChangeSupport support = keyListeners.get(key);
		if (support == null) {
			support = new TranslationPreferencesChangeSupport(source);
			keyListeners.put(key, support);
		}
		support.addPreferenceChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public synchronized void removePreferenceChangeListener(ITranslationPreferencesChangeListener listener) {
		listeners.remove(listeners.indexOf(listener));
	}

	/**
	 * @param key
	 * @param listener
	 */
	public void removePreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener) {
		if (listener == null || key == null) {
			return;
		}
		if (keyListeners == null) {
			return;
		}
		TranslationPreferencesChangeSupport support = (TranslationPreferencesChangeSupport) keyListeners.get(key);
		if (support == null) {
			return;
		}
		support.removePreferenceChangeListener(listener);
	}

	/**
	 * @return all registered generic listeners
	 */
	public ITranslationPreferencesChangeListener[] getTranslationChangeListeners() {
		ArrayList<ITranslationPreferencesChangeListener> list = new ArrayList<ITranslationPreferencesChangeListener>();
		if (listeners != null) {
			list.addAll(listeners);
		}
		return list.toArray(new ITranslationPreferencesChangeListener[0]);
	}

	/**
	 * @param property
	 * @return all listeners registered
	 */
	public ITranslationPreferencesChangeListener[] getTranslationChangeListeners(String property) {
		ArrayList<ITranslationPreferencesChangeListener> list = new ArrayList<ITranslationPreferencesChangeListener>();
		if (keyListeners != null) {
			for (TranslationPreferencesChangeSupport support : keyListeners.values()) {
				list.addAll(Arrays.asList(support.getTranslationChangeListeners()));
			}
		}
		return list.toArray(new ITranslationPreferencesChangeListener[0]);
	}

	/**
	 * @param key
	 * @param oldValue
	 * @param newValue
	 */
	public void fireTranslationPreferenceChange(String key, Locale oldValue, Locale newValue) {
		if (!oldValue.equals(newValue)) {
			fireTranslationPreferenceChange(
					new TranslationPreferenceChangeEvent(source, key, oldValue, newValue));
		}
	}

	/**
	 * @param preferences
	 */
	public TranslationPreferencesChangeSupport(ITranslationPreferences preferences) {
		source = preferences;
	}

}
