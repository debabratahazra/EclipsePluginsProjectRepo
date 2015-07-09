package com.odcgroup.t24.version.model.translation.tests;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.core.tests.util.AbstractXtextTest;

@RunWith(XtextRunner.class)
@InjectWith(VersionWithDependencyInjectorProvider.class)
public class VersionDSLModelTest extends AbstractXtextTest {

	public VersionDSLModelTest() {
		super("VersionDSLModelTest");
	}

	@Test
	public void test_Keyword() {
		testKeyword("Screen");
		testKeyword("description:");
		testKeyword("group:");
		testKeyword("includeVersionControl:");
		testKeyword("fieldsPerLine:");
		testKeyword("recordsPerPage:");
		testKeyword("header1:");
		testKeyword("header2:");
		testKeyword("language:");
		testKeyword("initialCursorPosition:");
		testKeyword("browserToolbar:");
		testKeyword("nextVersion:");
		testKeyword("nextVersionFunction:");
		testKeyword("nextVersionTransactionReference:");
		testKeyword("Fields");
		testKeyword("TransactionFlow");
		testKeyword("numberOfAuthorisers:");
		testKeyword("dealSlips:");
		testKeyword("dealSlipsTrigger:");
		testKeyword("dealSlipStyleSheet:");
		testKeyword("interfaceException:");
		testKeyword("overrideApproval:");
		testKeyword("exceptionProcessing:");
		testKeyword("otherCompanyAccess:");
		testKeyword("businessDayControl:");
		testKeyword("autoCompanyChange:");
		testKeyword("confirmVersion:");
		testKeyword("previewVersion:");
		testKeyword("authorizationRoutines:");
		testKeyword("authorizationRoutinesAfterCommit:");
		testKeyword("inputRoutines:");
		testKeyword("inputRoutinesAfterCommit:");
		testKeyword("keyValidationRoutines:");
		testKeyword("preProcessValidationRoutines:");
		testKeyword("webValidationRoutines:");
		testKeyword("associatedVersions:");
		testKeyword("keySequence:");
		testKeyword("Attributes:");

		// Fields
		testKeyword("mandatory:");
		testKeyword("default:");
		testKeyword("maxLength:");
		testKeyword("MV:");
		testKeyword("SV:");
		testKeyword("Presentation");
		testKeyword("column:");
		testKeyword("row:");
		testKeyword("label:");
		testKeyword("toolTip:");
		testKeyword("header:");
		testKeyword("footer:");
		testKeyword("rightAdjust:");
		testKeyword("expansion:");
		testKeyword("RekeyRequired:");
		testKeyword("enrichmentLength:");
		testKeyword("selectionEnquiry:");
		testKeyword("enquiryParameter:");
		testKeyword("popupBehaviour:");
		testKeyword("caseConvention:");
		testKeyword("displayType:");
		testKeyword("inputBehaviour:");
		testKeyword("hyperlink:");
		testKeyword("hotValidate:");
		testKeyword("hotField:");
		testKeyword("webValidate:");
		testKeyword("enrichment:");
		testKeyword("validation-routines:");
		testKeyword("jBC:");
		testKeyword("java:");
		testKeyword("format:");
		testKeyword("dealSlipFunction: ");
		testKeyword("generateIFP:");
		testKeyword("associatedVersionsPresentationPattern:");
	}

	@Test
	public void test_ParserRule() {
		//Java Routine Rule
		testParserRule("java:\"Test\"", "JavaRoutine");
		
		//JBC Routine Rule
		testParserRule("jBC:\"Test\"", "JBCRoutine");
		
		//Version Rule
		testParserRule(
				"Screen AA_TermAmount:AA_ARR_TERM_AMOUNT, AA_PA description: en = \"Default Values\" Presentation { " +
				"recordsPerPage: \"1\" fieldsPerLine: \"MULTI\" language: en ; fr } Fields { \"AMOUNT\" " +
				"{ Presentation {caseConvention: None maxLength: 18 column: 1 row: 1} Translations: label: en = \"Amount\"}}", "Version");
		

		//Version Relationship Rule
		testParserRule(
				"Screen AA_TermAmount:AA_ARR_TERM_AMOUNT, AA_PA description: en = \"Default Values\" Presentation { " +
				"recordsPerPage: \"1\" fieldsPerLine: \"MULTI\" language: en ; fr } Relationship { associatedVersions: AC_SWEEP_TYPE,ADMIN_AUDIT } Fields { \"AMOUNT\" " +
				"{ Presentation {caseConvention: None maxLength: 18 column: 1 row: 1} Translations: label: en = \"Amount\"}}", "Version");
		
		//version shortname with digits only DS-5671
		testParserRule(
				"Screen AA_TermAmount:AA_ARR_TERM_AMOUNT, 22 description: en = \"Default Values\" Fields { \"AMOUNT\" " +
				"{ Presentation {caseConvention: None maxLength: 18 column: 1 row: 1} Translations: label: en = \"Amount\"}}", "Version");
		
		//Default Rule
		testParserRule("IF (3-2) \"Old Value\" THEN \"New Value\"", "Default");
		
		//Dealslip Rule
		testParserRule("format: \"Test Format\" dealSlipFunction:  C", "DealSlip");
		
		//Version Connect Rule
		testParserRule(
				"Screen ST_Customer:CUSTOMER, CUST t24Name: \"CUSTOMER,CUST\" metamodelVersion: \"9.0.0.20130126\" numberOfAuthorisers: 1 "
						+ "TransactionFlow { otherCompanyAccess: Yes } Presentation { recordsPerPage: \"1\" fieldsPerLine: \"1\" } "
						+ "Connect { generateIFP: No associatedVersionsPresentationPattern: Tabs } Fields { }",	"Version");
	}

	@Test
	public void test_ModelFile() {
//		testFile("ds5420/DS5420,DS.version", "ds5420/ds5420.domain");
	}

	@Override
	@After
	public void _after() {
	}
}
