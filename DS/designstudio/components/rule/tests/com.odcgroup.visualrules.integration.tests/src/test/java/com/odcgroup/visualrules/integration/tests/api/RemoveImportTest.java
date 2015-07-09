/*
 * Copyright (C) 2006 Innovations Softwaretechnologie GmbH, Immenstaad, Germany. All rights reserved.
 */

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

public class RemoveImportTest {
	private IProject ruleProject;
	private IDataModelIntegration dataModelIntegration;
	private IRuleIntegration ruleIntegration;

	@Before
	public void setUp() throws Exception {

		ruleProject = IntegrationCore.createRuleProject("RemoveImportTest", null);
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
	public void testInternalReuse() throws Exception {

		// set up packages
		RulePackage pkg1 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg1.setName("package1");
		dataModelIntegration.merge(pkg1, "vrpath:/rules_123", null);

		// add import
		dataModelIntegration.addPackageReuse("vrpath:/rules_123",
				"vrpath:/rules_123/package1");
		List<String> imports = dataModelIntegration
				.getPackageReuses("vrpath:/rules_123");
		Assert.assertTrue(imports.contains("vrpath:/rules_123/package1"));

		// remove package2
		dataModelIntegration
				.deletePackage("vrpath:/rules_123/package1/package2");
		imports = dataModelIntegration.getPackageReuses("vrpath:/rules_123");
		Assert.assertTrue(imports.contains("vrpath:/rules_123/package1"));
	}

}
