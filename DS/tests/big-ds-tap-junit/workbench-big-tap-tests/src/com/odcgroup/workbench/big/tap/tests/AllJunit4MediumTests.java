package com.odcgroup.workbench.big.tap.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.aaa.connector.tests.AllAAAConnectorTests;

@RunWith(Suite.class)
@SuiteClasses( {
	
	AllAAAConnectorTests.class,
	
	})
	
public class AllJunit4MediumTests {
	
	@BeforeClass
	public static void registerModelsInHeadlessBuild() {
	}
}
