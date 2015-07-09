package com.odcgroup.visualrules.depmgr.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.workbench.core.internal.repository.maven.MavenSettingsCustomizerTest;

@RunWith(Suite.class)
@SuiteClasses( {
	MavenSettingsCustomizerTest.class,
	DependencyTest.class,
} )
public class AllVRDepMgrTests {

}
