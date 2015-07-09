package com.odcgroup.ocs.server.external.builder.internal;

import org.eclipse.core.resources.IProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.server.model.impl.OCSServer;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

public class OcsServerTest {

	private static final String SOME_ID = "someId";
	private static final String SOME_NAME = "someName";
	private static final String SOME_PROJECT_NAME1 = "someProjectName1";
	private static final String SOME_PROJECT_NAME2 = "someProjectName2";
	private static final String SOME_PATH = "somePath";

	private IDSServer ocsServer;

	class ChangeListenerTester implements IDSServerStateChangeListener {
		private int nbCalls = 0;
		public void serverStateChanged(IDSServer server) {
			nbCalls++;
		}
		public int getNbCalls() {
			return nbCalls;
		}
	}

	@Before
	public void setUp() {
		ocsServer = new OCSServer(SOME_ID, SOME_NAME) {
			public String getLogDirectory() {
				return null;
			}
			public String getListeningPort() {
				return null;
			}
			@Override
			public String getInstallationDirectory() {
				return null;
			}
			@Override
			public IProject getServerProject() {
				return null;
			}
			@Override
			public boolean canDeploy(IProject project) {
				return false;
			}
		};
	}

	@Test
	public void testNameAndId() {
		Assert.assertEquals("The id is wrong", SOME_ID, ocsServer.getId());
		Assert.assertEquals("The name is wrong", SOME_NAME, ocsServer.getName());
	}

	@Test
	public void testStateAndListener() {
		// Given
		ChangeListenerTester listener1 = new ChangeListenerTester();
		ChangeListenerTester listener2 = new ChangeListenerTester();
		ocsServer.addServerStateChangeListener(listener1);
		ocsServer.addServerStateChangeListener(listener2);

		Assert.assertEquals(IDSServer.STATE_STOPPED, ocsServer.getState());

		// When
		ocsServer.setState(IDSServer.STATE_STARTED);

		// Then
		Assert.assertEquals("The listener1 should be called", 1, listener1.getNbCalls());
		Assert.assertEquals("The listener2 should be called", 1, listener1.getNbCalls());

		// Given
		ocsServer.removeServerStateChangeListener(listener1);

		// When
		ocsServer.setState(IDSServer.STATE_STOPPED);

		// Then
		Assert.assertEquals("The listener1 shouldn't be called", 1, listener1.getNbCalls());
		Assert.assertEquals("The listener2 should be called", 2, listener2.getNbCalls());
	}

	@Test
	public void testProjectManagement() {
		// Given
		IDSProject project1 = new DSProject(SOME_PROJECT_NAME1, SOME_PATH);
		IDSProject project2 = new DSProject(SOME_PROJECT_NAME2, SOME_PATH);

		ChangeListenerTester listener = new ChangeListenerTester();
		ocsServer.addServerStateChangeListener(listener);

		// When
		ocsServer.addDsProject(project1);
		ocsServer.addDsProject(project2);

		// Then
		Assert.assertEquals("Should find 2 project", 2, ocsServer.getDsProjects().size());
		Assert.assertTrue(ocsServer.containsProject(project1.getName()));
		Assert.assertTrue(ocsServer.containsProject(project2.getName()));
		Assert.assertFalse(ocsServer.containsProject("notExistingProjectName"));
		Assert.assertEquals("The listener should be called once for each project adding", 2, listener.getNbCalls());

		// When
		boolean result = ocsServer.removeDsProject(project2.getName());

		// Then
		Assert.assertTrue("The removal should be successful", result);
		Assert.assertEquals("Should find 1 project", 1, ocsServer.getDsProjects().size());
		Assert.assertEquals("The listener should be called one more time", 3, listener.getNbCalls());

		// When
		ocsServer.addDsProject(project1); // is already in the list of project
		Assert.assertEquals("Should find 1 project", 1, ocsServer.getDsProjects().size());

		// When
		result = ocsServer.removeDsProject("unexisting project name");

		// Then
		Assert.assertFalse("The removal should be unsuccessful", result);

	}

	@Test
	public void testUpdateProjectList() {
		// Given
		IDSProject project1 = new DSProject(SOME_PROJECT_NAME1, SOME_PATH);
		IDSProject project2 = new DSProject(SOME_PROJECT_NAME2, SOME_PATH);
		IDSProject[] projects = new IDSProject[] {project1, project2};

		ChangeListenerTester listener = new ChangeListenerTester();
		ocsServer.addServerStateChangeListener(listener);

		// When
		ocsServer.updateProjectList(projects);

		// Then
		Assert.assertEquals("Should find 2 project", 2, ocsServer.getDsProjects().size());
		Assert.assertTrue(ocsServer.containsProject(project1.getName()));
		Assert.assertTrue(ocsServer.containsProject(project2.getName()));
		Assert.assertEquals("The listener should be called once", 1, listener.getNbCalls());
	}

	@Test
	public void testIsClearLogAtStartup() {
		// Given
		ProjectPreferences prefs = OCSPluginActivator.getDefault().getProjectPreferences();
		prefs.put(DSServerPreferenceManager.CLEAR_LOG_AT_STARTUP, "true");
		Assert.assertTrue(ocsServer.isClearLogAtStartup());

		// When
		prefs.put(DSServerPreferenceManager.CLEAR_LOG_AT_STARTUP, "false");

		// Then
		Assert.assertTrue(ocsServer.isClearLogAtStartup());
	}

	@Test
	public void testGetOCSProject() {
		IDSProject project = new DSProject(SOME_PROJECT_NAME1, SOME_PATH);
		ocsServer.addDsProject(project);
		Assert.assertNull("Shouldn't be found (doesn't exist)", ocsServer.getDsProject("test"));
		Assert.assertNotNull("Should be found (exists)", ocsServer.getDsProject(SOME_PROJECT_NAME1));
	}

	@Test
	public void testGetOCSProjectDoesNotExist() {
		Assert.assertNull("Shouldn't be found (doesn't exist)", ocsServer.getDsProject("test"));
	}


}
