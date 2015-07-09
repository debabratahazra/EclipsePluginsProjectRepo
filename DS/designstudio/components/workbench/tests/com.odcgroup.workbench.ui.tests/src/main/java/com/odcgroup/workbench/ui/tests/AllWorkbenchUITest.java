package com.odcgroup.workbench.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.workbench.ui.maven.M2EclipseIntegrationFacadeTest;
import com.odcgroup.workbench.ui.xtext.tests.ByResourceExtensionLoaderSorterTest;

/**
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( { 
	TestNoPopupTests.class,
	M2EclipseIntegrationFacadeTest.class,
	ByResourceExtensionLoaderSorterTest.class
})
public class AllWorkbenchUITest {

}
