package com.odcgroup.page.model.util.tests;


import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.page.model.util.ConditionUtils;
import com.odcgroup.workbench.el.DSELStandaloneSetup;
import com.odcgroup.workbench.el.engine.DSELContext;

/**
 * Tests Java Generation from Workbench EL Expressions with context variables.
 * 
 * This class, while looking simple, is the (important!) E2E integration test of
 * the Vorburger EL => Workbench EL + Domain JvmModel combination.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class DSExpressionWithContextGeneratorTest {
	
	@Inject Provider<XtextResourceSet> resourceSetProvider;
	
	@Before 
	public void beforeTest() {
		DSELStandaloneSetup.getInjector();
	}
	
	@Test
	public void testGenerateWithExpressionContextFromDomain() throws Exception {	
		XtextResourceSet resourceSet = resourceSetProvider.get();
		Resource resource = resourceSet.createResource(URI.createURI("ELTests.domain"));
		resource.load(getClass().getResourceAsStream("ELTests.domain"), null);
		
		DSELContext variables = new DSELContext().add("t", "ELTests:SomeClass");

		assertCodeGeneration("t.integer == 5", "com.odcgroup.workbench.el.runtime.DecimalExtensions.operator_equals(<bean:get-property bean=\"t\" property=\"integer\" />, new java.math.BigDecimal(\"5\"))", variables, resource);
		assertCodeGeneration("NOT(t.integer == null)", "com.odcgroup.workbench.el.runtime.BooleanExtensions.operator_not(com.odcgroup.workbench.el.runtime.DecimalExtensions.operator_equals(<bean:get-property bean=\"t\" property=\"integer\" />, null))", variables, resource);
		assertCodeGeneration("NOT(t.ref == null)", "com.odcgroup.workbench.el.runtime.BooleanExtensions.operator_not(com.odcgroup.workbench.el.runtime.ObjectExtensions.operator_equals(<bean:get-property bean=\"t\" property=\"ref\" />, null))", variables, resource);
	}

	private void assertCodeGeneration(String expression, String expectedJava, DSELContext variables, Notifier resource) throws Exception {
		String genJavaCode = ConditionUtils.getConditionAsJavaCode(variables, expression, resource, false);
		assertEquals(expectedJava, genJavaCode.trim());
	}
}
