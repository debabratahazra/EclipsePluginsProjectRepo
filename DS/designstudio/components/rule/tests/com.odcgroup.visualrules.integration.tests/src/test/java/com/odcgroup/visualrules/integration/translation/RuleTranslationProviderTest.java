package com.odcgroup.visualrules.integration.translation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;

@Ignore
public class RuleTranslationProviderTest extends AbstractRuleUnitTest {

	@Before
	public void setUp() throws Exception {
		createRuleProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGetTranslation() {
		RuleMessageProxy msg = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
		msg.setCode("123");
		RuleTranslationProvider provider = new RuleTranslationProvider();
		ITranslation translation = provider.getTranslation(getProject(), msg);
		
		Assert.assertTrue(translation instanceof RuleTranslation);
		Assert.assertEquals(msg.getCode(), ((RuleMessageProxy)translation.getOwner()).getCode());
	}

}
