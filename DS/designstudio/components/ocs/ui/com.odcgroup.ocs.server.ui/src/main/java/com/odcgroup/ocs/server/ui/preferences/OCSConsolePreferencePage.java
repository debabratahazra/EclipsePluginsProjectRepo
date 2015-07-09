package com.odcgroup.ocs.server.ui.preferences;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.server.ui.preferences.ConsolePreferencePage;

public class OCSConsolePreferencePage extends ConsolePreferencePage {

	@Override
	protected String getScopeQualifier() {
		return ServerCore.PLUGIN_ID;
	}
	
	public OCSConsolePreferencePage() {
	}

}
