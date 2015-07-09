package com.odcgroup.ocs.server.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.server.ui.actions.DebugServerActionTest;
import com.odcgroup.ocs.server.ui.actions.StartServerActionTest;
import com.odcgroup.ocs.server.ui.actions.StopServerActionTest;
import com.odcgroup.ocs.server.ui.views.ServerTreeLabelProviderTest;

@RunWith(Suite.class)
@SuiteClasses( {
	ServerTreeLabelProviderTest.class,
	StartServerActionTest.class,
	DebugServerActionTest.class,
	StopServerActionTest.class
} )
public class AllServerUITests {

}
