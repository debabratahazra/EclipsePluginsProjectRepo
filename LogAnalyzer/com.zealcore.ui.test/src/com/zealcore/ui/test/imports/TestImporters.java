package com.zealcore.ui.test.imports;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestImporters extends AbstractLABaseTest {
	
	private String PROJECT_NAME = null;
	private String PROJECT_NAME_2 = null;
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		IUIContext ui = getUI();
		if(PROJECT_NAME != null){
			LogAnalyzerUtil.deleteProject(PROJECT_NAME, 1000, ui);
			PROJECT_NAME = null;
		}
		if(PROJECT_NAME_2 != null){
			LogAnalyzerUtil.deleteProject(PROJECT_NAME_2, 1000, ui);
			PROJECT_NAME_2 = null;
		}
	}
	
	/**
	 * Test Importer with different log files.
	 * @throws Exception
	 */
	public void testImporters() throws Exception {
		
		IUIContext ui = getUI();
		PROJECT_NAME = "Test_Project";
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/" + "logset",
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEck.pmd", 2000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.event", 2000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "sendreceive.event", 2000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "printouts.log", 2000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "BlackBoxRecorder.srl", 2000, ui);
		
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEck.pmd", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.event", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "sendreceive.event", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "printouts.log", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "BlackBoxRecorder.srl", 2000, ui);
	}
	
	/**
	 * Test Old and new Importers.
	 * @throws Exception
	 */
	public void testImportOperation() throws Exception {
		
		IUIContext ui = getUI();
		PROJECT_NAME_2 = "Sample_Project";
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME_2, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME_2, "logset", "BlackBoxRecorder.srl", 2000, ui);
		ui.click(2, new TreeItemLocator(PROJECT_NAME_2 + "/" + "logset",
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		LogAnalyzerUtil.importLogfile(PROJECT_NAME_2, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME_2, "logset", "BlackBoxRecorder.srl", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME_2, "logset", "OSEdelta.pmd", 2000, ui);
		
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME_2, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME_2, "logset", "BlackBoxRecorder.srl", 2000, ui);
		
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME_2, "logset", "BlackBoxRecorder.srl", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME_2, "logset", "OSEdelta.pmd", 2000, ui);
	}

}
