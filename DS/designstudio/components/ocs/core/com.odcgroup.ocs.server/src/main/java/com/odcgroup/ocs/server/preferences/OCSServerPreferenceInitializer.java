package com.odcgroup.ocs.server.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.odcgroup.ocs.server.ServerCore;

/**
 * @author yan
 * @since 1.40
 */
public class OCSServerPreferenceInitializer extends AbstractPreferenceInitializer {

	protected static final String DEFAULT_LOG_WATCHED = "wui_trace.log,otf_trace.log,ocsServer_stdout.log,oams_error.log";

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(ServerCore.PLUGIN_ID);
		node.put(DSServerPreferenceManager.WATCHED_LOG_FILES, DEFAULT_LOG_WATCHED);
	}

}
