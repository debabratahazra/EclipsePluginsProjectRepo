package com.odcgroup.t24.version.model.translation.tests;

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.t24.version.translation.FieldTranslation;
import com.odcgroup.t24.version.translation.FieldTranslationProvider;
import com.odcgroup.t24.version.versionDSL.Field;
import com.odcgroup.t24.version.versionDSL.Version;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;
import com.odcgroup.t24.version.versionDSL.VersionDSLPackage;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Tests for Version and Field Translation interfaces 
 */

public class FieldTranslationTest extends AbstractDSUnitTest{
	
	
	private Field field;
	private Version version;
	private MdfAttribute attribute;
	private MdfClass application;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		
		application = MdfFactory.eINSTANCE.createMdfClass();
		attribute = MdfFactory.eINSTANCE.createMdfAttribute();
		((MdfAttributeImpl)attribute).setName("name");
		application.getProperties().add(attribute);
		
		version = VersionDSLFactory.eINSTANCE.createVersion();
		version.setForApplication(application);
		field = VersionDSLPackage.eINSTANCE.getVersionDSLFactory().createField();
		field.setName("name");
		version.getFields().add(field);
	}
	
	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}
	
	@Test
	public void testTranslationProvider() throws TranslationException {
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);
		Assert.assertTrue(translation instanceof FieldTranslation);
	}
	
	@Test
	public void testTranslationKinds() throws TranslationException {
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);
		ITranslationKind[] kinds = translation.getAllKinds();
		Assert.assertTrue("Translation Kind NAME is missing", ArrayUtils.contains(kinds, ITranslationKind.NAME));
		Assert.assertTrue("Translation Kind TEXT is missing", ArrayUtils.contains(kinds, ITranslationKind.TEXT));
	}

	@Test
	public void testEmptyTranslation() throws TranslationException {
		
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);
		
		Assert.assertTrue(translation.getOwner() == field);
		
		for (ITranslationKind kind : translation.getAllKinds()) {
			Assert.assertEquals(translation.getText(kind, Locale.ENGLISH), null);
		}

	}
	
	@Test
	public void testInheritableFlag()  throws TranslationException {
		
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);

		Assert.assertTrue(translation.isInheritable());

		for (ITranslationKind kind : translation.getAllKinds()) {
			Assert.assertFalse(translation.isInherited(kind, Locale.ENGLISH));
		}
	
	}

	@Test
	public void testInheritedFlagWithoutInheritedTranslation()  throws TranslationException {

		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);
		for (ITranslationKind kind : translation.getAllKinds()) {
			Assert.assertFalse(translation.isInherited(kind, Locale.ENGLISH));
		}
	
	}

	@Test
	public void testInheritedFlagWithInheritedTranslation()  throws TranslationException {
		
		IProject project = getOfsProject().getProject();
		
		ITranslationManager manager = TranslationCore.getTranslationManager(getProject());
		ITranslation attrTranslation = manager.getTranslation(attribute);
		

		final String expectedName = "Name";
		attrTranslation.setText(ITranslationKind.NAME, Locale.ENGLISH, expectedName);
		
		final String expectedText = "Text";
		attrTranslation.setText(ITranslationKind.TEXT, Locale.ENGLISH, expectedText);

		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);

		for (ITranslationKind kind : translation.getAllKinds()) {
			if (kind.equals(ITranslationKind.NAME) || kind.equals(ITranslationKind.TEXT)) {
				// only translation kind Name/Text can be inherited from the domain
				Assert.assertTrue(translation.isInherited(kind, Locale.ENGLISH));
			} else {
				Assert.assertFalse(translation.isInherited(kind, Locale.ENGLISH));
			}
		}

		String text = translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals(expectedName, text);
		
		text = translation.getText(ITranslationKind.TEXT, Locale.ENGLISH);
		Assert.assertEquals(expectedText, text);
	}

	@Test
	public void testUpdateFieldTranslations() throws TranslationException {
		
		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);

		Assert.assertTrue(translation.isInheritable());

		// set and get translation for all supported kinds
		int val = 100;
		for (ITranslationKind kind : translation.getAllKinds()) {
			String expectedText = kind.toString()+val++;
			translation.setText(kind, Locale.ENGLISH, expectedText);
			String text = translation.getText(kind, Locale.ENGLISH);
			Assert.assertEquals(expectedText, text);
		}

	}
	
	@Test
	public void testDeleteFieldTranslations() throws TranslationException {

		IProject project = getOfsProject().getProject();
		ITranslationProvider provider = new FieldTranslationProvider(); 
		ITranslation translation = provider.getTranslation(project, field);

		Assert.assertTrue(translation.isInheritable());

		// set and get translation for all kinds
		int val = 100;
		for (ITranslationKind kind : translation.getAllKinds()) {
			String expectedText = kind.toString()+val++;
			translation.setText(kind, Locale.ENGLISH, expectedText);
			String text = translation.getText(kind, Locale.ENGLISH);
			// ensure the text has been stored in the object
			Assert.assertEquals(expectedText, text);
			// delete the translation
			translation.delete(kind, Locale.ENGLISH);
			text = translation.getText(kind, Locale.ENGLISH);
			Assert.assertNull("The translation "+expectedText+" has not been deleted", text);
			
		}
	}
	
	
}
