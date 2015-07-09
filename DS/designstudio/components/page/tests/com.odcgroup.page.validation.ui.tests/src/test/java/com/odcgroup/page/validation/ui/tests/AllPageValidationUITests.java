package com.odcgroup.page.validation.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.validation.ui.internal.markers.NoDomainAttributeWarningTest;
import com.odcgroup.page.validation.ui.internal.markers.ValidationProblemResolutionGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses( {
	ValidationProblemResolutionGeneratorTest.class,
	NoDomainAttributeWarningTest.class,
} )
public class AllPageValidationUITests {

}
