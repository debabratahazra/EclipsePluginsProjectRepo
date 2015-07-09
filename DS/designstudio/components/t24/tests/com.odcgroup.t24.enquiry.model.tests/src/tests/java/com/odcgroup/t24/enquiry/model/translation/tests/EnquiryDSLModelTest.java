package com.odcgroup.t24.enquiry.model.translation.tests;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.t24.enquiry.EnquiryInjectorProvider;
import com.odcgroup.workbench.core.tests.util.AbstractXtextTest;


@RunWith(XtextRunner2.class)
@InjectWith(EnquiryInjectorProvider.class)
public class EnquiryDSLModelTest extends AbstractXtextTest{

	public EnquiryDSLModelTest() {
		super("EnquiryDSLModelTest");
	}

	@Test
	public void test_Keyword() {
		testKeyword("Enquiry");
		testKeyword("generateIFP:");
		testKeyword("description");
		testKeyword("server-mode:");
		testKeyword("header");
		testKeyword("companies:");
		testKeyword("account-field:");
		testKeyword("zero-records-display:");
		testKeyword("no-selection:");
		testKeyword("show-all-books:");		
		testKeyword("start-line:");
		testKeyword("end-line:");
		testKeyword("build-routines");
		testKeyword("custom-selection");
		testKeyword("toolbar:");
		testKeyword("fileVersion:");
		testKeyword("attributes:");
		testKeyword("label");
		testKeyword("column:");
		testKeyword("line:");
		testKeyword("target-for-application");
		testKeyword("screen:");
		testKeyword("map-field:");
		testKeyword("drilldown");
		testKeyword("image:");
		testKeyword("info:");
		testKeyword("from-field:");
		testKeyword("label-field:");
		testKeyword("application:");
		testKeyword("screen:");
		testKeyword("criteria:");
	}
	
	@Test
	public void test_ParserRule() {
		testParserRule("security { application: 'arg1.id' field: 'str1' abort: false }", "Security");
		testParserRule("field \"@ID\" { mandatory: false label { en = \"English Translation\" } }None", "Selection");
		testParserRule("target-for-application 'arg1.app' { screen: \"id0,\"  map-field: \"fromArg\" to 'toArg' }", "Target");
		testParserRule("drilldown {	description { en = \"translation\" } label-field: \"@ID\" criteria: 'arg1.no' Equals '@ID' enquiry: 'argEnq' }", "DrillDown");
		testParserRule("drilldown {	description { en = \"translation\" } label-field: \"@ID\" criteria: 'arg1.no' Equals '@ID' from-field: \"@ID\" }", "DrillDown");
		testParserRule("field \"RESIDENCE\" { label { en = \"Residence\" } format: User length: 25 alignment: Left comma-separator: false position {	column: 16 } processing-mode: Single hidden: true operation: application-field-name \"RESIDENCE\" conversion: getFrom 'COUNTRY' \"COUNTRY.NAME\" false }", "Field");
		testParserRule("web-service { publish: true names: 'arg1', 'arg2' activity: 'arg3' description: 'agr4' }", "WebService");
		testParserRule("fixed-selection \"@ID\" { Equals '=, =='}", "FixedSelection");
		
		//Enquiry Field Break Condition for Page type
		String breakConditionWIthPage ="field \"H.ACCOUNT\" { display-type: \"CLASS-ENQ.H.DATA\" break-condition { break: Page }" + 
             "length: 16 alignment: Left comma-separator: false display-section: Header "+
			 "position { column: 2 line: 2 }" +
			  "processing-mode: Single operation: application-field-name \"ACCOUNT.NUMBER\" } ";
		
		testParserRule(breakConditionWIthPage, "Field");
		//Enquiry Field Break Condition for Field reference type
		String breakConditionWIthFieldRefe ="field \"H.ACCOUNT\" { display-type: \"CLASS-ENQ.H.DATA\" break-condition { field: \"TestField\" }" + 
	             "length: 16 alignment: Left comma-separator: false display-section: Header "+
				 "position { column: 2 line: 2 }" +
				  "processing-mode: Single operation: application-field-name \"ACCOUNT.NUMBER\" } ";
		
		testParserRule(breakConditionWIthFieldRefe, "Field");
	}
	
	
}
