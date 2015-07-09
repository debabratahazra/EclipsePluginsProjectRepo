package com.odcgroup.t24.server.external.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	T24ServerUIExternalCoreTest.class,
	T24ServerStateTest.class,
	TestExternalServerCreate.class
} )
public class AllT24ExternalUITests {

}
