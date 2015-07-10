package com.zealcore.ui.test.workspace;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestPerspective extends AbstractLABaseTest {
	
	private String PROJECT_NAME;
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		PROJECT_NAME = "Test_Project";
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Create Log Analyzer Project
	 * and test Log Analyzer Perspective. 
	 * @throws Exception
	 */
	public void testPerspective() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 3000, ui);
				
		ui.contextClick(new TreeItemLocator("Test_Project/logset",
				new ViewLocator("com.zealcore.se.ui.views.SystemNavigator")),
				"Open/Text Browser");
		
		changePerspective(ui);
	}

	private void changePerspective(IUIContext ui) throws Exception{

		ui.click(new MenuItemLocator("Window/Open Perspective/Other..."));
		ui.wait(new ShellShowingCondition("Open Perspective"));
		ui.click(new TableItemLocator("OSE System Browsing"));
		ui.click(new ButtonLocator("OK"));
		ui.ensureThat(PerspectiveLocator.forName("OSE System Browsing").isActive());
		ui.click(new MenuItemLocator("Window/Open Perspective/Other..."));
		ui.wait(new ShellShowingCondition("Open Perspective"));
		ui.click(new TableItemLocator("Log Analyzer"));
		ui.click(new ButtonLocator("OK"));
		ui.ensureThat(PerspectiveLocator.forName("Log Analyzer").isActive());
		ui.click(new MenuItemLocator("Window/Reset Perspective..."));
		ui.wait(new ShellShowingCondition("Reset Perspective"));
		ui.click(new ButtonLocator("OK"));
	}

}
