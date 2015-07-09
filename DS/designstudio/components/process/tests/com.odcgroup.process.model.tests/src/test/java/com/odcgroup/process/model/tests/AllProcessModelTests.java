package com.odcgroup.process.model.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.process.model.translation.ProcessTranslationProviderTest;
import com.odcgroup.process.model.translation.ProcessTranslationTest;

/**
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( {
	ProcessTranslationProviderTest.class,
	ProcessTranslationTest.class,
} )
public class AllProcessModelTests {

}
