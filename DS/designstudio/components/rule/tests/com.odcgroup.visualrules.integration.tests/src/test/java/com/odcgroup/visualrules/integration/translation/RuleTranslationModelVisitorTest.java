package com.odcgroup.visualrules.integration.translation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;

@Ignore
public class RuleTranslationModelVisitorTest extends AbstractRuleUnitTest {

	private int counter = 0;

	@Before
	public void setUp() throws Exception {
		createRuleProject();
		RuleTranslationRepo repo = RulesIntegrationUtils.getRulesTranslationModel(getProject());
		repo.getCodeMap().put("1", RuletranslationFactory.eINSTANCE.createRuleMessageProxy());
		repo.getCodeMap().put("2", RuletranslationFactory.eINSTANCE.createRuleMessageProxy());
		repo.getCodeMap().put("3", RuletranslationFactory.eINSTANCE.createRuleMessageProxy());
		repo.eResource().save(null);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testVisit() {
		counter = 0;
		ITranslationModelVisitorHandler handler = new ITranslationModelVisitorHandler() {			
			public void notify(ITranslation translation) {
				counter++;
			}
		};
		RuleTranslationRepo repo = RulesIntegrationUtils.getRulesTranslationModel(getProject());
		RuleTranslationModelVisitor visitor = new RuleTranslationModelVisitor(getProject(), repo);
		visitor.visit(handler);
		Assert.assertEquals(3, counter);
	}

}
