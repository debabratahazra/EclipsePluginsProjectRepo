package com.odcgroup.translation.core.tests;

import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationChangeEvent;
import com.odcgroup.translation.core.ITranslationChangeListener;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.core.tests.model.SimpleObject;

/**
 * @author atr
 */
public class TranslationTest {

	private IProject project;
	private ITranslationManager manager = null;

	@Before
	public void setUp() throws Exception {

		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = {  "org.eclipse.jdt.core.javanature" };
		description.setNatureIds(natures);
		project.setDescription(description, null);
		
		// Gets the Translation Manager
		manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("The Translation manager cannot be loaded.", manager != null);
	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
	}

	@Test
	public void testReadTranslation() {

		final Locale ENGLISH = Locale.ENGLISH;
		final String EXPECTED_TEXT = "Hello";

		// create a simple localizable object
		SimpleObject so = new SimpleObject();
		so.setLabel(ENGLISH, EXPECTED_TEXT);
		Assert.assertTrue("Cannot set Label on SimpleObject", EXPECTED_TEXT.equals(so.getLabel(ENGLISH)));

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(so);
		Assert.assertTrue("The Translation support cannot be loaded.", translation != null);

		// Gets the name in english
		try {
			String label = translation.getText(ITranslationKind.NAME, ENGLISH);
			Assert.assertTrue("The translation cannot be null", label != null);
			Assert.assertTrue("Wrong translation: " + label + " , expected:" + EXPECTED_TEXT, EXPECTED_TEXT.equals(label));
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Raised an unexpected TranslationException");
		}			

	}

	@Test
	public void testChangeTranslationWithoutListener() {

		final Locale ENGLISH = Locale.ENGLISH;
		final String EXPECTED_OLD_TEXT = "Hello";
		final String EXPECTED_NEW_TEXT = "Bye";

		// create a simple localizable object
		SimpleObject so = new SimpleObject();
		so.setLabel(ENGLISH, EXPECTED_OLD_TEXT);
		Assert.assertTrue("Cannot set Label on SimpleObject", EXPECTED_OLD_TEXT.equals(so.getLabel(ENGLISH)));

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(so);
		Assert.assertTrue("The Translation support cannot be loaded.", translation != null);

		// Changes the translation
		try {
			String oldLabel = translation.setText(ITranslationKind.NAME, ENGLISH, EXPECTED_NEW_TEXT);
			Assert.assertTrue("Wrong old translation:" + oldLabel + " expected:" + EXPECTED_OLD_TEXT, 
					EXPECTED_OLD_TEXT.equals(oldLabel));
			Assert.assertTrue("The Label has not been changed on SimpleObject", 
					EXPECTED_NEW_TEXT.equals(so.getLabel(ENGLISH)));
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Raised an unexpected TranslationException");
		}
	}

	@Test
	public void testChangeTranslationWithGenericListener() {

		final Locale ENGLISH = Locale.ENGLISH;
		final String TEXT = "Hello";
		final String NEW_TEXT = "Bye";

		// create a simple localizable object
		SimpleObject so = new SimpleObject();
		so.setLabel(ENGLISH, TEXT);

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(so);

		final boolean[] notified = { false };
		translation.addTranslationChangeListener(new ITranslationChangeListener() {
			public void notifyChange(ITranslationChangeEvent event) {
				notified[0] = true;
			}
		});

		// Changes the translation
		try {
			translation.setText(ITranslationKind.NAME, ENGLISH, NEW_TEXT);
			Assert.assertTrue("The TranslationChangeListener has not been notified", true == notified[0]);
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Raised an unexpected TranslationException");
		}

	}

	@Test
	public void testChangeTranslationWithSpecificListener() {

		final Locale ENGLISH = Locale.ENGLISH;
		final String TEXT = "Hello";
		final String NEW_TEXT = "Bye";

		// create a simple localizable object
		SimpleObject so = new SimpleObject();
		so.setLabel(ENGLISH, TEXT);

		// Gets translation support for the object
		ITranslation translation = manager.getTranslation(so);

		final boolean[] notifiedName = { false };
		translation.addTranslationChangeListener(ITranslationKind.NAME, new ITranslationChangeListener() {
			public void notifyChange(ITranslationChangeEvent event) {
				if (ITranslationKind.NAME.equals(event.getKind())) {
					notifiedName[0] = true;
				}
			}
		});
		
		try {

			// Changes the NAME 
			for (int kx = 0; kx < 1000; kx++) {
				translation.setText(ITranslationKind.NAME, ENGLISH, NEW_TEXT+kx);
				Assert.assertTrue("The TranslationChangeListener on "+ITranslationKind.NAME+" has not been notified", true == notifiedName[0]);
			}
	
			// Redo the same changes on NAME 
			notifiedName[0] = false;
			translation.setText(ITranslationKind.TEXT, ENGLISH, NEW_TEXT);
			Assert.assertTrue("The TranslationChangeListener on "+ITranslationKind.NAME+" must no be invoked", false == notifiedName[0]);
	
			// Changes the TEXT
			final boolean[] notifiedText = { false };
			translation.addTranslationChangeListener(ITranslationKind.TEXT, new ITranslationChangeListener() {
				public void notifyChange(ITranslationChangeEvent event) {
					if (ITranslationKind.TEXT.equals(event.getKind())) {
						notifiedText[0] = true;
					}
				}
			});
	
			// Changes the translation
			translation.setText(ITranslationKind.NAME, ENGLISH, NEW_TEXT);
			Assert.assertTrue("The TranslationChangeListener on "+ITranslationKind.TEXT+" must not be invoked", false == notifiedText[0]);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Raised an unexpected TranslationException");
		}
	}
}
