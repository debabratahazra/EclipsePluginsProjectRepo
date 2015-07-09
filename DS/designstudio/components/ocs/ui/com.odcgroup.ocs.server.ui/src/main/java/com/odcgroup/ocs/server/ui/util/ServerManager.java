package com.odcgroup.ocs.server.ui.util;

import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;

/**
 *
 * @author pkk
 *
 */
public class ServerManager {

	/**
	 * @param property
	 * @return
	 */
	public static boolean isPropertyChangeServerRelated(String property) {
		if (property != null) {
			if (property.equals(DSServerPreferenceManager.DEPLOYED_PROJECTS)
					|| property.equals(DSServerPreferenceManager.WATCHED_LOG_FILES)
					|| property.equals(OCSRuntimePreference.INSTALL_DIR)
					|| property.equals(OCSRuntimePreference.OCS_SOURCES_JAR) ) {
				return true;
			}
		}
		return false;
	}

}
