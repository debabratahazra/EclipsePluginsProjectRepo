package com.odcgroup.workbench.migration.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.workbench.migration.MetaModelVersionHandlerTest;
import com.odcgroup.workbench.migration.MigrationCoreTest;

@RunWith(Suite.class)
@SuiteClasses( {
	MigrationCoreTest.class,
	MetaModelVersionHandlerTest.class,
} )
public class AllMigrationTests {

}
