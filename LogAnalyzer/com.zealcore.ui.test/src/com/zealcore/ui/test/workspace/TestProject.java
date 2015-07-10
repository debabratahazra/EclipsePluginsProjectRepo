package com.zealcore.ui.test.workspace;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestProject extends AbstractLABaseTest {
	
	private String PROJECT_NAME = null;
	private final String OLD_LOGSET = "logset";
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		PROJECT_NAME = "LA_Test_Project";
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Create/Rename/Delete empty logset.
	 * @throws Exception
	 */
	public void testLogset() throws Exception {

		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.renameLogset(PROJECT_NAME, OLD_LOGSET , "logset2", 1000, ui);
		LogAnalyzerUtil.deleteLogset(PROJECT_NAME, "logset2", ui);
		LogAnalyzerUtil.deleteProject(PROJECT_NAME, 1000, ui);
		
	}

	/**
	 * Create/Rename/Delete empty project.
	 * @throws Exception
	 */
	public void testProject() throws Exception {

		IUIContext ui = getUI();

		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.renameProject(PROJECT_NAME, "New_LA_Project", 1000, ui);
		LogAnalyzerUtil.deleteProject("New_LA_Project", 1000, ui);
		
	}
	
	/**
	 * Try to create Project/logset with same name.
	 * @throws Exception
	 */
	public void testFailure() throws Exception {

		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		createNewLogset(ui);
		createNewProject(ui);
		LogAnalyzerUtil.deleteProject(PROJECT_NAME, 1000, ui);
	
	}
	
	/**
	 * Open Text Browser after import LOGFILE.
	 * Rename logset and Open Text Browser.
	 * Rename Project and Open Text Browser.
	 * @throws Exception
	 */
	public void testProjectWithTextBrowser() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 1000, ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.TEXT_BROWSER, ui);
		LogAnalyzerUtil.renameLogset(PROJECT_NAME, "logset", "logset2", 1000, ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset2", LogAnalyzerUtil.TEXT_BROWSER, ui);
		LogAnalyzerUtil.renameProject(PROJECT_NAME, "Test_Project", 1500, ui);
		LogAnalyzerUtil.deleteProject("Test_Project", 1000, ui);
	}
	
	private void createNewProject(IUIContext ui) throws Exception {
		ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
		LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
		"New/Log Analyzer Project");
		ui.wait(new ShellShowingCondition("New Log Analyzer Project"));
		ui.enterText(PROJECT_NAME);
		LogAnalyzerUtil.delay(2000, ui);
		ui.click(new ButtonLocator("Cancel"));
		ui.wait(new ShellDisposedCondition("New Log Analyzer Project"));
		
	}

	private void createNewLogset(IUIContext ui) throws Exception {
		
		ui.click(new TreeItemLocator(PROJECT_NAME + "/logset", new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "New/Logset");
		ui.wait(new ShellShowingCondition("New Logset"));
		ui.enterText("logset");
		ui.click(new ButtonLocator("Cancel"));
		ui.wait(new ShellDisposedCondition("New Logset"));
		ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "New/Logset");
		ui.wait(new ShellShowingCondition("New Logset"));
		ui.enterText("logset");
		LogAnalyzerUtil.delay(2000, ui);
		ui.click(new ButtonLocator("Cancel"));
		ui.wait(new ShellDisposedCondition("New Logset"));
	}

}
