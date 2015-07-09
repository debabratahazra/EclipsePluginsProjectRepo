package com.odcgroup.process.generation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.process.generation.tests.ocs.ActivityTest;
import com.odcgroup.process.generation.tests.ocs.AssigneeRefTest;
import com.odcgroup.process.generation.tests.ocs.ProcessTest;
import com.odcgroup.process.generation.tests.ocs.PropertyTest;
import com.odcgroup.process.generation.tests.ocs.ServiceTest;
import com.odcgroup.process.generation.tests.ocs.TestPageflowURL;

/**
 * @author pkk
 *
 */
@RunWith(Suite.class)
@SuiteClasses( {
	ActivityTest.class,
	AssigneeRefTest.class,
	ProcessTest.class,
	PropertyTest.class,
	ServiceTest.class,
	TestPageflowURL.class
} )
public class AllProcessGenerationTests {

}
