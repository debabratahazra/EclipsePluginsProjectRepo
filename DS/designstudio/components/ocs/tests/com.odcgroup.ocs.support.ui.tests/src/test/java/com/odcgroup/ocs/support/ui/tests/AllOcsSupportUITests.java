package com.odcgroup.ocs.support.ui.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.odcgroup.ocs.support.installer.OcsBinariesExtractionUIFacadeTest;
import com.odcgroup.ocs.support.ui.external.tool.ExternalToolUITest;
import com.odcgroup.ocs.support.ui.external.tool.LogKeeperTest;
import com.odcgroup.ocs.support.ui.external.tool.OutputCallbackWithProgressBarTest;
import com.odcgroup.ocs.support.ui.preferences.pages.EmbeddedServerPreferencePageTest;
import com.odcgroup.ocs.support.ui.preferences.pages.ExternalServerPreferencePageTest;

/**
 * @author yan
 */
@RunWith(Suite.class)
@SuiteClasses( {
	ExternalToolUITest.class,
	OcsBinariesExtractionUIFacadeTest.class,
	EmbeddedServerPreferencePageTest.class,
	ExternalServerPreferencePageTest.class,
	LogKeeperTest.class,
	OutputCallbackWithProgressBarTest.class,
} )
public class AllOcsSupportUITests {

}
