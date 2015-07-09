package com.odcgroup.translation.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.core.OfsCore;

/**
 * This test case attempts to test the creation of the an ITranslationManager
 * instance.
 * <p>
 * 
 * @author atr
 */
public class TranslationManagerTest {

	private IProject project;
	
	@Before
	public void setUp() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = { OfsCore.NATURE_ID };
		description.setNatureIds(natures);
		project.setDescription(description, null);
	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
	}

	/**
	 * Tests the creation of the implementation by using the service provided by
	 * the com.odcgroup.translation.core plugin.
	 */
	@Test
	public void testGetTranslationManagerViaPlugin() {
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("Cannot get translation manager for project "+project.getName(), manager != null);
	}

}
