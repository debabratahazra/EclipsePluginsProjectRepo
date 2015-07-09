package com.odcgroup.ocs.server.external.model.internal;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.external.builder.internal.delta.ServerLocationConstants;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.server.tests.ServerTestsCore;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

public class ExternalServerTest {

	private static final String TEST_INSTALL_DIR = "C:\\someInstallDir";
	private static final String TEST_EAR_DIR = "C:\\someEarDir";

	private static final String NEW_DESTINATION = "newDestination";
	private static final String EXTERNAL_SERVER = "resources/external-server";

	private String startScript = "";
	private ExternalServer externalServer;
	
	@Before
	public void setUp() {
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(OCSRuntimePreference.INSTALL_DIR, TEST_INSTALL_DIR);
		prefs.put(OCSRuntimePreference.EAR_DIR, TEST_EAR_DIR);
		
		externalServer = new ExternalServer("someId", "SomeName");
	}

	/**
	 * Validate that the server model is updated when the preferences change 
	 */
	@Test
	public void testDs4322_DeployToServerWorksInUnpredictableWays() {
		// Given
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(OCSRuntimePreference.INSTALL_DIR, "");
		
		Assert.assertEquals("Installation dir should be empty", "", externalServer.getInstallationDirectory());
		
		// When
		OCSPluginActivator.getDefault().getProjectPreferences().put(OCSRuntimePreference.INSTALL_DIR, NEW_DESTINATION);
		
		// Then
		Assert.assertEquals("Installation dir should be the new installation", NEW_DESTINATION, externalServer.getInstallationDirectory());
	}

	@Test
	public void testExternalServer_getState() throws IOException {
		// Given
		ExternalServer externalServer = new ExternalServer("someId", "SomeName") {
			@Override
			public String getStartScript() {
				return startScript;
			}
		};
		Assert.assertEquals("Initially the external server shouldn't be considered as properly configured", 
				IDSServer.STATE_NOT_CONFIGURED, externalServer.getState());
		
		// When
		File fakeStartScript = File.createTempFile("ds4392", "test");
		fakeStartScript.deleteOnExit();
		startScript = fakeStartScript.getAbsolutePath();
		
		// Then
		Assert.assertEquals("The server should be properly configured by now", 
				IDSServer.STATE_STOPPED, externalServer.getState());
	}
	
	@Test
	public void testInstallDir() {
		Assert.assertEquals("The install dir should be the one set in the preferences", 
				TEST_INSTALL_DIR, externalServer.getInstallationDirectory());
	}
	
	@Test
	public void testGetLogDirectory() {
		Assert.assertTrue("Should contain the install dir", externalServer.getLogDirectory().contains(TEST_INSTALL_DIR));
		Assert.assertTrue("Should contain the log subfolder", externalServer.getLogDirectory().contains(ExternalServer.LOGS_LOCATION));
	}

	@Test
	public void testGetStartScript() {
		Assert.assertTrue("Should contain the install dir", externalServer.getStartScript().contains(TEST_INSTALL_DIR));
		Assert.assertTrue("Should contain the stop script", externalServer.getStartScript().contains(ExternalServer.STARTOCS_SCRIPT));
	}

	@Test
	public void testGetStopScript() {
		Assert.assertTrue("Should contain the install dir", externalServer.getStopScript().contains(TEST_INSTALL_DIR));
		Assert.assertTrue("Should contain the stop script", externalServer.getStopScript().contains(ExternalServer.STOPOCS_SCRIPT));
	}

	@Test
	public void testGetReloadMessagesTouchFile() {
		Assert.assertTrue("Should contain the install dir", externalServer.getReloadMessagesTouchFile().contains(TEST_INSTALL_DIR));
		Assert.assertTrue("Should contain the wui block subfolder", externalServer.getReloadMessagesTouchFile().contains(ServerLocationConstants.WUI_BLOCK_LOCATION));
		Assert.assertTrue("Should contain the touch file", externalServer.getReloadMessagesTouchFile().contains(ExternalServer.RELOAD_MESSAGES_TOUCH_FILE));
	}

	@Test
	public void testIsDisplayAllDeployedFiles() {
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(DSServerPreferenceManager.DISPLAY_ALL_DEPLOYED_FILES, "true");
		Assert.assertTrue("The deploy all files flag dir should be the one set in the preferences", 
				externalServer.isDisplayAllDeployedFiles());
		
		prefs.put(DSServerPreferenceManager.DISPLAY_ALL_DEPLOYED_FILES, "false");
		prefs.flush();
		Assert.assertFalse("The deploy all files flag dir should be the one set in the preferences", 
				externalServer.isDisplayAllDeployedFiles());
	}
	
	@Test
	public void testGetListeningPort() {
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(OCSRuntimePreference.INSTALL_DIR, getInstallDir().getAbsolutePath());
		
		// Then
		Assert.assertEquals("15000", externalServer.getListeningPort());
	}

	/**
	 * @return the install dir
	 */
	private File getInstallDir() {
		URL url = FileLocator.find(ServerTestsCore.getDefault().getBundle(), new Path(EXTERNAL_SERVER), null);
        File installationDir;
		try {
			installationDir = new File(FileLocator.toFileURL(url).toURI());
		} catch (Exception e) {
			throw new RuntimeException("Unable to find: " + url, e);
		}
		return installationDir;
	}
	
	@Test
	public void testGetListeningPortError1() {
		// Given
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(OCSRuntimePreference.INSTALL_DIR, "");

		// Then
		Assert.assertEquals("", externalServer.getListeningPort());
	}	
	
	@Test
	public void testGetListeningPortError2() {
		// Given
		externalServer = new ExternalServer("SomeId", "SomeName") {
			protected File getAllInOneDpiProperties() {
				// Return an existing folder instead of a 
				// dpi properties file 
				return getInstallDir();
			}
			
		};

		// Then
		Assert.assertEquals("", externalServer.getListeningPort());
	}	
}
