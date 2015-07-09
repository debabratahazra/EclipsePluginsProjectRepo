package com.odcgroup.page.translation.generation.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Before;

/**
 * @author atr
 */
public class TranslationKeyTest {
	
	IProject project;
	
	protected final IProject getProject() {
		return project;
	}

	@Before
	public void setUp() throws Exception {
		// create a dummy project with the OfsNature.
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = { "org.eclipse.jdt.core.javanature"  };
		description.setNatureIds(natures);
		project.setDescription(description, null);	
	}

	@After
	public void tearDown() throws Exception  {
		if (project != null) {
			project.delete(true, null);
		}
	}
	
}
