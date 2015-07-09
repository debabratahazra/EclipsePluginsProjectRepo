package com.odcgroup.t24.version.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.version.model.translation.tests.AllVersionDSLTests;
import com.odcgroup.t24.version.model.translation.tests.AllVersionTranslationTests;
import com.odcgroup.t24.version.model.translation.tests.VersionModelTest;
import com.odcgroup.t24.version.scoping.tests.VersionNameProviderTest;
import com.odcgroup.t24.version.validation.tests.VersionDSLJavaValidatorTest;
import com.odcgroup.t24.version.xml.generator.VersionXMLGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses( { 
	AllVersionTranslationTests.class,
	VersionXMLGeneratorTest.class,
	AllVersionDSLTests.class,
	VersionModelTest.class,
	VersionXtextProxyUtilTest.class,
	VersionNameProviderTest.class,
	VersionDSLJavaValidatorTest.class
	} )
public class AllVersionTests {

}
