package com.odcgroup.ocs.packager.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.packager.launcher.PackagerLauncherHelperTest;
import com.odcgroup.ocs.packager.launcher.PackagerStreamListenerTest;
import com.odcgroup.ocs.packager.nature.PackagerNatureTest;

@RunWith(Suite.class)
@SuiteClasses( {
	PackagerNatureTest.class,
	PackagerStreamListenerTest.class,
	PackagerLauncherHelperTest.class,
} )
public class AllOCSPackagerTests  {

}
