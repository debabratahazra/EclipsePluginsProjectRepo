package com.odcgroup.edge.t24ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	DS7367ESONReferenceXtextProxyUtilTest.class,
	DS7351EnquiryESONBrokenReferenceTest.class
})
public class AllT24UIModelTests {
}
