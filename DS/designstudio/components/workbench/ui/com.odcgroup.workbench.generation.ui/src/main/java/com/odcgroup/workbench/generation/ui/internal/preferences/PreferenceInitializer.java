package com.odcgroup.workbench.generation.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.workbench.generation.PreferenceConstants;
import com.odcgroup.workbench.generation.ui.GenerationUICore;

/**
 *
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */

	public void initializeDefaultPreferences() {
		IPreferenceStore store = GenerationUICore.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PREF_PAGEFLOW_ACTIVITYNAME, "aaa");
	}

}
