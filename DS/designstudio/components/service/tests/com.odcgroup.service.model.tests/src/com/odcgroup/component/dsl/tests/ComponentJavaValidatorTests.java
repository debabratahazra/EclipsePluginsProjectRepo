package com.odcgroup.component.dsl.tests;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics.Pred;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.IResourceValidator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.service.model.ComponentInjectorProvider;
import com.odcgroup.service.model.validation.ComponentJavaValidator;

/**
 * Validator Tests.
 *
 * @author rameshsampenga
 */
@RunWith(XtextRunner.class)
@InjectWith(ComponentInjectorProvider.class)
public class ComponentJavaValidatorTests {
	

	@Inject	ResourceSet rs;
	@Inject ParseHelper<EObject> parseHelper;
	@Inject ValidatorTester<ComponentJavaValidator> tester;
	@Inject	IResourceValidator resourceValidator;
	
	/**
	 * Test validation of Out direction of primitive with optional or mandatory multiplicity 
	 */
	@Test
	public void testNoNameFeature() throws Exception {
		InputStream is = getClass().getResourceAsStream("/com/odcgroup/component/validation/tests/PrimitiveArguments.component");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is, URI.createURI("com/odcgroup/component/validation/tests/PrimitiveArguments.component"), null, rs);
		int errorCount = 7; // the number errors written in the test  
		Pred[] preds = new Pred[errorCount];
		for (int i = 0; i < preds.length; i++) {
			preds[i] = AssertableDiagnostics.errorCode(ComponentJavaValidator.ERR_OUTGOING_PRIMITIVE);
		}
		tester.validate(testModel).assertDiagnosticsCount(errorCount).assertAll(preds);
	}

}
