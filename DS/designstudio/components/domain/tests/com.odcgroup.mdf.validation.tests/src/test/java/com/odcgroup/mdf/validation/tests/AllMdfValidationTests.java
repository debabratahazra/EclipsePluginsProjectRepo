package com.odcgroup.mdf.validation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.mdf.validation.internal.constraint.tests.MdfDatasetDerivedAssociationTest;
import com.odcgroup.mdf.validation.internal.constraint.tests.MdfEnumMaskTest;
import com.odcgroup.mdf.validation.internal.constraint.tests.MdfModelValidatorTest;
import com.odcgroup.mdf.validation.internal.constraint.tests.NonMdfModelInstanceTest;


/**
 * @author snn
 */
@RunWith(Suite.class)
@SuiteClasses( {
	MdfModelValidatorTest.class,
	NonMdfModelInstanceTest.class,
	MdfEnumMaskTest.class,
	MdfDatasetDerivedAssociationTest.class
} )
public class AllMdfValidationTests {

}
