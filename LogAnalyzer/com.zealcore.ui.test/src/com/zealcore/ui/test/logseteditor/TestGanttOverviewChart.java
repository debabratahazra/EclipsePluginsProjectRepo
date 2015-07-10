package com.zealcore.ui.test.logseteditor;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestGanttOverviewChart extends AbstractLABaseTest {
	
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
	
	public void testGanttOverviewChart() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset", new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.GANTT_CHART_OVERVIEW, ui);
		
		stepOperation(true, 5, ui);
		stepOperation(false, 5, ui);
		ui.keyClick(WT.END);
		LogAnalyzerUtil.delay(500, ui);
		ui.keyClick(WT.HOME);
		LogAnalyzerUtil.delay(500, ui);
	}

	private void stepOperation(boolean FW, int step, IUIContext ui) throws Exception {
		while(step != 0){
			if(FW){
				ui.keyClick(WT.ARROW_DOWN);
			}else{
				ui.keyClick(WT.ARROW_UP);
			}
			step--;
			LogAnalyzerUtil.delay(500, ui);
		}
	}
}

