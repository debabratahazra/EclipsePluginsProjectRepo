package com.odcgroup.pageflow.validation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.pageflow.validation.tests.cycle.PageflowCyclicalFlowTest;
import com.odcgroup.pageflow.validation.tests.properties.PageflowAmbiguousPropertiesTest;

@RunWith(Suite.class)
@SuiteClasses( {
	PageflowCyclicalFlowTest.class,
	PageflowAmbiguousPropertiesTest.class,
})
public class AllPageflowValidationTests {

}
