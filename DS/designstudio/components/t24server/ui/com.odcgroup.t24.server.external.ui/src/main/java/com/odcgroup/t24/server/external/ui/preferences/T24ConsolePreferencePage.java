package com.odcgroup.t24.server.external.ui.preferences;

import com.odcgroup.server.ui.preferences.ConsolePreferencePage;
import com.odcgroup.t24.server.external.T24ServerExternalCore;

public class T24ConsolePreferencePage extends ConsolePreferencePage {

	@Override
	protected String getScopeQualifier() {
		return T24ServerExternalCore.PLUGIN_ID;
	}
	
	public T24ConsolePreferencePage() {
	}

}
