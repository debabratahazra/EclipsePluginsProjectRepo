package com.odcgroup.visualrules.integration.translation;

import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.resources.IFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;
import com.odcgroup.workbench.core.IOfsProject;

@Ignore
public class RuleTranslationTest extends AbstractRuleUnitTest {

	private IOfsProject ofsProject;
	private RuleTranslation translation;
	private int translationChangedFired;
	private int isProtectedCalled;
	private RuleMessageProxy msg;
	private RuleTranslationProvider translationProvider;
	private RuleTranslationRepo repo;
	private String key;
	
	@Before
	public void setUp() throws Exception {
		ofsProject = createRuleProject();
		repo = RulesIntegrationUtils.getRulesTranslationModel(getProject());
		msg = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
		key = "123";
		repo.getCodeMap().put(key, msg);
		translationProvider = new RuleTranslationProvider();
		
		translation = new RuleTranslation(new RuleTranslationProvider(), ofsProject.getProject(), msg) {
			// Override this to count the number of translation change
			// event thrown
			protected void fireChangeTranslation(ITranslationKind kind, Locale locale, String oldText, String newText) {
				translationChangedFired++;
			}

			/* (non-Javadoc)
			 * @see com.odcgroup.visualrules.integration.translation.RuleTranslation#isProtected()
			 */
			@Override
			public boolean isProtected() throws TranslationException {
				isProtectedCalled++;
				return super.isProtected();
			}
			
		};
		isProtectedCalled = 0;
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testConstructor() {
		try {
			new RuleTranslation(null, getProject(), null);
		} catch(IllegalArgumentException e) {
			return;
		}
		Assert.fail();
	}
	
	@Test
	public void testDelete() throws TranslationException {
		final String TEXT = "text in english";
		RuleTranslation ruleTranslation = new RuleTranslation(translationProvider, ofsProject.getProject(), msg);
		ruleTranslation.setText(ITranslationKind.NAME, Locale.ENGLISH, TEXT);
		String deletedText = ruleTranslation.delete(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text has not been property deleted", TEXT, deletedText);
		String text = ruleTranslation.getText(ITranslationKind.NAME, Locale.ENGLISH);
		Assert.assertEquals("The text must be null", null, text);
	}

	@Test
	public void testGetAllKinds() {
		Assert.assertEquals(1, translation.getAllKinds().length);
		Assert.assertTrue(ArrayUtils.contains(translation.getAllKinds(), ITranslationKind.NAME));
	}

	@Test
	public void testGetOwner() {
		RuleTranslation ruleTranslation = new RuleTranslation(translationProvider, ofsProject.getProject(), msg);
		Assert.assertEquals(msg, ruleTranslation.getOwner());
	}
	
	@Test
	public void testGetTextAndSetText() throws TranslationException {
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
		
		String oldValue = translation.setText(ITranslationKind.NAME, Locale.FRENCH, "texte en français");
		
		Assert.assertEquals("Should have a translation now", "texte en français", translation.getText(ITranslationKind.NAME, Locale.FRENCH));
		Assert.assertEquals("Shouldn't have a translation yet", null, translation.getText(ITranslationKind.NAME, Locale.ENGLISH));
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
	@Ignore
	public void testIsReadOnly() throws TranslationException {
		Assert.assertFalse("The translation shouldn't be readonly", translation.isReadOnly());
		
		IFile ruleFile = RulesIntegrationUtils.getRulesTranslationFile(getProject());
		ruleFile.setReadOnly(true);
		Assert.assertTrue("The translation must be readonly", translation.isReadOnly());
		ruleFile.setReadOnly(false);
		
	}
	
	@Test
	public void testIsProtected_DS4024ImportationOfTranslationExcelFileFails() throws TranslationException {
		Assert.assertEquals(0, isProtectedCalled);
		translation.isReadOnly();
		Assert.assertEquals("isProtected should have been called", 1, isProtectedCalled);
	}
}
