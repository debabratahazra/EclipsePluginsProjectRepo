package com.zealcore.ui.test.imports;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Spinner;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CComboItemLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.LabeledLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestTextImportWizard extends AbstractLABaseTest {
	
	private String PROJECT_NAME;
	private String TEXT_FILENAME;
	private String LOG_FILENAME;
	
	@Override
	protected void oneTimeSetup() throws Exception {
		super.oneTimeSetup();
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		PROJECT_NAME = "LA_Project";
		TEXT_FILENAME = "printouts.txt";
		LOG_FILENAME = "printouts.log";
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Import Text logfile using Import wizard.
	 * @throws Exception
	 */
	public void testImportWizard() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", TEXT_FILENAME, 1000, ui);
		openImportWizard(ui);
		importWizard(ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.TEXT_BROWSER, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", TEXT_FILENAME, 1000, ui);
		LogAnalyzerUtil.deleteProject(PROJECT_NAME, 1000, ui);
	}
	
	/**
	 * Handle Exception during import .log file
	 * @throws Exception
	 */
	public void testErrorHandling() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", LOG_FILENAME, 2000, ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.TEXT_BROWSER, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "Copy of printouts.log", 1000, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.event", 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", LOG_FILENAME, 2000, ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "Copy of printouts.log", 2000, ui);
		
		editLogfile(ui);
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME, "logset", LOG_FILENAME, 2000, ui);
		exceptionDialogShown(ui);
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME, "logset", "Copy of printouts.log", 2000, ui);
		exceptionDialogShown(ui);
	}

	private void exceptionDialogShown(IUIContext ui) throws Exception {
		
		ui.wait(new ShellShowingCondition("Problem Occurred"));
		ui.wait(new ShellDisposedCondition(
				"LogAnalyzer deleting log files Activity"));
		ui.click(new ButtonLocator("&Details >>"));
		LogAnalyzerUtil.delay(1200, ui);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Problem Occurred"));
		
	}

	private void editLogfile(IUIContext ui) throws Exception {

		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset/printouts.log",
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.click(new XYLocator(new SWTWidgetLocator(StyledText.class), 39, 78));
		ui.keyClick(WT.BS);
		ui.enterText("+");
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.BS);
		ui.enterText("+");
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.CTRL, 's');
		ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset/Copy of printouts.log",
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.click(new XYLocator(new SWTWidgetLocator(StyledText.class),	106, 78));
		ui.enterText("-");
		ui.keyClick(WT.DEL);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.enterText("-");
		ui.keyClick(WT.DEL);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.enterText("-");
		ui.keyClick(WT.DEL);
		ui.keyClick(WT.ARROW_RIGHT);
		ui.keyClick(WT.CTRL, 's');
	}

	private void importWizard(IUIContext ui) throws Exception {
		
		ui.click(1, new ButtonLocator("&Next >"));
		ui.click(2, new LabeledLocator(Spinner.class, "Number of header lines"));
		ui.enterText("3");
		ui.click(new ButtonLocator("&Next >"));
		ui.click(1, new TableCellLocator(1,2));
		ui.click(1, new XYLocator(new CComboItemLocator("String"), 90, 8));
		ui.click(new CComboItemLocator("String"));
		ui.click(new CComboItemLocator("Timestamp (Y-M-D)"));
		ui.click(1, new TableCellLocator(1,3));
		ui.enterText(" ");
		ui.click(1, new TableCellLocator(2,2));
		ui.click(new XYLocator(new CComboItemLocator("String"), 69, 5));
		ui.click(new CComboItemLocator("String"));
		ui.click(new CComboItemLocator("Timestamp (H:M:S:us)"));
		ui.click(1, new TableCellLocator(2,3));
		ui.enterText(" ");
		ui.click(1, new TableCellLocator(3,2));
		ui.click(new XYLocator(new CComboItemLocator("String"), 69, 5));
		ui.click(new CComboItemLocator("String"));
		ui.click(new CComboItemLocator("Event Type Name"));
		ui.click(1, new TableCellLocator(3,3));
		ui.enterText("\\s\\r");
		ui.click(1, new TableCellLocator(4,3));
		
		ui.click(new CTabItemLocator("Filters"));
		ui.click(new LabeledTextLocator("Regular expression:"));
		ui.enterText(".*Temperature.*");
		ui.click(new ButtonLocator("Add"));
		
		ui.click(new ButtonLocator("&Finish"));
		ui.wait(new ShellDisposedCondition("Generic Import Wizard"));
		
	}

	private void openImportWizard(IUIContext ui) throws Exception {
		
		ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/logset/printouts.txt",
				new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
				"Import Wizard...");
		ui.wait(new ShellShowingCondition("Generic Import Wizard"));
	}

}
