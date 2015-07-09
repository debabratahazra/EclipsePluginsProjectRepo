package com.odcgroup.visualrules.integration.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.visualrules.integration.tests.api.DatasyncTest;
import com.odcgroup.visualrules.integration.tests.api.MergePackageTest;
import com.odcgroup.visualrules.integration.tests.api.ODYXXTests;
import com.odcgroup.visualrules.integration.tests.api.RemoveImportTest;
import com.odcgroup.visualrules.integration.tests.api.ReuseConfigurationSettingsTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationKeyProviderTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationKeyTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationModelVisitorProviderTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationModelVisitorTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationProviderTest;
import com.odcgroup.visualrules.integration.translation.RuleTranslationTest;

@RunWith(Suite.class)
@SuiteClasses( {
	DatasyncTest.class,
	ReuseConfigurationSettingsTest.class,
	MergePackageTest.class,
	RemoveImportTest.class,
	ODYXXTests.class,
	// translation tests
    RuleTranslationTest.class,
    RuleTranslationKeyTest.class,
    RuleTranslationKeyProviderTest.class,
    RuleTranslationModelVisitorTest.class,
    RuleTranslationModelVisitorProviderTest.class,
    RuleTranslationProviderTest.class,
} )
public class AllVRIntegrationTests {

}
