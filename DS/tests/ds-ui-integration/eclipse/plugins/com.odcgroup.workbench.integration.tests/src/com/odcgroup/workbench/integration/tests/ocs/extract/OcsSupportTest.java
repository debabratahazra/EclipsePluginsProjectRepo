package com.odcgroup.workbench.integration.tests.ocs.extract;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.FileFilter;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.widgets.TimeoutException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
@RunWith(SWTBotJunit4ClassRunner.class)
public class OcsSupportTest {

	private static final String DICK1_DISTRIB_OCS_DIRECTORY = "\\\\oams.com\\Software\\Incoming Packages\\OCS\\${ds.target.platform.version}\\disk1\\distribution";

	protected static final Logger logger = LoggerFactory.getLogger(OcsSupportTest.class);
	@Ignore
	@Test
	public void ds4309TripleAPlusRuntimePreferencesReloadOKextractsTwice() throws InterruptedException {
		SWTWorkbenchBot bot = new SWTWorkbenchBot();

		String targetPlatformVersion = System.getProperty("ds.target.platform.version");
		String lastStableOcsDirectoryStr = DICK1_DISTRIB_OCS_DIRECTORY.replace("${ds.target.platform.version}", targetPlatformVersion);
		logger.info("Searching for Triple'A Plus distribution in " + lastStableOcsDirectoryStr);
		File lastStableOcsDirectory = new File(lastStableOcsDirectoryStr);
		assertTrue("The Triple'A Plus distribution dir doesn't exist: " + lastStableOcsDirectoryStr, lastStableOcsDirectory.exists());

		File[] ocsZipFiles = lastStableOcsDirectory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String name = pathname.getName();
				return name.endsWith(".zip") && !name.contains("sources");
			}
		});
		assertTrue("No zip file found in the Triple'A Plus distribution dir: " + lastStableOcsDirectoryStr, ocsZipFiles.length != 0);
		assertTrue("More that one zip file found in Triple'A Plus distribution dir: " + lastStableOcsDirectoryStr, ocsZipFiles.length == 1);

		logger.info("Selecting the following Triple'A Plus zip file: " + ocsZipFiles[0].getAbsolutePath());

		logger.info("Starting Triple'A Plus zip extraction test");
		bot.menu("Window").menu("Preferences").click();
		bot.waitUntil(Conditions.shellIsActive("Preferences"));

		bot.tree().expandNode("Design Studio").expandNode("Servers").select("Embedded");

		String distribution = ocsZipFiles[0].getAbsolutePath();
		logger.info("Distribution: " + distribution);

		bot.textWithLabel("Distribution:").setText(distribution);

		// Click the Reload button
		bot.button("Reload...").click();

		// Wait for the extraction to be done
		bot.waitUntil(Conditions.shellIsActive("Progress Information"));
		bot.waitUntil(Conditions.shellCloses(bot.shell("Progress Information")), 5*60*1000, 1000);

		// Wait for the original wui blocks extraction to be done
		bot.waitUntil(Conditions.shellIsActive("Orignial Wuiblocks Extraction Progress Information"));
		bot.waitUntil(Conditions.shellCloses(bot.shell("Orignial Wuiblocks Extraction Progress Information")), 60*1000, 1000);

		assertTrue("The orignialWuiBlocs folder should exist",
				OcsBinariesExtractionFacade.instance().getOcsBinariesOriginalWuiBlocksFolder().exists());
		
		assertTrue("The orignialWuiBlocs folder shouldn't be empty",
				OcsBinariesExtractionFacade.instance().getOcsBinariesOriginalWuiBlocksFolder().listFiles().length > 0);
		
		bot.waitUntil(Conditions.shellIsActive("M2eclipse Properties Update"));
		bot.button("OK").click();

		// Close the preference window
		bot.button("OK").click();
		boolean extractTriggeredTwice = true;
		try {
			bot.waitUntil(Conditions.shellIsActive("Progress Information"), 5000, 1000);
		} catch (TimeoutException e) {
			extractTriggeredTwice = false;
		}
		assertFalse("The extract shouldn't be triggered twice", extractTriggeredTwice);

		bot.closeAllShells();
	}
}
