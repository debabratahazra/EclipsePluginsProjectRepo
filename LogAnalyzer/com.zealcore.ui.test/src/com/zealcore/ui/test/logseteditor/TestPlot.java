package com.zealcore.ui.test.logseteditor;

import org.eclipse.swt.widgets.Canvas;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestPlot extends AbstractLABaseTest {
	
	private String PROJECT_NAME;
	private final String PLOT_VIEW_ID = "com.zealcore.se.ui.views.PlottableViewer";
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		PROJECT_NAME = "Test_Plot";
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPlot() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset", new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.PLOT, ui);
		plotDialog("ProcessExecution", ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.PLOT, ui);
		plotDialog("Process", ui);
	}

	private void plotDialog(String event, IUIContext ui) throws Exception {
		
		ui.wait(new ShellShowingCondition("Open Plot"));
		ui.click(new ButtonLocator("Select Type"));
		ui.wait(new ShellShowingCondition("Select Type"));
		ui.click(new TreeItemLocator("Generic Type Package/" + event));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Select Type"));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Open Plot"));
		ui.click(new XYLocator(new SWTWidgetLocator(Canvas.class,
				new ViewLocator(PLOT_VIEW_ID)),
				110, 228));
		LogAnalyzerUtil.delay(2000, ui);
	}

}
