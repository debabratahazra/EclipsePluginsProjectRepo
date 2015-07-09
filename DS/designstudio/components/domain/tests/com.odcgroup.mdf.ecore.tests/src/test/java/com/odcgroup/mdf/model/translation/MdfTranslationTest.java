package com.odcgroup.mdf.model.translation;

import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Test MdfTranslation 
 * @author yan
 */
public class MdfTranslationTest {
	
	protected IProject project;
	protected MdfDomainImpl domain;
	protected MdfClassImpl mdfClass;
	protected MdfTranslation translation;
	protected int translationChangedFired;
	protected int isProtectedCalled;

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws Exception {
		
		// create a dummy project with the OfsNature.
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = { OfsCore.NATURE_ID };
		description.setNatureIds(natures);
		project.setDescription(description, null);

		// create a dummy domain
		domain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("dummyDomain");
		mdfClass = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		mdfClass.setName("NewClass");
		domain.getClasses().add(mdfClass);
		translation = new MdfTranslation(new MdfTranslationProvider(), project, mdfClass) {
			// Override this to count the number of translation change
			// event thrown
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}
			
		};
		
		MdfTranslationHelper.instance = new MdfTranslationHelper() {
			public boolean isProtected(IMdfTranslationHelperAdapter helperAdapter) {
				isProtectedCalled++;
				return super.isProtected(helperAdapter);
			}
		};
	}
	
	@After
	public void tearDown() throws Exception  {
		translation = null;
		translationChangedFired = 0;
		isProtectedCalled = 0;
		if (project != null) {
			project.delete(true, null);
		}
	}
	
	@Test
	public void testConstructor() {
		try {
			new MdfTranslation(null, null, null);
		} catch (IllegalArgumentException e) {
			return; // test ok
		}
		Assert.fail();
	}
	
	@Test
	public void testGetOwner() {
		MdfClassImpl mdfClass = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		mdfClass.setName("NewClass");
		MdfTranslation translation = new MdfTranslation(new MdfTranslationProvider(), project, mdfClass);
		Assert.assertEquals("The owner is not properly set", mdfClass, translation.getOwner());
	}
	
	@Test
	public void testDelete() throws TranslationException {
		final String TEXT = "text in english";
		translation = new MdfTranslation(new MdfTranslationProvider(), project, mdfClass);
		translation.setText(ITranslationKind.NAME, Locale.ENGLISH, TEXT);
		String deletedText = translation.delete(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text has not been property deleted", TEXT, deletedText);
		String text = translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text must be null", null, text);
	}
	
//	@Test
//	public void testAnnotationNameFromKind() throws TranslationException {
//		Assert.assertEquals("Label", MdfTranslationHelper.instance.annotationFromKind(ITranslationKind.NAME));
//		Assert.assertEquals("Tooltip", MdfTranslationHelper.instance.annotationFromKind(ITranslationKind.TEXT));
//	}
	
	@Test
	public void testPropertyNameFromLocale() throws TranslationException {
		Assert.assertEquals("en", MdfTranslationHelper.getInstance().propertyNameFromLocale(Locale.ENGLISH));
		Assert.assertEquals("fr", MdfTranslationHelper.getInstance().propertyNameFromLocale(Locale.FRENCH));
	}
	
	@Test
	public void testIsReadOnly() {
		Assert.assertFalse("The translation shouldn't be readonly", translation.isReadOnly());
		
		domain.setName("aaaentities");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), translation.isReadOnly());
		
		domain.setName("aaaenumerations");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), translation.isReadOnly());
		
		domain.setName("aaaformats");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), translation.isReadOnly());
	}
	
	@Test
	public void testIsProtected_DS4024ImportationOfTranslationExcelFileFails() {
		Assert.assertEquals(0, isProtectedCalled);
		translation.isReadOnly();
		Assert.assertEquals(1, isProtectedCalled);
	}
	
	@Test
	public void testGetTextAndSetText() throws TranslationException {
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		
		String oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français");
		
		Assert.assertEquals("Should have a translation yet", "texte en français", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", null, oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
		
		oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français v2");

		Assert.assertEquals("Should have a translation yet", "texte en français v2", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", "texte en français", oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
	}
	
	@Test
	public void testSetTextNoEffect() throws TranslationException {
		translation.setText(ITranslationKind.TEXT, Locale.ENGLISH, "text in english");
		Assert.assertEquals("The TEXT translation shouldn't be saved for MdfClass", null, translation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
	}
	
}
