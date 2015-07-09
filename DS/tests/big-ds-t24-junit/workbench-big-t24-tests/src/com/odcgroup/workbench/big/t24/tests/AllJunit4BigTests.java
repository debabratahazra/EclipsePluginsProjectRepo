package com.odcgroup.workbench.big.t24.tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.aa.product.importer.test.AllExternalProductLinesTest;
import com.odcgroup.t24.application.importer.tests.AllApplicationImportBigTests;
import com.odcgroup.t24.deployment.tests.AllT24ModelDeploymentTests;
import com.odcgroup.t24.enquiry.importer.tests.AllT24EnquiryImporterBigTests;
import com.odcgroup.t24.iris.importer.tests.RIMImporterTest;
import com.odcgroup.t24.localref.application.importer.test.AllLocalrefApplicationImporterTest;
import com.odcgroup.t24.version.importer.tests.AllT24VersionImporterBigTests;

@RunWith(Suite.class)
@SuiteClasses( {
	AllApplicationImportBigTests.class,
	AllT24VersionImporterBigTests.class,
	AllT24EnquiryImporterBigTests.class,
	//RIMImporterTest.class,
	AllLocalrefApplicationImporterTest.class,
	AllExternalProductLinesTest.class,
	AllT24ModelDeploymentTests.class
	})
	
public class AllJunit4BigTests {
	
	@BeforeClass
	public static void registerModelsInHeadlessBuild() {
	}
}
