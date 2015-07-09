package com.odcgroup.ocs.server.external.ui.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.server.util.DSProjectUtil;

public class ProjectHelperTest {

	private static final String TEST_PROJECT_NAME = "testProjectName";
	
	/**
	 * @throws Exception
	 */
	/**
	 * @throws Exception
	 */
	@Test
	public void testJavaProjectsAreNotEmpty() throws Exception {

		IDSProject mockOcsProject = Mockito.mock(DSProject.class);
		Mockito.when(mockOcsProject.getName()).thenReturn(TEST_PROJECT_NAME);
		IProject javaProject = DSProjectUtil.convertToIProject(ResourcesPlugin.getWorkspace().getRoot(), mockOcsProject);
		String outputFolder = ProjectHelper.getOutputFolder(javaProject);
		Assert.assertTrue(outputFolder.isEmpty());
		
	}
}
