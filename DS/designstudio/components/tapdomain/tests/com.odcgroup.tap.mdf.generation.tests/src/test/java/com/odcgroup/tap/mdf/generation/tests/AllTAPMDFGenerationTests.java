package com.odcgroup.tap.mdf.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Functionality is built up from the first test to the last
 */
@RunWith(Suite.class)
@SuiteClasses( {
	TAPExcelWriterTestCase.class,
	TAPExcelDomainGeneratorTest.class
	} )
public class AllTAPMDFGenerationTests {

}
