package com.odcgroup.visualrules.integration.tests.api;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.RulePackage;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class ReuseConfigurationSettingsTest {
	private IProject ruleProject;
	private IDataModelIntegration dataModelIntegration;
	private IRuleIntegration ruleIntegration;

	@Before
	public void setUp() throws Exception {
		ruleProject = IntegrationCore.createRuleProject("ReuseConfigurationSettingsTest", null);
		dataModelIntegration = VisualRulesIntegration
				.getDataModelIntegration(ruleProject);
		ruleIntegration = VisualRulesIntegration
				.getRuleIntegration(ruleProject);
		ruleIntegration.createRuleModel(ruleProject, "rules_123", null);
	}

	@After
	public void tearDown() throws Exception {
		ruleProject.delete(true, null);
	}

	@Test
	public void testEditReuseSettings() throws Exception {
		RulePackage pkg1 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg1.setName("package");
		RulePackage pkg2 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg2.setName("package");

		dataModelIntegration.merge(pkg1, "vrpath:/rules_123", null);
		dataModelIntegration.addPackageReuse("vrpath:/rules_123",
				"vrpath:/rules_123/package");
		dataModelIntegration.save("vrpath:/rules_123");

		dataModelIntegration.deletePackage("vrpath:/rules_123/package");
		dataModelIntegration.merge(pkg2, "vrpath:/rules_123", null);
		List<String> imports = dataModelIntegration
				.getPackageReuses("vrpath:/rules_123");

		Assert.assertEquals(1, imports.size());

	}

}
