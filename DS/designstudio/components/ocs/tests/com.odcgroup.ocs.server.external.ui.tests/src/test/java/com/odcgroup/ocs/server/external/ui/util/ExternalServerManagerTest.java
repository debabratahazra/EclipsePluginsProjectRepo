package com.odcgroup.ocs.server.external.ui.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.DSProjectUtil;

public class ExternalServerManagerTest {

	private static final String TEST_PROJECT_NAME = "testProjectName";

	@After
	public void tearDown() throws Exception {
		ExternalServerManager.resestProjectsToBeRemovedWhenServerStopped();
	}
	
	@Test
	public void testRegisteringProjectForUndeployment_UpdatesTheInternalProjectsList_WhenRegisteringProjectsForUndeployment() throws Exception {
		Assert.assertEquals(0, ExternalServerManager.getProjectsToBeRemovedWhenServerStopped().size());
		
		ExternalServerManager.registerProjectForUndeployment(null);
		
		Assert.assertEquals(1, ExternalServerManager.getProjectsToBeRemovedWhenServerStopped().size());
	}
	
	@Test
	public void testprocessLateUndeployment_ClearsInternalProjectsList_WhenProjectsSuccesfullyundeployed() throws Exception {
		IDSProject mockOcsProject = Mockito.mock(DSProject.class);
		Mockito.when(mockOcsProject.getName()).thenReturn(TEST_PROJECT_NAME);
		
		
		IProject mockIProject = Mockito.mock(IProject.class);
		Mockito.when(mockIProject.getName()).thenReturn(TEST_PROJECT_NAME);

		
		IProject realIProject = DSProjectUtil.convertToIProject(ResourcesPlugin.getWorkspace().getRoot(), mockOcsProject);
		
		
		ExternalServerManager.registerProjectForUndeployment(realIProject);
		Assert.assertEquals(1, ExternalServerManager.getProjectsToBeRemovedWhenServerStopped().size());
		
		ExternalServerManager.processLateUndeployment();
		
		Assert.assertEquals(0, ExternalServerManager.getProjectsToBeRemovedWhenServerStopped().size());
	}
}
