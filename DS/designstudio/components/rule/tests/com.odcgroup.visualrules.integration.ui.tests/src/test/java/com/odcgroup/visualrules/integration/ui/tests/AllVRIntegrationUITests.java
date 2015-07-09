package com.odcgroup.visualrules.integration.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.visualrules.integration.ui.tests.jira.TestTankBasedVRTest;

@RunWith(Suite.class)
@SuiteClasses( {
	TestTankBasedVRTest.class
} )
public class AllVRIntegrationUITests {

}
