package com.odcgroup.page.metamodel.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.page.metamodel.util.tests.AccountabilityRuleTest;
import com.odcgroup.page.metamodel.util.tests.MetaModelRegistryTest;
import com.odcgroup.page.metamodel.util.tests.OperatorTypeRegistryTest;

/**
 * Tests for the Page MetaModel plugin.
 * 
 * @author Gary Hayes
 */
@RunWith(Suite.class)
@SuiteClasses( {
	MetaModelRegistryTest.class,
	OperatorTypeRegistryTest.class,
	AccountabilityRuleTest.class,
} )
public class AllPageMetaModelTests {

}
