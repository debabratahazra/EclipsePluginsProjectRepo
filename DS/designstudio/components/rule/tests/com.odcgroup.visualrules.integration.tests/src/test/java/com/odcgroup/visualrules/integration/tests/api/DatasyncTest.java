package com.odcgroup.visualrules.integration.tests.api;

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.model.Attribute;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.RulePackage;
import de.visualrules.integration.model.VRClass;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class DatasyncTest {
	private IProject ruleProject;
	private IDataModelIntegration dataModelIntegration;
	private IRuleIntegration ruleIntegration;

	@Before
	public void setUp() throws Exception {
		ruleProject = IntegrationCore.createRuleProject("DatasyncTest", null);
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
	public void testMergeDataTypes() throws Exception {
		RulePackage pkgIn = IntegrationFactory.eINSTANCE.createRulePackage();
		pkgIn.setName("package1");
		VRClass clazzIn = IntegrationFactory.eINSTANCE.createVRClass();
		clazzIn.setName("Person");
		Attribute attrIn = IntegrationFactory.eINSTANCE.createAttribute();
		attrIn.setName("name");
		attrIn.setTypeName("String");
		clazzIn.getAttributes().add(attrIn);
		pkgIn.getTypes().add(clazzIn);

		dataModelIntegration.merge(pkgIn, "vrpath:/rules_123", null);

		dataModelIntegration.save("vrpath:/rules_123");

		RulePackage pkgOut = dataModelIntegration
				.getPackage("vrpath:/rules_123/package1");
		VRClass clazzOut = (VRClass) pkgOut.getTypes().get(0);
		Attribute attrOut = (Attribute) clazzOut.getAttributes().get(0);

		Assert.assertEquals("name", attrOut.getName());

	}

}
