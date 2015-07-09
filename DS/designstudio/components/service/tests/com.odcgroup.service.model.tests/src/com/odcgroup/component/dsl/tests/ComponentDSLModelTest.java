package com.odcgroup.component.dsl.tests;


import org.eclipse.xtext.junit4.InjectWith;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.core.tests.util.AbstractXtextTest;

@RunWith(XtextRunner2.class)
@InjectWith(ComponentWithDependencyInjectorProvider.class)
public class ComponentDSLModelTest extends AbstractXtextTest {
	
	public ComponentDSLModelTest() {
		super("ComponentDSLModelTest");
	}
	
	@Test
    public void test_ML_DOC(){		
            testTerminal("/*This is Documentation*/", "ML_DOC");
            testTerminal("/*This is Documentation /n Which is multiline*/", "ML_DOC");
    }  
	
	@Test
	public void test_SL_COMMENT() {
		testTerminal("* <Rating>0</Rating> ", "SL_COMMENT");
	}

	@Test
	public void test_Keyword() {
		testKeyword("component");
        testKeyword("metamodelVersion");
        
        testKeyword("public");
        testKeyword("private");
        testKeyword("module");
        testKeyword("external");

        testKeyword("interface");
        testKeyword("method");
        testKeyword("property");
        testKeyword("table");
        testKeyword("constant");
        
        testKeyword("IN");
        testKeyword("INOUT");
        testKeyword("OUT");
        
        testKeyword("readonly");
        testKeyword("readwrite");
	}
	
	@Test
	public void test_ParserRule() {
		testParserRule("public interface InvokeRecord ( INOUT OverrideList string )", "Interface");
		testParserRule("private method PaymentScheduleValidateProductline ( IN ProductLine string ) { jBC: argument }", "Method");
		testParserRule("external method ExternalMethod ( IN parameter string ) { jBC: argument }", "Method");
		testParserRule("public property readonly PaymentType : string { jBC: arg1 -> arg2 }", "Property");
		testParserRule("module table TableWithoutFields {t24: SOMETABLE}", "Table");
		testParserRule("public constant NumberPropertyLines = 50", "Constant");
		testParserRule("AdContractDate = 1", "Attribute");
		testParserRule("IN InputString string", "Argument");
		testParserRule("public property readonly ChargeProperty : string { jBC: I_AA.CHARGE.COMMON -> AA$CHARGE.PROPERTY }", "Property");

		testParserRule("public property readwrite MyVar : string { jBC: I_TEST -> MY.VAR }", "Property");
		testParserRule("public property readwrite MyVar : string { jBC: I_TEST -> MY.VAR() }", "Property");
		testParserRule("public property readonly MyVar : string { jBC: I_TEST -> MY.VAR(1) }", "Property");
	}

	
	@Test
	public void test_ModelFile() {
		ignoreFormattingDifferences();
		testFile("sampleComponent.component");
		testFile("IntegrationComponent.component", "IntegrationService.domain");
	}
	
	@Override
	@After
	public void _after() {
		//do nothing
	}
}
