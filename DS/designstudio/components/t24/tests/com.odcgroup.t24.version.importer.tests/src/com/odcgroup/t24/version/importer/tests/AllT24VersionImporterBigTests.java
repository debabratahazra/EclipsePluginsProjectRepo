package com.odcgroup.t24.version.importer.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	ExternalVersionLoaderTest.class,
	ExternalVersionImporterTest.class,
} )
public class AllT24VersionImporterBigTests {
}
