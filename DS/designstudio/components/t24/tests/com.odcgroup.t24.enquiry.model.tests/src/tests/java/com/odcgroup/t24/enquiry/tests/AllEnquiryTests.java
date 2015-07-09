package com.odcgroup.t24.enquiry.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.t24.enquiry.model.translation.tests.AllEnquiryDSLTests;
import com.odcgroup.t24.enquiry.scoping.tests.EnquiryQualifiedNameProviderTest;
import com.odcgroup.t24.enquiry.validation.tests.EnquiryJavaValidatorTest;
import com.odcgroup.t24.enquiry.xml.generator.EnquiryXMlGenerationTest;



@RunWith(Suite.class)
@SuiteClasses( { 
	EnquiryXMlGenerationTest.class, 
	AllEnquiryDSLTests.class,
	EnquiryQualifiedNameProviderTest.class,
	EnquiryJavaValidatorTest.class
	} )
public class AllEnquiryTests {

    
}
