package com.odcgroup.t24.server.external.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.server.preferences.ServerPreferenceInitializer;
import com.odcgroup.t24.server.external.T24ServerExternalCore;

public class T24ServerPreferenceInitializer extends ServerPreferenceInitializer {

	@Override
	public Preferences getPreferences() {
		return DefaultScope.INSTANCE.getNode(T24ServerExternalCore.PLUGIN_ID);
	}
	
}
