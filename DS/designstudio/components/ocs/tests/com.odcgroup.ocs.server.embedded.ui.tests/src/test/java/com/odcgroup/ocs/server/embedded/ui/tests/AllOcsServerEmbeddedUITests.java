package com.odcgroup.ocs.server.embedded.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.server.embedded.ui.EmbeddedServerContributionsTest;
import com.odcgroup.ocs.server.embedded.ui.EmbeddedServerLauncherHelperTest;
import com.odcgroup.ocs.server.embedded.ui.WuiBlockHelperTest;
import com.odcgroup.ocs.server.embedded.ui.logs.LogWatcherManagerTest;

@RunWith(Suite.class)
@SuiteClasses( {
	EmbeddedServerContributionsTest.class,
	LogWatcherManagerTest.class,
	EmbeddedServerLauncherHelperTest.class,
	WuiBlockHelperTest.class,
} )
public class AllOcsServerEmbeddedUITests {

}
