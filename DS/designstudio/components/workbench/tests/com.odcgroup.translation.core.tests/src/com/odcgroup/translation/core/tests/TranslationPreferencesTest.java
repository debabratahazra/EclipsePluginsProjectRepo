package com.odcgroup.translation.core.tests;

import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.TranslationCore;

/**
 *
 * @author atr
 *
 */
public class TranslationPreferencesTest {

	private IProject project;
	
	@Before
	public void setUp() throws Exception {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject("test");
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = {  "org.eclipse.jdt.core.javanature" };
		description.setNatureIds(natures);
		project.setDescription(description, null);
	}

	@After
	public void tearDown() throws Exception {
		if (project != null) {
			project.delete(true, null);
		}
	}
	
	@Test
	public void testGetDefaultLocale() {
		
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("Cannot get translation manager for project "+project.getName(), manager != null);
		
		ITranslationPreferences prefs = manager.getPreferences();
		Assert.assertTrue("Cannot get translation preferences for project "+project.getName(), prefs != null);
		
		Locale locale = prefs.getDefaultLocale();
		Assert.assertTrue("Wrong default locale",Locale.ENGLISH.equals(locale));
		

	}
	
	@Test
	public void testGetAdditionalLocales() {
		
		ITranslationManager manager = TranslationCore.getTranslationManager(project);
		Assert.assertTrue("Cannot get translation manager for project "+project.getName(), manager != null);
		
		ITranslationPreferences prefs = manager.getPreferences();
		Assert.assertTrue("Cannot get translation preferences for project "+project.getName(), prefs != null);

		List<Locale> locales = prefs.getAdditionalLocales();
		Assert.assertTrue("Wrong number of additional locales", locales.size() == 2);
		Assert.assertTrue("Wrong 1st additional locale",Locale.FRENCH.equals(locales.get(0)));
		Assert.assertTrue("Wrong 2nd additional locale",Locale.GERMAN.equals(locales.get(1)));
		

	}

}
