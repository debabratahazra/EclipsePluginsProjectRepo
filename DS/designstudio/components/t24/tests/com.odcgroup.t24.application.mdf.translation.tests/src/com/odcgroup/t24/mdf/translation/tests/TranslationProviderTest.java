package com.odcgroup.t24.mdf.translation.tests;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.JavaCore;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.mdf.model.translation.T24MdfTranslation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.workbench.core.OfsCore;

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
		description.setNatureIds(new String[] { OfsCore.NATURE_ID, JavaCore.NATURE_ID });
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
		MdfClass mdfClass = MdfFactory.eINSTANCE.createMdfClass();

		// Gets the Translation Manager
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("Cannot load the Translation manager", manager != null);

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(mdfClass);
		Assert.assertTrue("Cannot get translations for project ["+project.getName()+"]", 
				translation instanceof T24MdfTranslation);
		
		ITranslationKind[] kinds = translation.getAllKinds();
		Assert.assertTrue("Translation Kind NAME is missing", ArrayUtils.contains(kinds, ITranslationKind.NAME));
		Assert.assertTrue("Translation Kind TEXT is missing", ArrayUtils.contains(kinds, ITranslationKind.TEXT));
		Assert.assertTrue("Translation Kind HEADER1 is missing", ArrayUtils.contains(kinds, ITranslationKind.HEADER1));
		Assert.assertTrue("Translation Kind HEADER2 is missing", ArrayUtils.contains(kinds, ITranslationKind.HEADER2));
		
		
	}

}
