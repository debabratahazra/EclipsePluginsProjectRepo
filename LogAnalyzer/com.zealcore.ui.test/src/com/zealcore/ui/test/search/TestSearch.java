package com.zealcore.ui.test.search;

import org.eclipse.swt.widgets.Group;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.AbstractLABaseTest;
import com.zealcore.ui.test.util.LogAnalyzerUtil;

public class TestSearch extends AbstractLABaseTest {
	
	private String PROJECT_NAME;
	private String SEARCH_VIEW_ID = "org.eclipse.search.ui.views.SearchView";
	
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
	 * Test Search
	 * @throws Exception
	 */
	public void testSearch() throws Exception {
		
		IUIContext ui = getUI();
		
		LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
		LogAnalyzerUtil.importLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 1000, ui);
		LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset", LogAnalyzerUtil.GANTT_CHART, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "Error", ui);
		searchError(".*peng.*", ui);
		ui.click(2, new TreeItemLocator("Error[Time=1620281524000]", new ViewLocator(SEARCH_VIEW_ID)));
		showSearchPreferencePage(ui);
		LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.configureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd", 2000, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "Send", ui);
		searchSend("debugping.*", false, false, true, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "Send", ui);
		searchSend("*ping.*", false, true, false, ui);
		searchSend(".*p?ng.*", false, false, true, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "Send", ui);
		searchSend(".*debugping:ping.*", true, true, true, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "User", ui);
		searchUser(false, ui);
		LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset", "Generic Type Package", "User", ui);
		searchUser(true, ui);

	}

	private void searchUser(boolean not, IUIContext ui) throws Exception {
		
		ui.click(new LabeledTextLocator("Entry"));
		ui.enterText("40");
		ui.keyClick(WT.TAB);
		ui.enterText("50");
		if(not){
			ui.click(new ButtonLocator("Not", 1, new SWTWidgetLocator(Group.class,
			"User")));
		}
		ui.click(new ButtonLocator("&Search"));
		ui.wait(new ShellDisposedCondition("Search"));
		ui.wait(new ShellDisposedCondition("Search for User"));
		
	}

	private void showSearchPreferencePage(IUIContext ui) throws Exception {
		
		ui.click(new MenuItemLocator("Window/Preferences"));
		ui.wait(new ShellShowingCondition("Preferences"));
		ui.click(new FilteredTreeItemLocator("Log Analyzer/Search"));
		LogAnalyzerUtil.delay(1000, ui);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Preferences"));
		
	}

	private void searchSend(String text, boolean not, boolean regx, boolean success, IUIContext ui) throws Exception {
		
		ui.click(2, new LabeledTextLocator("From"));
		ui.enterText(text);
		if(not){
			ui.click(new ButtonLocator("Not", 4, new SWTWidgetLocator(Group.class,
			"Send")));
		}
		if(regx){
			ui.click(new ButtonLocator("Regular expression"));
		}
		ui.click(new ButtonLocator("&Search"));
		if(success){
			ui.wait(new ShellDisposedCondition("Search"));
			ui.wait(new ShellDisposedCondition("Search for Send"));	
		}else{
			LogAnalyzerUtil.delay(2000, ui);
		}
	}

	private void searchError(String text, IUIContext ui) throws Exception {
		
		ui.click(2, new LabeledTextLocator("Faulting Process"));
		ui.enterText(text);
		ui.click(new ButtonLocator("&Search"));
		ui.wait(new ShellDisposedCondition("Search"));
		ui.wait(new ShellDisposedCondition("Search for Error"));
		
	}

}
