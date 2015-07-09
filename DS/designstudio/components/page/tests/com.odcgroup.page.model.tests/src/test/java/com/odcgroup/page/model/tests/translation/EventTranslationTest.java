package com.odcgroup.page.model.tests.translation;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.metamodel.EventType;
import com.odcgroup.page.metamodel.MetaModel;
import com.odcgroup.page.metamodel.util.MetaModelRegistry;
import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.EventTranslation;
import com.odcgroup.page.model.translation.EventTranslationProvider;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class EventTranslationTest extends AbstractDSUnitTest {

	private Event event;
	private EventTranslation translation;
	private int translationChangedFired;
	private int isProtectedCalled;
	private IOfsModelResource fragmentResource;

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/ds3327/DS-3327.domain", 
				"fragment/ds3327/DS3327.fragment");

		// Plug the standard model as a resource of the custo project
		URI fragmentUri = URI.createURI("resource:///ds3327/DS3327.fragment");
		fragmentResource = getOfsProject().getOfsModelResource(fragmentUri);

		Model model = (Model)fragmentResource.getEMFModel().get(0);
		Widget fragment = model.getWidget();
		Widget box = fragment.getContents().get(0);
		Widget textField = box.getContents().get(0);
		event = textField.getEvents().get(0);
		
		EventTranslationProvider provider = new EventTranslationProvider();
		translation = new EventTranslation(provider, getProject(), event) {
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}

			@Override
			public boolean isProtected() throws TranslationException {
				isProtectedCalled++;
				return super.isProtected();
			}
			
		};
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
		event = null;
		translation = null;
		translationChangedFired = 0;
		isProtectedCalled = 0;
	}	
	
	@Test
	public void testGetAllKind() throws TranslationException {
		List<ITranslationKind> allKinds = Arrays.asList(translation.getAllKinds());
		Assert.assertEquals("The number of kinds supported for Event translation is wrong", 1, allKinds.size());
		Assert.assertTrue("This kind should be present for Event translation", allKinds.contains(ITranslationKind.NAME));
		Assert.assertFalse("This kind shouldn't be present for Event translation", allKinds.contains(ITranslationKind.TEXT));
		
		translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		try {
			translation.getText(ITranslationKind.TEXT, Locale.ENGLISH);
			Assert.fail("Should thrown an exception");
		} catch (IllegalArgumentException e) {
			// Ok
		}
	}

	@Test
	public void testIsReadOnly() throws TranslationException, CoreException {
		// Note the test is like if the translation was in a jar (as there is 
		// no resource associated with the model in this unit test setup.
		ResourceAttributes attributes = new ResourceAttributes();
		attributes.setReadOnly(true);
		fragmentResource.getResource().setResourceAttributes(attributes);
		Assert.assertTrue("The event translation should be readonly", translation.isReadOnly());
		
		attributes.setReadOnly(false);
		fragmentResource.getResource().setResourceAttributes(attributes);
		Assert.assertFalse("The event translation shouldn't be readonly", translation.isReadOnly());
	}
	
	@Test
	public void testIsProtected_DS4024ImportationOfTranslationExcelFileAssert() throws TranslationException, CoreException {
		Assert.assertEquals(0, isProtectedCalled);
		translation.isReadOnly();
		Assert.assertEquals(1, isProtectedCalled);
	}
	
	@Test
	public void testGetOwner() {
		Assert.assertEquals("getOwner is wrong for Event translation", event, translation.getOwner());
	}
	
	@Test
	public void testGetTextAndSetText() throws TranslationException {
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		
		String oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français");
		
		Assert.assertEquals("Should have a translation yet", "texte en français", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", null, oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
		
		oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français v2");

		Assert.assertEquals("Should have a translation yet", "texte en français v2", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", "texte en français", oldValue);
		Assert.assertEquals("a translation changed event should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
		
	}

	@Test
	public void testDelete() throws TranslationException {
		final ITranslationKind KIND = ITranslationKind.NAME;
		final Locale LOCALE = Locale.FRENCH;

		Assert.assertNull("Shouldn't have any translation before calling setText for the " +
				"first time", 
				translation.getText(KIND, LOCALE));

		translation.setText(KIND, LOCALE, "test");
		
		Assert.assertNotNull("Should have a translation after setText ", 
				translation.getText(KIND, LOCALE));
		
		translation.delete(KIND, LOCALE);
		
		Assert.assertNull("Shouldn't have any translation atfer delete",
				translation.getText(KIND, LOCALE));
	}
	

	/**
	 * This test ensure that a property of an event does not generated an NPE. 
	 */
	@Test
	public void testDS3673() {
		
		EventTranslationProvider provider = new EventTranslationProvider();
		
		// create events with properties set to their default values
		MetaModel metamodel = MetaModelRegistry.getMetaModel();
		
		// check translation changes for all type of events
		for (EventType et : metamodel.getEventModel().getEventTypes()) {
			
			try {
				Event event = ModelFactory.eINSTANCE.createEvent(et.getName());
				
				// no translation by default
				Assert.assertTrue("New event must not have translations", event.getMessages().size() == 0);
				Assert.assertTrue("Translation id must be zero", event.getTranslationId() == 0);
				
				// add a translation
				ITranslation translation = provider.getTranslation(getProject(), event);
				translation.setText(ITranslationKind.NAME, Locale.ENGLISH, "test");
				Assert.assertTrue("Translation id must be greater than zero when at least one translation is defined", event.getTranslationId() > 0);
	
				// remove translation
				translation.delete(ITranslationKind.NAME, Locale.ENGLISH);
				Assert.assertTrue("Translation id must be zero when no translations are defined", event.getTranslationId() == 0);
			} catch (TranslationException ex) {
				ex.printStackTrace();
				Assert.fail("Unexpected translation exception");
			}
			
		}
	}

}
