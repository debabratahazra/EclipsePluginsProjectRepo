package com.odcgroup.visualrules.integration.tests.api;

import org.eclipse.core.resources.IProject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.visualrules.integration.IDataModelIntegration;
import de.visualrules.integration.IRuleIntegration;
import de.visualrules.integration.IntegrationCore;
import de.visualrules.integration.model.Constant;
import de.visualrules.integration.model.Enumeration;
import de.visualrules.integration.model.IntegrationFactory;
import de.visualrules.integration.model.JavaBeanTypeBinding;
import de.visualrules.integration.model.JavaConstantBinding;
import de.visualrules.integration.model.RulePackage;
import de.visualrules.integration.model.VRClass;
import de.visualrules.ui.integration.VisualRulesIntegration;

public class MergePackageTest {
	private IProject ruleProject;
	private IDataModelIntegration dataModelIntegration;
	private IRuleIntegration ruleIntegration;

	@Before
	public void setUp() throws Exception {
		ruleProject = IntegrationCore.createRuleProject("MergePackageTest",
				null);
		dataModelIntegration = VisualRulesIntegration
				.getDataModelIntegration(ruleProject);
		ruleIntegration = VisualRulesIntegration
				.getRuleIntegration(ruleProject);

		ruleIntegration.createRuleModel(ruleProject, "rules", null);
	}

	@After
	public void tearDown() throws Exception {
		ruleProject.delete(true, null);
	}

	@Test
	public void testMergeEnumeration() throws Exception {

		// set up package with enumeration
		RulePackage pkg1 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg1.setName("package1");
		Enumeration myEnum = IntegrationFactory.eINSTANCE.createEnumeration();
		myEnum.setName("MyEnum");
		myEnum.setExternal(true);
		JavaBeanTypeBinding tBinding = IntegrationFactory.eINSTANCE
				.createJavaBeanTypeBinding();
		tBinding.setFullyQualifiedClassname("a.b.c.D");
		myEnum.getTypeBindings().add(tBinding);
		Constant literal = IntegrationFactory.eINSTANCE.createConstant();
		literal.setName("literal");
		literal.setTypeName("String");
		literal.setLiteral(true);
		JavaConstantBinding cBinding = IntegrationFactory.eINSTANCE
				.createJavaConstantBinding();
		cBinding.setDeclaringClassName("a.b.c.D");
		cBinding.setConstantName("const");
		literal.getConstantBindings().add(cBinding);
		myEnum.getConstants().add(literal);
		pkg1.getTypes().add(myEnum);
		dataModelIntegration.merge(pkg1, "vrpath:/rules", null);

		// read package again and merge it for a second time
		pkg1 = dataModelIntegration.getPackage("vrpath:/rules/package1");
		dataModelIntegration.merge(pkg1, "vrpath:/rules", null);
	}

	@Test
	public void testMergeClassIntoExistingPackage() throws Exception {

		// create a class "A" in "testpackage"
		RulePackage pkg = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg.setName("testpackage");
		pkg.setExternal(true);
		VRClass clazzA = IntegrationFactory.eINSTANCE.createVRClass();
		clazzA.setName("A");
		JavaBeanTypeBinding binding = IntegrationFactory.eINSTANCE
				.createJavaBeanTypeBinding();
		binding.setFullyQualifiedClassname("com.odcgroup.A");
		clazzA.getTypeBindings().add(binding);
		pkg.getTypes().add(clazzA);
		dataModelIntegration.merge(pkg, "vrpath:/rules", null);
		dataModelIntegration.save("vrpath:/rules");

		// check insertion of class A
		RulePackage resultingPackage = dataModelIntegration
				.getPackage("vrpath:/rules/testpackage");
		Assert.assertEquals(1, resultingPackage.getTypes().size());

		// create a class "B" in "testpackage"
		pkg = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg.setName("testpackage");
		pkg.setExternal(true);
		VRClass clazzB = IntegrationFactory.eINSTANCE.createVRClass();
		clazzB.setName("B");
		binding = IntegrationFactory.eINSTANCE.createJavaBeanTypeBinding();
		binding.setFullyQualifiedClassname("com.odcgroup.B");
		clazzB.getTypeBindings().add(binding);
		pkg.getTypes().add(clazzB);
		dataModelIntegration.merge(pkg, "vrpath:/rules", null);
		dataModelIntegration.save("vrpath:/rules");

		// check insertion of class B
		resultingPackage = dataModelIntegration
				.getPackage("vrpath:/rules/testpackage");
		// since VR5.0 we will only find class B in here - class A is removed by
		// merge()
		Assert.assertEquals(1, resultingPackage.getTypes().size());
	}

	@Test
	public void testMergeTwoPackages() throws Exception {

		// create a "testpackage1" with class "A"
		RulePackage pkg1 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg1.setName("testpackage1");
		pkg1.setExternal(true);
		VRClass clazzA = IntegrationFactory.eINSTANCE.createVRClass();
		clazzA.setName("A");
		JavaBeanTypeBinding binding = IntegrationFactory.eINSTANCE
				.createJavaBeanTypeBinding();
		binding.setFullyQualifiedClassname("com.odcgroup.A");
		clazzA.getTypeBindings().add(binding);
		pkg1.getTypes().add(clazzA);
		dataModelIntegration.merge(pkg1, "vrpath:/rules", null);
		dataModelIntegration.save("vrpath:/rules");

		// check creation of "package1" with class "A"
		pkg1 = dataModelIntegration.getPackage("vrpath:/rules/testpackage1");
		Assert.assertEquals(1, pkg1.getTypes().size());

		// create a "testpackage2" with class "B"
		RulePackage pkg2 = IntegrationFactory.eINSTANCE.createRulePackage();
		pkg2.setName("testpackage2");
		pkg2.setExternal(true);
		VRClass clazzB = IntegrationFactory.eINSTANCE.createVRClass();
		clazzB.setName("B");
		binding = IntegrationFactory.eINSTANCE.createJavaBeanTypeBinding();
		binding.setFullyQualifiedClassname("com.odcgroup.B");
		clazzB.getTypeBindings().add(binding);
		pkg2.getTypes().add(clazzB);
		dataModelIntegration.merge(pkg2, "vrpath:/rules", null);

		// check creation of "package2" with class "B"
		pkg2 = dataModelIntegration.getPackage("vrpath:/rules/testpackage2");
		Assert.assertEquals(1, pkg2.getTypes().size());

		// check that package "testpackage1" with class "A" still exists
		pkg1 = dataModelIntegration.getPackage("vrpath:/rules/testpackage1");
		Assert.assertEquals(1, pkg1.getTypes().size());

		dataModelIntegration.save("vrpath:/rules");

		// repeat the same tests from above after the save

		// check creation of "package2" with class "B"
		pkg2 = dataModelIntegration.getPackage("vrpath:/rules/testpackage2");
		Assert.assertEquals(1, pkg2.getTypes().size());

		// check that package "testpackage1" with class "A" still exists
		pkg1 = dataModelIntegration.getPackage("vrpath:/rules/testpackage1");
		Assert.assertEquals(1, pkg1.getTypes().size());
	}

}
