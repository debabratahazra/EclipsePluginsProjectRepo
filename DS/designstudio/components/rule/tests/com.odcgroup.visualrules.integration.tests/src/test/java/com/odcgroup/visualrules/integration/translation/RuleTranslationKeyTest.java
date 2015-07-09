package com.odcgroup.visualrules.integration.translation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;

@Ignore
public class RuleTranslationKeyTest extends AbstractRuleUnitTest {

	private RuleTranslationKey key;

	@Before
	public void setUp() throws Exception {
		createRuleProject();
		
		RuleTranslationProvider provider = new RuleTranslationProvider();
		RuleMessageProxy msg = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
		msg.setCode("123");
		ITranslation translation = provider.getTranslation(getProject(), msg);
		key = new RuleTranslationKey(translation);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testGetKey() {
		Assert.assertEquals("123", key.getKey(ITranslationKind.NAME));
	}
}
