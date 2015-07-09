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

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.translation.WidgetTranslation;
import com.odcgroup.page.model.translation.WidgetTranslationProvider;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class WidgetTranslationUnitTest extends AbstractDSUnitTest {

	private Widget label;
	private WidgetTranslation translation;
	private int translationChangedFired;
	private IOfsModelResource fragmentResource;

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("domain/ds3327/DS-3327.mml", 
				"fragment/ds3327/DS3327.fragment");

		// Plug the standard model as a resource of the custo project
		URI fragmentUri = URI.createURI("resource:///ds3327/DS3327.fragment");
		fragmentResource = getOfsProject().getOfsModelResource(fragmentUri);

		Model model = (Model)fragmentResource.getEMFModel().get(0);
		Widget fragment = model.getWidget();
		Widget box = fragment.getContents().get(0);
		label = box.getContents().get(1);
		
		WidgetTranslationProvider provider = new WidgetTranslationProvider();
		translation = new WidgetTranslation(provider, getProject(), label) {
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}
		};
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
		label = null;
		translation = null;
		translationChangedFired = 0;
	}	
	
	@Test
	public void testGetAllKind() throws TranslationException {
		List<ITranslationKind> allKinds = Arrays.asList(translation.getAllKinds());
		Assert.assertEquals("The number of kinds supported for Widget translation is wrong", 2, allKinds.size());
		Assert.assertTrue("This kind should be present for Widget translation", allKinds.contains(ITranslationKind.NAME));
		Assert.assertTrue("This kind should be present for Widget translation", allKinds.contains(ITranslationKind.TEXT));
		
		translation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		translation.getText(ITranslationKind.TEXT, Locale.ENGLISH);
		try {
			translation.getText(ITranslationKind.TEXT, null);
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
		Assert.assertTrue("The widget translation should be readonly", translation.isReadOnly());
		
		attributes.setReadOnly(false);
		fragmentResource.getResource().setResourceAttributes(attributes);
		Assert.assertFalse("The widget translation shouldn't be readonly", translation.isReadOnly());
	}
	
	@Test
	public void testGetOwner() {
		Assert.assertEquals("getOwner is wrong for Widget translation", label, translation.getOwner());
	}
	
	@Test
	public void testGetTextAndSetText() throws TranslationException {
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		
		String oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français");
		
		Assert.assertEquals("Should have a translation yet", "texte en français", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", null, oldValue);
		Assert.assertEquals("a translation changed  should have been fired", 
				1, translationChangedFired);
		translationChangedFired = 0; // reset the value
		
		oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français v2");

		Assert.assertEquals("Should have a translation yet", "texte en français v2", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		Assert.assertEquals("The old value is not correct", "texte en français", oldValue);
		Assert.assertEquals("a translation changed widget should have been fired", 
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
	
}
