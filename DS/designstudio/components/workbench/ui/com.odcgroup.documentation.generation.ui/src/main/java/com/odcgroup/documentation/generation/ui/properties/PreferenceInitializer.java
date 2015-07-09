package com.odcgroup.documentation.generation.ui.properties;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.odcgroup.documentation.generation.ui.DocumentationUICore;
import com.odcgroup.workbench.generation.PreferenceConstants;


public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */

	public void initializeDefaultPreferences() {
		IPreferenceStore store = DocumentationUICore.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\");
		DocumentationUICore.getDefault().getPreferenceStore()
		.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty() == PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME) {
					String value = event.getNewValue().toString();
					IPreferenceStore store = DocumentationUICore.getDefault().getPreferenceStore();
					store.setDefault(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, value);
				}
			}
		});
	}
}
