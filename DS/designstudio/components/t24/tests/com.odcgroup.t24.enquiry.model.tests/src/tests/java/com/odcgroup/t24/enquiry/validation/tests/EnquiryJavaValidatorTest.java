package com.odcgroup.t24.enquiry.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.inject.Inject;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.IResourceValidator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.enquiry.EnquiryInjectorProvider;
import com.odcgroup.t24.enquiry.validation.EnquiryJavaValidator;

/**
 * Tests for EnquiryJavaValidator.
 * 
 * @author vramya
 */
@RunWith(XtextRunner.class)
@InjectWith(EnquiryInjectorProvider.class)
public class EnquiryJavaValidatorTest {
	
	@Inject	ResourceSet rs;
	@Inject ParseHelper<EObject> parseHelper;
	@Inject ValidatorTester<EnquiryJavaValidator> tester;
	@Inject	IResourceValidator resourceValidator;

	/**
	 * Test validation of physical file name of resource matches the "name" in
	 * the model (of the "root" content EObject).
	 */
	@Test
	public void testCheckFileNameAndRootModelNameAreSame() {
		InputStream is = getClass().getResourceAsStream(
				"/com/odcgroup/t24/enquiry/validation/tests/CUSTOMERPUSH.enquiry");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is,
				URI.createURI("com/odcgroup/t24/enquiry/validation/tests/CUSTOMERPUSH.enquiry"), null, rs);
		tester.validate(testModel).assertErrorContains("Enquiry file name and root model name are not same.");
	}
	
	/**
	 * Test validation of duplicate field names.
	 */
	@Test
	public void testCheckEnquiryFieldNameDuplicate() {
		InputStream is = getClass().getResourceAsStream(
				"/com/odcgroup/t24/enquiry/validation/tests/AP.LOGIN.DETAILS.enquiry");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is,
				URI.createURI("com/odcgroup/t24/enquiry/validation/tests/AP.LOGIN.DETAILS.enquiry"), null, rs);
		Iterable<Diagnostic> iterable = tester.validate(testModel).getAllDiagnostics();
		for (Diagnostic diagnostic : iterable) {
			assertEquals("Enquiry 'AP.LOGIN.DETAILS': has duplicate field name: 'TIME.LAST.USE'",
					diagnostic.getMessage());
		}
	}

}
