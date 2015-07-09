package com.odcgroup.process.model.translation;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.model.Process;
import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.TranslationKind;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class ProcessTranslationTest extends AbstractDSUnitTest {

	private ServiceTask serviceTask;
	private UserTask userTask;
	private Process process;
	
	private ProcessTranslation serviceTranslation;
	private ProcessTranslation userTranslation;
	private ProcessTranslation processTranslation;
	private int translationChangedFired;
	private int isProtectedCalled;
	
	private ITranslationProvider provider;
	
	@Before
	public void setUp() {
		createModelsProject();
		provider = new ProcessTranslationProvider();
		
		serviceTask = ProcessFactory.eINSTANCE.createServiceTask();
		serviceTranslation = new ProcessTranslation(
				provider,
				getProject(),
				ProcessElementAdapterFactory.buildAdapter(serviceTask)) {
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}

			@Override
			public boolean isProtected() throws TranslationException {
				isProtectedCalled++;
				return super.isProtected();
			}
			
		};

		userTask = ProcessFactory.eINSTANCE.createUserTask();
		userTranslation = new ProcessTranslation(
				provider,
				getProject(),
				ProcessElementAdapterFactory.buildAdapter(userTask))  {
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}

			@Override
			public boolean isProtected() throws TranslationException {
				isProtectedCalled++;
				return super.isProtected();
			}
		};

		process = ProcessFactory.eINSTANCE.createProcess();
		processTranslation = new ProcessTranslation(
				provider, 
				getProject(),
				ProcessElementAdapterFactory.buildAdapter(process)) {
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}
			@Override
			public boolean isProtected() throws TranslationException {
				isProtectedCalled++;
				return super.isProtected();
			}
		}; 
		translationChangedFired = 0;
		isProtectedCalled = 0;
	}
	
	@After
	public void tearDown() {
		serviceTask = null;
		userTask = null;
		serviceTranslation = null;
		userTranslation = null;
		translationChangedFired = 0;
		isProtectedCalled = 0;
		deleteModelsProjects();
	}

	@Test
	public void testGetAllKind() throws TranslationException {
		testGetAllKind(serviceTranslation, "ServiceTask");
		testGetAllKind(userTranslation, "UserTask");
		testGetAllKind(processTranslation, "Process");
	}
	
	/**
	 * @param task
	 * @param name
	 * @throws TranslationException 
	 */
	private void testGetAllKind(ProcessTranslation translation, String name) {
		List<ITranslationKind> allKinds = Arrays.asList(translation.getAllKinds());
		Assert.assertEquals("The number of kinds supported for " + name + " translation is wrong", 2, allKinds.size());
		Assert.assertTrue("This kind should be present for " + name + " translation", allKinds.contains(ITranslationKind.NAME));
		Assert.assertTrue("This kind should be present for " + name + " translation", allKinds.contains(ITranslationKind.TEXT));
	}

	@Test
	public void testIsReadOnly() throws TranslationException {
		testIsReadOnly(serviceTranslation, "ServiceTask");
		testIsReadOnly(userTranslation, "UserTask");
		testIsReadOnly(processTranslation, "Process");
	}
	
	/**
	 * @param serviceTranslation2
	 * @param string
	 * @throws TranslationException 
	 */
	private void testIsReadOnly(ProcessTranslation translation, String name) throws TranslationException {
		// Note the test is like if the translation was in a jar (as there is 
		// no resource associated with the model in this unit test setup.
		Assert.assertTrue("isReadOnly wrong for " + name + " translation", translation.isReadOnly());
	}

	@Test
	public void testIsProtected_DS4024ImportationOfTranslationExcelFileFails() throws TranslationException, CoreException {
		Assert.assertEquals(0, isProtectedCalled);
		serviceTranslation.isReadOnly();
		Assert.assertEquals(1, isProtectedCalled);
		userTranslation.isReadOnly();
		Assert.assertEquals(2, isProtectedCalled);
		processTranslation.isReadOnly();
		Assert.assertEquals(3, isProtectedCalled);
	}
	
	@Test
	public void testGetOwner() {
		testGetOwner(serviceTask, serviceTranslation, "ServiceTask");
		testGetOwner(userTask, userTranslation, "UserTask");
		testGetOwner(process, processTranslation, "Process");
	}
	
	/**
	 * @param serviceTranslation2
	 * @param string
	 */
	private void testGetOwner(Object expectedOwner, ProcessTranslation translation, String name) {
		Assert.assertEquals("getOwner is wrong for " + name + " translation", expectedOwner, translation.getOwner());
	}

	@Test
	public void testGetTextAndSetText() throws TranslationException {
		testGetTextAndSetText(serviceTranslation, "ServiceTask");
		testGetTextAndSetText(userTranslation, "UserTask");
		testGetTextAndSetText(processTranslation, "Process");
	}
	
	/**
	 * @param serviceTask2
	 * @param serviceTranslation2
	 * @param string
	 * @throws TranslationException 
	 */
	private void testGetTextAndSetText(ProcessTranslation translation, String name) throws TranslationException {
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
	public void testMatchKind() {
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match((ITranslationKind)null, (TranslationKind)null));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(ITranslationKind.NAME, null));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(null, TranslationKind.NAME_LITERAL));
		
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match(ITranslationKind.NAME, TranslationKind.NAME_LITERAL));
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match(ITranslationKind.TEXT, TranslationKind.TEXT_LITERAL));
		
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(ITranslationKind.TEXT, TranslationKind.NAME_LITERAL));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(ITranslationKind.NAME, TranslationKind.TEXT_LITERAL));
	}
	
	@Test
	public void testMatchLocale() {
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match((Locale)null, (String)null));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(Locale.FRENCH, null));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(null, "fr"));
		
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match(Locale.ENGLISH, "en"));
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match(Locale.FRENCH, "fr"));
		Assert.assertTrue(ProcessTranslation.ProcessTranslationHelper.match(Locale.GERMAN, "de"));
		
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(Locale.ENGLISH, "de"));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(Locale.FRENCH, "en"));
		Assert.assertFalse(ProcessTranslation.ProcessTranslationHelper.match(Locale.GERMAN, "fr"));
	}

	@Test
	public void testConvertKind() {
		Assert.assertEquals(TranslationKind.NAME_LITERAL, 
				serviceTranslation.convert(ITranslationKind.NAME));
		Assert.assertEquals(TranslationKind.TEXT_LITERAL, 
				serviceTranslation.convert(ITranslationKind.TEXT));
	}

		
	@Test
	public void testDelete() throws TranslationException {
		testDelete(serviceTask, serviceTranslation, "ServiceTask");
		testDelete(userTask, userTranslation, "UserTask");
		testDelete(process, processTranslation, "Process");
	}
	
	/**
	 * @param serviceTranslation2
	 * @param string
	 * @throws TranslationException 
	 */
	private void testDelete(EObject obj, ProcessTranslation translation, String name) throws TranslationException {
		final ITranslationKind KIND = ITranslationKind.NAME;
		final Locale LOCALE = Locale.FRENCH;

		Assert.assertNull("Shouldn't have any translation before calling setText for the " +
				"first time (" + name + ")", 
				translation.findExistingTranslation(KIND, LOCALE));

		translation.setText(KIND, LOCALE, "test");
		
		Assert.assertNotNull("Should have a translation after setText " +
				" (" + name + ")", 
				translation.findExistingTranslation(KIND, LOCALE));
		
		translation.delete(KIND, LOCALE);
		
		Assert.assertNull("Shouldn't have any translation atfer delete  (" + name + ")",
				translation.findExistingTranslation(KIND, LOCALE));
	}
	
}
