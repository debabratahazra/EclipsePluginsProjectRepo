package com.odcgroup.page.transformmodel.tests.event;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Convenient test suite to validate all events transformation
 * @author atr
 */

@RunWith(Suite.class)
@SuiteClasses( {
	EventFunctionAndTargetParameterTest.class,
	PullModuleTest.class
} )
public class AllEventTransformationTests {

}
