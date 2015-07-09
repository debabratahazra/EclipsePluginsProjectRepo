package com.odcgroup.t24.application.importer.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( {
	ApplicationIntrospectionTest.class,
	LocalApplicationImporterTest.class,
	ReaderTest.class,
	LocalApplicationFilterTest.class
} )
public class AllApplicationImportTests {
}
