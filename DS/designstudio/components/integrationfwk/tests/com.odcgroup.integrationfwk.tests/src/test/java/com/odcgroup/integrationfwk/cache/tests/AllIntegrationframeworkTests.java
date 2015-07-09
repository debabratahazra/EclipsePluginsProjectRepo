package com.odcgroup.integrationfwk.cache.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.integrationfwk.controller.tests.ComponentServiceControllerTest;
import com.odcgroup.integrationfwk.decorator.tests.DecoratorCoreTest;
import com.odcgroup.integrationfwk.decorator.tests.DecoratorTest;
import com.odcgroup.integrationfwk.decorator.tests.DecoratorUITest;
import com.odcgroup.integrationfwk.services.connectivity.tests.LandscapeServicesTest;
import com.odcgroup.integrationfwk.services.connectivity.tests.TafjCreateFlowServiceDataBuilderImplTest;
import com.odcgroup.integrationfwk.t24connectivity.flowservice.tests.TafcCreateFlowServiceDataBuilderImplTest;
import com.odcgroup.integrationfwk.ui.common.CreateFlowServiceDataBuilderTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationFieldsLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationsVersionsLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.CommonVariablesLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ComponentServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.CreateDataLibraryServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ExitPointLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.OverridesLandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.TSALandscapeServiceTest;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.VersionLandscapeServiceTest;
import com.odcgroup.integrationfwk.utils.tests.UtilsTest;

/**
 * @author satishnangi
 *
 */
@RunWith(Suite.class)
@SuiteClasses( {
	CacheManagerTest.class ,
	ComponentServiceControllerTest.class ,
	DecoratorCoreTest.class ,
	DecoratorTest.class ,
	DecoratorUITest.class ,
	LandscapeServicesTest.class ,
	TafjCreateFlowServiceDataBuilderImplTest.class ,
	TafcCreateFlowServiceDataBuilderImplTest.class ,
	CreateFlowServiceDataBuilderTest.class ,
	ApplicationFieldsLandscapeServiceTest.class ,
	ApplicationLandscapeServiceTest.class ,
	ApplicationsVersionsLandscapeServiceTest.class ,
	CommonVariablesLandscapeServiceTest.class ,
	ComponentServiceTest.class ,
	CreateDataLibraryServiceTest.class ,
	ExitPointLandscapeServiceTest.class ,
	OverridesLandscapeServiceTest.class,
	TSALandscapeServiceTest.class ,
	VersionLandscapeServiceTest.class,
	UtilsTest.class
})

public class AllIntegrationframeworkTests {

}
