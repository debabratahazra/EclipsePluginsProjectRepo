package com.odcgroup.t24.menu.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.menu.validation.tests.MenuJavaValidatorTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	DS7509MenuEnquiryReferenceTest.class,
	MenuJavaValidatorTest.class
})
public class AllT24MenuModelTests {
}
