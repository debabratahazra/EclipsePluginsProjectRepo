package com.odcgroup.menu.model.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.menu.model.tests.internal.BasicMenuEditorTests;
import com.odcgroup.menu.model.tests.internal.MenuGenerationTest;
import com.odcgroup.menu.model.tests.internal.MenuResourceTest;
import com.odcgroup.menu.model.tests.internal.MenuValidationTest;

/**
 *
 */
@RunWith(Suite.class)
@SuiteClasses( {
	MenuValidationTest.class,
	MenuResourceTest.class,
	MenuGenerationTest.class,
	BasicMenuEditorTests.class
} )
public class AllMenuModelTests {
	
}
