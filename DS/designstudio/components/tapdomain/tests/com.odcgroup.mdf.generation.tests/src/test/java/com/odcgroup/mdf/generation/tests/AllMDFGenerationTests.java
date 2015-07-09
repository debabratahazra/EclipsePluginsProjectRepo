package com.odcgroup.mdf.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Functionality is built up from the first test to the last
 */
@RunWith(Suite.class)
@SuiteClasses( {
	CastorJDOWriterTestCase.class,
	ModelCopyGeneratorTestCase.class,
	EJBServiceWriterTest.class,
	EcoreGeneratorTestCase.class,
	EcoreBinaryGeneratorTestCase.class,
	MMLGeneratorTestCase.class} )
public class AllMDFGenerationTests {

}
