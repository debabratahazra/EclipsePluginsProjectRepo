package com.odcgroup.t24.version.validation.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidatorTester;
import org.eclipse.xtext.validation.IResourceValidator;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.version.VersionDSLInjectorProvider;
import com.odcgroup.t24.version.validation.VersionDSLJavaValidator;
import com.odcgroup.t24.version.versionDSL.VersionDSLFactory;

/**
 * Tests for VersionDSLJavaValidator.
 * 
 * @author vramya
 * 
 */
@RunWith(XtextRunner.class)
@InjectWith(VersionDSLInjectorProvider.class)
public class VersionDSLJavaValidatorTest {
	@Inject
	ResourceSet rs;
	@Inject
	ParseHelper<EObject> parseHelper;
	@Inject
	ValidatorTester<VersionDSLJavaValidator> tester;
	@Inject
	IResourceValidator resourceValidator;

	/**
	 * Test validation of physical file name of resource matches the "name" in
	 * the model (of the "root" content EObject).
	 */
	@Test
	public void testCheckFileNameAndRootModelNameAreSame() {
		InputStream is = getClass().getResourceAsStream(
				"/com/odcgroup/t24/version/validation/tests/TEST_FREQUENCY,AA_AUDIT.version");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is,
				URI.createURI("com/odcgroup/t24/version/validation/tests/TEST_FREQUENCY,AA_AUDIT.version"), null, rs);
		tester.validate(testModel).assertErrorContains("Version file name and root model name are not same.");
	}
	
	/**
	 * Test validation of physical file name of resource matches the "t24name"
	 * with '%' symbol in the model (of the "root" content EObject).
	 */
	@Test
	public void testCheckFileNameAndRootModelNameAreSameWithPercent() {
		InputStream is = getClass().getResourceAsStream(
				"/com/odcgroup/t24/version/validation/tests/AA_ACCRUAL_FREQUENCY,_AA_AUDIT.version");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is,
				URI.createURI("com/odcgroup/t24/version/validation/tests/AA_ACCRUAL_FREQUENCY,_AA_AUDIT.version"), null, rs);
		tester.validate(testModel).assertOK();
	}
	
	/**
	 * Test validation of IF ( a - b ) where a and b are number
	 */
	@Test
	public void testDefaultIFExpressionWithNumber() {
		InputStream is = getClass().getResourceAsStream(
				"/com/odcgroup/t24/version/validation/tests/AM_VIOLATION,OVR_ALL.version");
		assertNotNull(is);
		EObject testModel = parseHelper.parse(is,
				URI.createURI("com/odcgroup/t24/version/validation/tests/AM_VIOLATION,OVR_ALL.version"), null, rs);
		
		// no syntactic error
		assertTrue("There are some syntactic errors", testModel.eResource().getErrors().size() == 0);
		
		tester.validate(testModel).assertOK();
	}

	/**
	 * Validation to check that all enum values are part of NID grammar definition.
	 *
	 * @throws IOException
	 */
	@Test
	public void testCheckNIDHoldsAllEnumValues() throws IOException {

		List<String> enumListerals = new ArrayList<String>();
		for (EClassifier type : VersionDSLFactory.eINSTANCE.getEPackage().getEClassifiers()) {
			if (type instanceof EEnum) {
				EEnum keys = (EEnum) type;
				for (EEnumLiteral literal : keys.getELiterals()) {
					if (!enumListerals.contains(literal.getLiteral())) {
						enumListerals.add(literal.getLiteral());
					}
				}
			}
		}

		for (int i = 0; i > enumListerals.size(); i++) {
			testEnumValuewithNID(enumListerals.get(i));
		}
	}

	/**
	 * Test method to verify the provided String value is a valid shortname for version.
	 *
	 * @param string
	 * @throws IOException
	 */
	private void testEnumValuewithNID(String enumValue) throws IOException {
		InputStream in = null;
		try {

			String version = "Screen ST_Customer:CUSTOMER, "
					+ enumValue
					+ " t24Name: \"CUSTOMER,"
					+ enumValue
					+ "\" metamodelVersion: \"9.0.0.20130126\" numberOfAuthorisers: 1 "
					+ "TransactionFlow { otherCompanyAccess: Yes } Presentation { recordsPerPage: \"1\" fieldsPerLine: \"1\" } "
					+ "Fields { }";
			in = IOUtils.toInputStream(version, "UTF-8");
			EObject testModel = parseHelper.parse(in,
					URI.createURI("com/odcgroup/t24/version/validation/tests/CUSTOMER," + enumValue + ".version"),
					null, rs);
			tester.validate(testModel).assertOK();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
