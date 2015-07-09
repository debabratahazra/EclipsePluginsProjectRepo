package com.odcgroup.ocs.server.external.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.server.external.ui.builder.DeployBuilderTest;
import com.odcgroup.ocs.server.external.ui.builder.PrepareUndeploymentFacadeTest;
import com.odcgroup.ocs.server.external.ui.builder.ProjectHelperTest;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.AbstractTargetMapperTest;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.GenTargetMapperTest;
import com.odcgroup.ocs.server.external.ui.builder.internal.mapping.TargetMapperHelperTest;
import com.odcgroup.ocs.server.external.ui.util.ExternalServerManagerTest;

@RunWith(Suite.class)
@SuiteClasses( {
	AbstractTargetMapperTest.class,
	PrepareUndeploymentFacadeTest.class,
	TargetMapperHelperTest.class,
	GenTargetMapperTest.class,
	DeployBuilderTest.class,
	ExternalServerManagerTest.class,
	ProjectHelperTest.class,
} )
public class AllOcsServerExternalUITests {
}
