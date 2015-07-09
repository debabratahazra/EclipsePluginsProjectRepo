package com.odcgroup.mdf.model.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.mdf.ecore.MdfAttributeImpl;
import com.odcgroup.mdf.ecore.MdfClassImpl;
import com.odcgroup.mdf.ecore.MdfDatasetImpl;
import com.odcgroup.mdf.ecore.MdfDatasetMappedPropertyImpl;
import com.odcgroup.mdf.ecore.MdfDomainImpl;
import com.odcgroup.mdf.ecore.MdfFactory;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;

/**
 * Test MdfTranslation 
 * @author yan
 */
public class MdfInheritableTranslationTest {
	
	private static int id;
	
	private IProject project;
	private MdfDomainImpl domain;
	private MdfClassImpl mdfClass;
	private MdfAttributeImpl mdfAttribute;
	private MdfDatasetImpl mdfDataset;
	private MdfDatasetMappedPropertyImpl mdfDSMappedProperty;
	private MdfInheritableTranslationProvider inheritableTranslationProvider;
	private MdfInheritableTranslation datasetTranslation;
	private MdfInheritableTranslation datasetMappedPropertyTranslation;
	private int translationChangedFired;
	private int isProtectedCalled;
	
	private List<ITranslation> translationToRelease = new ArrayList<ITranslation>();

	@Before
	@SuppressWarnings("unchecked")
	public void setUp() throws Exception {
		
		// create a dummy project with the OfsNature.
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = { "org.eclipse.jdt.core.javanature" };
		description.setNatureIds(natures);
		project.setDescription(description, null);

		// create a domain
		domain = (MdfDomainImpl)MdfFactory.eINSTANCE.createMdfDomain();
		domain.setName("dummyDomain");
		
		// with a class
		mdfClass = (MdfClassImpl)MdfFactory.eINSTANCE.createMdfClass();
		mdfClass.setName("MyClass" + id++);
		domain.getClasses().add(mdfClass);
		mdfAttribute = (MdfAttributeImpl)MdfFactory.eINSTANCE.createMdfAttribute();
		mdfAttribute.setName("classAttrib");
		mdfClass.getProperties().add(mdfAttribute);
		
		// and with a dataset
		mdfDataset = (MdfDatasetImpl)MdfFactory.eINSTANCE.createMdfDataset();
		mdfDataset.setName("NewDataset");
		mdfDataset.setBaseClass(mdfClass);
		domain.getDatasets().add(mdfDataset);
		mdfDSMappedProperty = (MdfDatasetMappedPropertyImpl)MdfFactory.eINSTANCE.createMdfDatasetMappedProperty();
		mdfDSMappedProperty.setName("datasetAttrib");
		mdfDSMappedProperty.setPath("classAttrib");
		mdfDataset.getProperties().add(mdfDSMappedProperty);
		
		inheritableTranslationProvider = new MdfInheritableTranslationProvider();
		datasetTranslation = createMdfInheritableTranslation(inheritableTranslationProvider, project, mdfDataset);
		
		datasetMappedPropertyTranslation = createMdfInheritableTranslation(inheritableTranslationProvider, project, mdfDSMappedProperty);
		
		MdfTranslationHelper.instance = new MdfTranslationHelper() {
			@Override
			public boolean isProtected(IMdfTranslationHelperAdapter helperAdapter) {
				isProtectedCalled++;
				return super.isProtected(helperAdapter);
			}
			
		};

	}
	
	@After
	public void tearDown() throws Exception  {
		isProtectedCalled = 0;
		if (project != null) {
			project.delete(true, null);
		}
	}
	
	private MdfInheritableTranslation createMdfInheritableTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		MdfInheritableTranslation translation = new MdfInheritableTranslation(provider, project, mdfModelElement) {
			// Override this to count the number of translation change
			// event thrown
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}
		};
		translationToRelease.add(translation);
		return translation;
	}
	
	private MdfTranslation createMdfTranslation(ITranslationProvider provider, IProject project, MdfModelElement mdfModelElement) {
		MdfTranslation translation = new MdfTranslation(provider, project, mdfModelElement);
		translationToRelease.add(translation);
		return translation;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		createMdfInheritableTranslation(null, null, null);
	}
	
	@Test
	public void testGetOwner() {
		MdfDataset mdfDataset = MdfFactory.eINSTANCE.createMdfDataset();
		MdfInheritableTranslation translation = createMdfInheritableTranslation(inheritableTranslationProvider, project, mdfDataset);
		Assert.assertEquals("The owner is not properly set", mdfDataset, translation.getOwner());
	}
	
	@Test
	public void testDelete() throws TranslationException {
		final String TEXT = "text in english";
		datasetTranslation = createMdfInheritableTranslation(inheritableTranslationProvider, project, mdfDataset);
		datasetTranslation.setText(ITranslationKind.NAME, Locale.ENGLISH, TEXT);
		String deletedText = datasetTranslation.delete(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text has not been property deleted", TEXT, deletedText);
		String text = datasetTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text must be null", null, text);
	}
	
	@Test
	public void testIsReadOnly() throws TranslationException {
		Assert.assertFalse("The translation shouldn't be readonly", datasetTranslation.isReadOnly());
		
		domain.setName("aaaentities");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), datasetTranslation.isReadOnly());
		
		domain.setName("aaaenumerations");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), datasetTranslation.isReadOnly());
		
		domain.setName("aaaformats");
		Assert.assertTrue("The translation should be readonly for " + domain.getName(), datasetTranslation.isReadOnly());
	}
	
	@Test
	public void testIsProtected_DS4024ImportationOfTranslationExcelFileFails() throws TranslationException {
		Assert.assertEquals(0, isProtectedCalled);
		datasetTranslation.isReadOnly();
		Assert.assertEquals(1, isProtectedCalled);
	}
	
	@Test
	public void testGetTextAndSetText() throws TranslationException {
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		
		String oldValue = datasetTranslation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français");
		
		Assert.assertEquals("Should have a translation yet", "texte en français", datasetTranslation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", null, oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
		
		oldValue = datasetTranslation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français v2");

		Assert.assertEquals("Should have a translation yet", "texte en français v2", datasetTranslation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("Shouldn't have a translation yet", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", "texte en français", oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
	}
	
	@Test
	public void testSetTextNoEffect() throws TranslationException {
		datasetTranslation.setText(ITranslationKind.TEXT, Locale.ENGLISH, "text in english");
		Assert.assertEquals("The TEXT translation shouldn't be saved for MdfClass", null, datasetTranslation.getText(ITranslationKind.TEXT, Locale.ENGLISH));
	}
	
	@Test
	public void testInheritedDataset() throws TranslationException {
		MdfTranslation classTranslation = createMdfTranslation(new MdfTranslationProvider(), project, mdfClass);
		classTranslation.setText(ITranslationKind.NAME, Locale.ENGLISH, "Class comment");

		Assert.assertEquals("The translation should be set in the class", 
				"Class comment", classTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The translation should be inherited from the class", 
				"Class comment", datasetTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
	}
	
	@Test
	public void testInheritedDatasetMappedProperty() throws TranslationException {
		MdfTranslation attributeTranslation = createMdfTranslation(new MdfTranslationProvider(), project, mdfAttribute);
		attributeTranslation.setText(ITranslationKind.NAME, Locale.ENGLISH, "Attribute comment");

		Assert.assertEquals("The translation should be set in the attribute", 
				"Attribute comment", attributeTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The translation should be inherited from the class", 
				"Attribute comment", datasetMappedPropertyTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH));
	}
	
}
