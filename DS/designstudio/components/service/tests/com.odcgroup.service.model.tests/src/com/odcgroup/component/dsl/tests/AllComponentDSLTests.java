package com.odcgroup.component.dsl.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { 
	ComponentDSLModelTest.class,
	ComponentFormatterTest.class,
	ComponentParserTest.class
})
public class AllComponentDSLTests {
}
