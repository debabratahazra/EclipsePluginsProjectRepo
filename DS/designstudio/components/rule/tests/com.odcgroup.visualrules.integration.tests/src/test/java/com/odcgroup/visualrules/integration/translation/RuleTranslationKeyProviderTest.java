package com.odcgroup.visualrules.integration.translation;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.visualrules.integration.RulesIntegrationUtils;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuletranslationFactory;
import com.odcgroup.visualrules.integration.tests.AbstractRuleUnitTest;

@Ignore
public class RuleTranslationKeyProviderTest extends AbstractRuleUnitTest {

	private ITranslation translation;

	@Before
	public void setUp() throws Exception {
		createRuleProject();
		
		RuleTranslationProvider provider = new RuleTranslationProvider();
		RuleMessageProxy msg = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
		msg.setCode("123");
		translation = provider.getTranslation(getProject(), msg);

	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testGetTranslationKey() {
		RuleTranslationKeyProvider provider = new RuleTranslationKeyProvider();
		ITranslationKey key = provider.getTranslationKey(getProject(), translation);
		
		Assert.assertTrue(key instanceof RuleTranslationKey);
		Assert.assertEquals("123", key.getKey(ITranslationKind.NAME));
	}

	@Test
	public void testDS3542() throws TranslationException {
		RuleTranslationProvider tProvider = new RuleTranslationProvider();
		
		RuleMessageProxy msg1 = RuletranslationFactory.eINSTANCE.createRuleMessageProxy();
		msg1.setCode("1.rule");
		
		ITranslation t1 = tProvider.getTranslation(getProject(), msg1);
		t1.setText(ITranslationKind.NAME, Locale.ENGLISH, "english1");
		t1.setText(ITranslationKind.NAME, Locale.FRENCH, "french1");
		
		RulesIntegrationUtils.getRulesTranslationModel(getProject()).eResource().unload();
		
		RuleTranslationModelVisitorProvider vProvider = new RuleTranslationModelVisitorProvider();
		RuleTranslationRepo repo = RulesIntegrationUtils.getRulesTranslationModel(getProject());
		ITranslationModelVisitor modelVisitor = vProvider.getModelVisitor(getProject(), repo);

		modelVisitor.visit(new ITranslationModelVisitorHandler() {			
			@Override
			public void notify(ITranslation translation) {
				RuleTranslationKeyProvider kProvider = new RuleTranslationKeyProvider();
				ITranslationKey key = kProvider.getTranslationKey(getProject(), translation);
				Assert.assertEquals("1.rule", key.getKey(ITranslationKind.NAME));
			}
		});
	}
}
