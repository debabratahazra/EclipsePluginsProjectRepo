package com.odcgroup.aaa.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.aaa.ui.internal.model.ConnectionInfoTest;
import com.odcgroup.aaa.ui.internal.page.bindings.impl.AbstractUDEPropertiesBindingTest;
import com.odcgroup.aaa.ui.internal.page.validation.UDEModelValidationTest;
import com.odcgroup.aaa.ui.internal.page.validation.UDEModelValidatorFactoryTest;
import com.odcgroup.aaa.ui.internal.page.validation.UDEModelValidatorTest;

@RunWith(Suite.class)
@SuiteClasses( {
	ConnectionInfoTest.class,
	UDEModelValidatorTest.class,
	UDEModelValidatorFactoryTest.class,
	AbstractUDEPropertiesBindingTest.class,
	UDEModelValidationTest.class,
} )
public class AllAAAUITests {

}
