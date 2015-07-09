package com.odcgroup.translation.core.preferences;

import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.ITranslationPreferencesChangeListener;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.internal.preferences.TranslationPreferencesChangeSupport;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public abstract class BaseTranslationPreferences implements ITranslationPreferences {

	/**
	 */
	private TranslationPreferencesChangeSupport queue = 
					new TranslationPreferencesChangeSupport(this);
	
	/**
	 * The preferences store
	 */
	private ProjectPreferences preferences;
	
	protected final ProjectPreferences getPreferences() {
		return preferences;
	}

	/**
	 * Notifies listener of a change in the translation
	 * @param key
	 * @param oldValue
	 * @param newValue
	 */
	protected void fireChangeTranslation(String key, Locale oldValue, Locale newValue) {
		queue.fireTranslationPreferenceChange(key, oldValue, newValue);
	}
	
	@Override
	public void addPreferenceChangeListener(ITranslationPreferencesChangeListener listener) {
		queue.addPreferenceChangeListener(listener);
	}

	@Override
	public void addPreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener) {
		queue.addPreferenceChangeListener(key, listener);
	}

	@Override
	public void removePreferenceChangeListener(ITranslationPreferencesChangeListener listener) {
		queue.removePreferenceChangeListener(listener);
	}

	@Override
	public void removePreferenceChangeListener(String key, ITranslationPreferencesChangeListener listener) {
		queue.removePreferenceChangeListener(key, listener);
	}

	@Override
	public void release() {
	}
	
	public BaseTranslationPreferences(IProject project) {
		if (project == null) {
			throw new IllegalArgumentException();
		}
		this.preferences = new ProjectPreferences(project, TranslationCore.PLUGIN_ID);
		
	}
	
}
