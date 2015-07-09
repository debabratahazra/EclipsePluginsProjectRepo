package com.odcgroup.edge.t24.generation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.edge.t24ui.model.TranslationUtil;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;


public class TranslationTest extends AbstractDSUnitTest{
	
	@Before
	public void setUp() {
		createModelsProject();
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testInterestLanguages(){
		List<Locale> locales = TranslationUtil.getInterestLanguages(getProject());
		assertNotNull(locales);
		assertTrue(locales.size()!=0);
		assertTrue(locales.size() ==2);
	}
	@Test
	public void testDefaultLanguage(){
		Locale locale = TranslationUtil.getDefaultLanguage(getProject());
		assertNotNull(locale);
		assertTrue(locale.getDisplayName().equals("English"));
	}

}
