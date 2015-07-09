package com.odcgroup.t24.version.model.translation.tests;

		

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.t24.version.translation.FieldTranslation;
import com.odcgroup.t24.version.translation.FieldTranslationProvider;
import com.odcgroup.t24.version.translation.VersionTranslation;
import com.odcgroup.t24.version.translation.VersionTranslationProvider;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Test the version and field translation providers
 */
public class VersionTranslationProviderTest extends AbstractDSUnitTest {
	
	@Before
	public void setUp() {
		createModelsProject(); 
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testVersionTranslationProvider() throws TranslationException {
		Version version = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createVersion();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new VersionTranslationProvider(); 

		ITranslation translation = provider.getTranslation(project, version);
		Assert.assertTrue("Wrong translation object returned", translation instanceof VersionTranslation);
		
		boolean exception = false;
		try {
			translation = provider.getTranslation(project, "wrong object type");
		} catch (IllegalArgumentException ex) {
			exception = true;
		}
		Assert.assertTrue("The exception 'IllegalArgumentException' must be raised", exception);
	}

	@Test
	public void testFieldTranslationProvider() throws TranslationException {
		Field field = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createField();
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);
		Assert.assertTrue("Wrong translation object returned", translation instanceof FieldTranslation);
		
		boolean exception = false;
		try {
			translation = provider.getTranslation(project, "wrong object type");
		} catch (IllegalArgumentException ex) {
			exception = true;
		}
		Assert.assertTrue("The exception 'IllegalArgumentException' must be raised", exception);

	}
}
