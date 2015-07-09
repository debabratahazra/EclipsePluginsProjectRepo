package com.odcgroup.workbench.el.tests;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.vorburger.el.engine.ExpressionContext;
import ch.vorburger.el.engine.tests.AbstractExpressionGeneratorTestBase;

import com.odcgroup.workbench.el.DSELInjectorProvider;
import com.odcgroup.workbench.el.engine.DSExpressionFactory;

/**
 * This tests Java Code generated from DS EL expressions.
 *
 * DSExpressionWithContextGeneratorTest (in page-model-tests) has more interesting tests. 
 *
 * Tests that are generic for (non DS) EL may be in the BasicExpressionGeneratorTest.
 * It sometimes makes sense to test expressions both in the BasicExpressionGeneratorTest
 * as well as here, as the configuration is slightly different.
 *
 * @see com.odcgroup.page.model.util.tests.DSExpressionWithContextGeneratorTest
 * @see ch.vorburger.el.engine.tests.BasicExpressionGeneratorTest
 *
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(DSELInjectorProvider.class)
public class DSExpressionGeneratorTest extends AbstractExpressionGeneratorTestBase {

	private ExpressionContext context;

	public DSExpressionGeneratorTest() {
		super(new DSExpressionFactory());
		
		XtextResourceSet resourceSet = expressionFactory.getInjector().getInstance(XtextResourceSet.class);
		context = new ExpressionContext(resourceSet);
	}

	@Test
	public void testIsNull() throws Exception {
		checkGeneration("\"Saluton\" == null", Boolean.class, context, "com.odcgroup.workbench.el.runtime.ObjectExtensions.operator_equals(\"Saluton\", null)");
	}

	@Test
	public void testNotNull() throws Exception {
		checkGeneration("NOT(\"Saluton\" == null)", Boolean.class, context, "com.odcgroup.workbench.el.runtime.BooleanExtensions.operator_not(com.odcgroup.workbench.el.runtime.ObjectExtensions.operator_equals(\"Saluton\", null))");
	}

	@Test
	public void testIfExpression() throws Exception {
		checkGeneration("if (true) true else false", Boolean.class, context, "new org.eclipse.xtext.xbase.lib.Functions.Function0<Boolean>() {\n  public Boolean apply() {\n    boolean _xifexpression = false;\n    if (true) {\n      _xifexpression = true;\n    } else {\n      _xifexpression = false;\n    }\n    return _xifexpression;\n  }\n}.apply()");
	}

}
