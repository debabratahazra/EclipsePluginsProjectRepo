package com.odcgroup.ocs.server.external.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.server.external.builder.internal.OcsServerTest;
import com.odcgroup.ocs.server.external.builder.internal.mapping.PrepareDeploymentHelperTest;
import com.odcgroup.ocs.server.external.model.internal.ExternalServerTest;

@RunWith(Suite.class)
@SuiteClasses( {
	PrepareDeploymentHelperTest.class,
	OcsServerTest.class,
	ExternalServerTest.class,
} )
public class AllOcsServerExternalTests {

}
