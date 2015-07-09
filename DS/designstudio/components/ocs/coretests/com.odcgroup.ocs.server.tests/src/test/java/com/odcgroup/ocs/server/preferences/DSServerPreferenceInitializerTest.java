package com.odcgroup.ocs.server.preferences;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

public class DSServerPreferenceInitializerTest  {
	
	@Test
	public void testDS4524() {
		new OCSServerPreferenceInitializer().initializeDefaultPreferences();
		Assert.assertEquals(OCSServerPreferenceInitializer.DEFAULT_LOG_WATCHED, new ProjectPreferences(null, ServerCore.PLUGIN_ID).get(DSServerPreferenceManager.WATCHED_LOG_FILES, null));
	}

}
