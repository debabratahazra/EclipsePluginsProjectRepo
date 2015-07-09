package com.odcgroup.t24.mdf.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	T24ExcelDocGeneratorTest.class,
	T24MdfGeneratorTest.class
} )
public class AllT24MdfGenerationTests {
}
