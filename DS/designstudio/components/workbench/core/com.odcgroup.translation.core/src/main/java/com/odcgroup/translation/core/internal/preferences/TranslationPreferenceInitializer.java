package com.odcgroup.translation.core.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationPreferenceConstants;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class TranslationPreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(TranslationCore.PLUGIN_ID);

		node.put(TranslationPreferenceConstants.PROPERTY_DEFAULT_LANGUAGE,
				TranslationPreferenceConstants.DEFAULT_LOCALE.toString());

		StringBuilder builder = new StringBuilder();
		builder.append(TranslationPreferenceConstants.DEFAULT_FIRST_ADDITIONAL_LOCALE.toString());
		builder.append(",");
		builder.append(TranslationPreferenceConstants.DEFAULT_SECOND_ADDITIONAL_LOCALE.toString());

		node.put(TranslationPreferenceConstants.PROPERTY_ADDITIONAL_LANGUAGES, builder.toString());
	}

	/**
	 * 
	 */
	public TranslationPreferenceInitializer() {
	}

}
