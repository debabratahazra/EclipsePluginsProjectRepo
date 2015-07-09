package com.odcgroup.ocs.server.embedded.nature;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class EmbeddedServerNatureTest  {

	private IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
	}

	@Test
	public void testGetProjectNull() {
		// Given
		EmbeddedServerNature nature = new EmbeddedServerNature();

		// Then
		Assert.assertNull(nature.getProject());
	}

	@Test
	public void testGetSetProject() throws CoreException {
		// Given
		EmbeddedServerNature nature = new EmbeddedServerNature();
		if(project.exists()) {
			project.delete(true, null);
		}
		project.create(null);
		project.open(null);

		// When
		nature.setProject(project);

		// Then
		Assert.assertEquals(project, nature.getProject());
	}

}
