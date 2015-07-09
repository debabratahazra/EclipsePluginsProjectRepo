package com.odcgroup.workbench.integration.tests.help;

import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.DS_PROJECT;
import static com.odcgroup.workbench.integration.tests.IntegrationTestConstants.STD_PACKAGE;
import static junit.framework.Assert.fail;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.util.pageobjects.ContextSensitiveHelpPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.MenuPageObject;
import com.odcgroup.workbench.integration.tests.util.pageobjects.OdysseyNavigatorPageObject;

@RunWith(SWTBotJunit4ClassRunner.class)
public class ContextSensitiveHelpTest {
	
	private static SWTWorkbenchBot bot = new SWTWorkbenchBot();
	
	// PageObjects that are used in these tests
	private static MenuPageObject menu = new MenuPageObject();
	private static ContextSensitiveHelpPageObject help = new ContextSensitiveHelpPageObject();

	@Before @After
	public void prepareTest() throws CoreException {
		help.closeHelpWindow(); 
		AbstractSWTBotTest.deleteProjects();
	}
	@Ignore
	@Test
	public void testCreateTemplateOnlineHelp() {
		menu.createNewTemplateProject_openWizard();
		testContextHelp();
		menu.cancelWizard();
	}
	@Ignore
	@Test
	public void testCreateModelProjectHelp() {
		menu.createNewModelProject_openWizard();
		testContextHelp();
		menu.cancelWizard();
	}
	@Ignore
	@Test
	public void testImportProjectHelp() {
		menu.importProjects_openWizard();
		testContextHelp();
		menu.cancelWizard();
	}
	@Ignore
	@Test
	public void testCreatePackageHelp() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		IPath path = new Path(DS_PROJECT+"/Domains");
		on.createPackage_openWizard(path);
		testContextHelp();
		on.cancelWizard();
	}
	@Ignore
	@Test
	public void testNewModuleHelp() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createModule_openWizard(DS_PROJECT);
		testContextHelp();
		on.cancelWizard();
	}
	@Ignore
	@Test
	public void testNewFragmentHelp() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createFragment_openWizard(DS_PROJECT, STD_PACKAGE);
		testContextHelp();
		on.cancelWizard();
	}
	
	@Test @Ignore // Doesn't work on Bamboo
	public void testNewPage() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createPage_openWizard(DS_PROJECT);
		testContextHelp();
		on.cancelWizard();
	}
	@Ignore
	@Test
	public void testNewDomain() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createDomain_openWizard(DS_PROJECT);
		testContextHelp();
		on.cancelWizard();
	}
	@Ignore
	@Test
	public void testNewPageflow() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createPageflow_openWizard(DS_PROJECT);
		testContextHelp();
		on.cancelWizard();
	}
	@Ignore
	@Test
	public void testNewWorkflow() throws Exception {
		AbstractSWTBotTest.createProject();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.createWorkflow_openWizard(DS_PROJECT);
		testContextHelp();
		on.cancelWizard();
	}
	
	@Test @Ignore // Doesn't work on Bamboo
	public void testImportTAFormat() throws InterruptedException {
		menu.createNewTemplateProject("pms-models");
		waitForAutoBuidToComplete();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.importTAFormats_openWizard();
		testContextHelp();
		on.cancelWizard();
	}
	
	@Test @Ignore // Doesn't work on Bamboo
	public void testImportMetaDict() throws InterruptedException {
		menu.createNewTemplateProject("pms-models");
		waitForAutoBuidToComplete();
		OdysseyNavigatorPageObject on = new OdysseyNavigatorPageObject();
		on.importTAMetaDictionary_openWizard();
		testContextHelp();
		on.cancelWizard();
	}
	
	private void waitForAutoBuidToComplete() throws InterruptedException {
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	private void testContextHelp() {
		String contextHelpTested = bot.activeShell().getText(); 
		help.displayContextSensitiveHelp();
		Display helpDisplay = help.findHelpDisplay();
		Assert.assertNotNull("No help window displayed for " + contextHelpTested, helpDisplay);
		Assert.assertTrue("Context help not found for " + contextHelpTested, help.isContextualHelpFound(helpDisplay));
		help.closeHelpWindow(helpDisplay);
		help.waitForHelpToClose(helpDisplay);
		Assert.assertTrue("Help window should have been disposed (" + contextHelpTested + ")", helpDisplay.isDisposed());
	}

}
