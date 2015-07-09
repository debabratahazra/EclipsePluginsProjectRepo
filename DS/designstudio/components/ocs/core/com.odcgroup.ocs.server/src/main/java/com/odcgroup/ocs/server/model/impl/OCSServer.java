package com.odcgroup.ocs.server.model.impl;

import java.util.List;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.server.model.impl.DSServer;

public abstract class OCSServer extends DSServer {

	public OCSServer(String id, String name) {
		super(id, name);
	}

	boolean logsClearedAtStartup;

	@Override
	protected void updateServerValuesFromPreferences() {
		logsClearedAtStartup = Boolean.valueOf(ServerCore.getDefault().getProjectPreferences().get(DSServerPreferenceManager.CLEAR_LOG_AT_STARTUP, "true"));
	}

	public boolean isClearLogAtStartup() {
		return logsClearedAtStartup;
	}

	@Override
	public List<String> getWatchedLogFiles() {
		return ServerCore.getDefault().getOCSServerPreferenceManager().getWatchedLogFiles();
	}


}
