package com.odcgroup.domain.validation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.domain.validation.tests.tap.TAPDatasetDerivedAssociationTest;
import com.odcgroup.domain.validation.tests.tap.TAPEnumMaskValidationTest;


/**
 * @author snn
 */
@RunWith(Suite.class)
@SuiteClasses( {
	DomainDslValidationTest.class,
	NewDomainDslValidationTest.class,
	TAPEnumMaskValidationTest.class,
	TAPDatasetDerivedAssociationTest.class
} )
public class AllDomainDslValidationTests {

}
