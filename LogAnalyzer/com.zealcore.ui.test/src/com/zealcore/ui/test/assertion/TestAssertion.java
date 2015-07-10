package com.zealcore.ui.test.assertion;

import org.eclipse.swt.widgets.Group;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.MenuItemLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.ShellLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestAssertion extends AbstractLABaseTest {
	
	private final String PROJECT_NAME = "Test_Project";
	
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
	}
	
	/**
	 * Create Assertion Set.
	 * @throws Exception
	 */
	public void testEditAssertionSet() throws Exception {
		
		IUIContext ui = getUI();
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 1000, ui);
		LogAnalyzerUtil.createAssertionSet(PROJECT_NAME, "AssertionSet1", ui);
		LogAnalyzerUtil.createAssertionSet(PROJECT_NAME, "AssertionSet2", ui);
	}
	
	/**
	 * Create multiple Assertions in Assertion Set.
	 * @throws Exception
	 */
	public void testEditBasicAssertion() throws Exception {
		
		IUIContext ui = getUI();
		LogAnalyzerUtil.createBasicAssertion(PROJECT_NAME, "AssertionSet1", 
				"Generic Type Package", "Process", "Process execution limit", 
				"Asserts for process that have executed longer than 10% of the CPU time", ui);
		createProcessAssertion(ui);
		closeAssertions(ui);
		LogAnalyzerUtil.createBasicAssertion(PROJECT_NAME, "AssertionSet2", 
				"Generic Type Package", "ProcessExecution", "Big Execution", 
				"Asserts for process executions longer than 1 billion ns", ui);
		createProcessExecutionAssertion(ui);
		closeAssertions(ui);
	}
	
	/**
	 * Run Assertions and verify the results.
	 * @throws Exception
	 */
	public void testRunAssertions() throws Exception {
		
		IUIContext ui = getUI();
		runAssertion(ui);
		showAssertionPreferencePage(ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 1500, ui);
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.createAssertionSet(PROJECT_NAME, "AssertionSet3", ui);
		LogAnalyzerUtil.createBasicAssertion(PROJECT_NAME, "AssertionSet3", "Generic Type Package", 
				"Send", "Send", "", ui);
		createSendAssertion("debugping.*", false, false, ui);
		closeAssertions(ui);
		runAssertion(ui);
		
		LogAnalyzerUtil.editAssertion(PROJECT_NAME, "AssertionSet3", "Send", ui);
		createSendAssertion(".*p?ng.*", false, false, ui);
		closeAssertions(ui);
		runAssertion(ui);
		
		LogAnalyzerUtil.editAssertion(PROJECT_NAME, "AssertionSet3", "Send", ui);
		createSendAssertion(".*debugping:ping.*", true, true, ui);
		closeAssertions(ui);
		runAssertion(ui);
		
		LogAnalyzerUtil.createBasicAssertion(PROJECT_NAME, "AssertionSet3", "Generic Type Package", 
				"User", "User", "", ui);
		createUserAssertion("40", "50", false, ui);
		deleteSendAssertion(ui);
		closeAssertions(ui);
		runAssertion(ui);
		
		LogAnalyzerUtil.editAssertion(PROJECT_NAME, "AssertionSet3", "User", ui);
		createUserAssertion(null, null, true, ui);
		closeAssertions(ui);
		runAssertion(ui);
		
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 1500, ui);
	}

	private void deleteSendAssertion(IUIContext ui) throws Exception {

		ui.click(new ListItemLocator("Send"));
		ui.click(new ButtonLocator("Delete", 1, new ShellLocator("Assertions")));
		ui.wait(new ShellShowingCondition("Delete"));
		ui.click(new ButtonLocator("&Yes"));
		ui.wait(new ShellDisposedCondition("Delete"));
	}

	private void createUserAssertion(String firstField, String secondField, boolean not,
			IUIContext ui) throws Exception {
		
		if(firstField != null && secondField != null){
			ui.click(new LabeledTextLocator("Entry"));
			ui.enterText(firstField);
			ui.click(new LabeledTextLocator("Entry"));
			ui.keyClick(WT.TAB);
			ui.enterText(secondField);
		}
		
		if(not){
			ui.click(new ButtonLocator("Not", 1, new SWTWidgetLocator(Group.class,
			"User")));
		}
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Assertion"));
	}

	private void createSendAssertion(String text, boolean not, boolean regx, IUIContext ui) throws Exception {
		
		ui.click(2, new LabeledTextLocator("From"));
		ui.enterText(text);
		if(not){
			ui.click(new ButtonLocator("Not", 4, new SWTWidgetLocator(Group.class, "Send")));
		}
		if(regx){
			ui.click(new ButtonLocator("Regular expression"));
		}
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Assertion"));
		
	}

	private void showAssertionPreferencePage(IUIContext ui) throws Exception {
		
		ui.click(new MenuItemLocator("Window/Preferences"));
		ui.wait(new ShellShowingCondition("Preferences"));
		ui.click(new FilteredTreeItemLocator("Log Analyzer/Assertion"));
		LogAnalyzerUtil.delay(1200, ui);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Preferences"));
	}

	private void runAssertion(IUIContext ui) throws Exception {
		
		ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/logset", new ViewLocator(
				LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "Run Assertions");
		ui.wait(new ShellShowingCondition("Select Assertion Sets to Run"));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Select Assertion Sets to Run"));
		ui.wait(new ShellDisposedCondition("Assertion Results"));
		LogAnalyzerUtil.delay(1000, ui);
	}

	private void closeAssertions(IUIContext ui) throws Exception { 

		ui.click(new ButtonLocator("      Close      "));
		ui.ensureThat(new ShellLocator("Assertions").isClosed());
		ui.wait(new ShellDisposedCondition("Assertions"));
	}
	
	private void createProcessExecutionAssertion(IUIContext ui) throws Exception {
		
		ui.click(new LabeledTextLocator("Execution Time"));
		ui.enterText("1000000000");
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Assertion"));
	}

	private void createProcessAssertion(IUIContext ui) throws Exception {

		ui.click(new LabeledTextLocator("Utilization"));
		ui.enterText("50.0");
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Assertion"));
	}

}
