package com.odcgroup.server.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.osgi.service.prefs.Preferences;


public abstract class ServerPreferenceInitializer extends AbstractPreferenceInitializer {
	
	public abstract Preferences getPreferences();

	public void initializeDefaultPreferences() {
		ServerPreferences.setDefaultValues(getPreferences());
	}
}
