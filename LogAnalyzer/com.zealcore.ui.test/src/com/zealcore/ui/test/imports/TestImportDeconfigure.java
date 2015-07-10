package com.zealcore.ui.test.imports;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestImportDeconfigure extends AbstractLABaseTest {
	
	private String PROJECT_NAME;
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		PROJECT_NAME = "Sample_Project";
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test Import/Deconfigure using double click
	 * on log file.
	 * @throws Exception
	 */
	public void testImportDeconfigure() throws Exception {
		
		IUIContext ui = getUI();
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.event", 2000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.TEXT_BROWSER, ui);
		setPreference(ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.event", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		//Import logfile by D-Click on logfile
		importOperation("OSEdelta.event", ui);
		importOperation("OSEdelta.pmd", ui);
		//Deconfigure logfile by D-Click on logfile
		importOperation("OSEdelta.event", ui);
		importOperation("OSEdelta.pmd", ui);
		
	}

	private void importOperation(String logfile, IUIContext ui) throws Exception {
		
		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset/" + logfile,
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.wait(new ShellDisposedCondition("Reading event XML file"));
		ui.wait(new ShellDisposedCondition(
				"LogAnalyzer deleting log files Activity"));
		ui.click(new CTabItemLocator("logset"));
		LogAnalyzerUtil.delay(1500, ui);
	}

	private void setPreference(IUIContext ui) throws Exception {
		
		ui.click(new MenuItemLocator("Window/Preferences"));
		ui.wait(new ShellShowingCondition("Preferences"));
		ui.click(new FilteredTreeItemLocator("Log Analyzer/Misc"));
		ui.click(new ButtonLocator("Enable Double-Click Import/Deconfigure"));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Preferences"));		
	}
}
