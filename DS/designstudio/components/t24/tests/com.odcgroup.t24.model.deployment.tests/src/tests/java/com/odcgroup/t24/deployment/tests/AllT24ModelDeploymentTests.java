package com.odcgroup.t24.deployment.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.enquiry.deployment.test.T24EnquiryDeploymentTest;
import com.odcgroup.t24.localRef.deployment.test.T24LocalRefDeploymentTest;

@RunWith(Suite.class)
@SuiteClasses( {
	//TAFJC And TAJF down will enable once the servers areup.
	T24LocalRefDeploymentTest.class,
	T24EnquiryDeploymentTest.class,
	/*T24VersionDeploymentTest.class,
	T24LocalRefApplicationDeploymentTest.class*/
	})
public class AllT24ModelDeploymentTests {

}
