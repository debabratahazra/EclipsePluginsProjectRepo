package com.odcgroup.translation.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.tests.model.SimpleObject;
import com.odcgroup.translation.core.tests.provider.SimpleTranslation;

/**
 * @author atr
 */
public class TranslationProviderTest {

	private IProject project;

	@Before
	public void setUp() throws Exception {

		IProjectDescription description = null;

		// create a normal project
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("project");
		project.create(null);
		project.open(null);
		description = project.getDescription();
		description.setNatureIds(new String[] {  "org.eclipse.jdt.core.javanature" });
		project.setDescription(description, null);

	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
	}

	@Test
	public void testGetTranslations() {

		// create a simple localizable object
		SimpleObject so = new SimpleObject();

		// Gets the Translation Manager
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("Cannot load the Translation manager", manager != null);

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(so);
		Assert.assertTrue("Cannot get translations for project ["+project.getName()+"]", 
				translation instanceof SimpleTranslation);
	}

}
