package com.odcgroup.ocs.server.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.server.preferences.ServerPreferenceInitializer;

public class OCSServerConsolePreferencesInitializer extends ServerPreferenceInitializer {

	@Override
	public Preferences getPreferences() {
		return DefaultScope.INSTANCE.getNode(ServerCore.PLUGIN_ID);
	}

}
