package com.odcgroup.visualrules.integration.translation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;

@Ignore
public class RuleTranslationModelVisitorProviderTest extends AbstractRuleUnitTest {

	@Before
	public void setUp() throws Exception {
		createRuleProject();
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGetModelVisitor() {
		RuleTranslationModelVisitorProvider provider = new RuleTranslationModelVisitorProvider();
		RuleTranslationRepo repo = RulesIntegrationUtils.getRulesTranslationModel(getProject());
		ITranslationModelVisitor modelVisitor = provider.getModelVisitor(getProject(), repo);

		Assert.assertTrue(modelVisitor instanceof RuleTranslationModelVisitor);
	}

}
