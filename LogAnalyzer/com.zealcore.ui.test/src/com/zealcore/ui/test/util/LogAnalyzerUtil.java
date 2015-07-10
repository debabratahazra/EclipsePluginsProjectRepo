package com.zealcore.ui.test.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.ShellLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;
import com.zealcore.ui.test.Messages;

public class LogAnalyzerUtil {
	
	public static final String SYSTEM_NAVIGATOR_VIEW_ID = "com.zealcore.se.ui.views.SystemNavigator";
	public static final String OVERVIEW_VIEW_ID = "com.zealcore.se.ui.views.OverviewTimeclusters";
	
	public static final String SEQUENCE_DIAGRAM = "Sequence Diagram";
	public static final String GANTT_CHART = "Gantt Chart";
	public static final String GANTT_CHART_OVERVIEW = "Gantt Chart Overview";
	public static final String TIMELINE_BROWSER = "Timeline Browser";
	public static final String TEXT_BROWSER = "Text Browser";
	public static final String PLOT = "Plot";
	
	/**
	 * Import LOGFILE.
	 * @param projectName
	 * @param logsetName
	 * @param logfileName
	 * @param ui
	 * @throws Exception
	 */
	public static void importLogfile(String projectName, String logsetName, 
			String logfileName, int delay, IUIContext ui) throws Exception {
		
		ui.click(1, new TreeItemLocator(projectName + "/" + logsetName , new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.contextClick(new TreeItemLocator(projectName + "/" + logsetName,
				new ViewLocator(SYSTEM_NAVIGATOR_VIEW_ID)), "Import...");
		ui.wait(new ShellShowingCondition("Import"));
		ui.click(1, new FilteredTreeItemLocator("General"), WT.SHIFT);
		ui.click(1, new FilteredTreeItemLocator("General/File System"),
				WT.SHIFT);
		ui.click(new ButtonLocator("&Next >"));
		ui.enterText(Messages.getString("AbstractBaseTest.logfile"));
		ui.click(new ButtonLocator("B&rowse..."));
		ui.keyClick(SWT.Selection);
		ui.click(1, new TreeItemLocator("LOGFILE"), WT.SHIFT);
		ui.click(1, new TableItemLocator(logfileName), WT.CHECK);
		ui.click(new ButtonLocator("&Finish"));
		ui.wait(new ShellDisposedCondition("Import"));
		delay(delay, ui);
	}
	
	public static void configureLogfile(String projectName, String logsetName, 
			String logfileName, int delay, IUIContext ui) throws Exception {
	
		ui.contextClick(new TreeItemLocator(projectName + "/" + logsetName + "/" + logfileName,
				new ViewLocator(SYSTEM_NAVIGATOR_VIEW_ID)),
				"Use as Log File");
		delay(delay, ui);
	}
	
	public static void deconfigureLogfile(String projectName, String logsetName, 
			String logfileName, int delay, IUIContext ui) throws Exception {
		
		ui.contextClick(new TreeItemLocator(projectName + "/" + logsetName + "/" + logfileName,
				new ViewLocator(SYSTEM_NAVIGATOR_VIEW_ID)),
				"Deconfigure Log File");
		ui.wait(new ShellDisposedCondition("LogAnalyzer deimport Activity"));
		delay(delay, ui);
	}
	
	/**
	 * Create new Log Analyzer Project.
	 * @param projectName
	 * @param ui
	 * @throws Exception
	 */
	public static void createLogAnalyzerProject(String projectName, IUIContext ui) 
		throws Exception {
		
		ui.contextClick(new SWTWidgetLocator(Tree.class, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)),
				"New/Log Analyzer Project");
		ui.wait(new ShellShowingCondition("New Log Analyzer Project"));
		ui.enterText(projectName);
		ui.click(new SWTWidgetLocator(Composite.class, new SWTWidgetLocator(
				Composite.class, 1, new SWTWidgetLocator(Composite.class))));
		ui.click(new ButtonLocator("&Finish"));
		ui.wait(new ShellDisposedCondition("New Log Analyzer Project"));
	}
	
	/**
	 * Rename logset name.
	 * @param projectName
	 * @param old_logset
	 * @param new_logset
	 * @param ui
	 * @throws Exception
	 */
	public static void renameLogset(String projectName, String old_logset, String new_logset, int delay,
			IUIContext ui) throws Exception {

		ui.click(new TreeItemLocator(projectName + "/" + old_logset, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.keyClick(WT.F2);
		ui.wait(new ShellDisposedCondition("Progress Information"));
		ui.wait(new ShellShowingCondition("Rename Resource"));
		ui.click(new XYLocator(new LabeledTextLocator("New na&me:"), -58, 7));
		ui.enterText(new_logset);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Rename Resource"));
		delay(delay, ui);
	}
	
	/**
	 * Rename Project name.
	 * @param old_project
	 * @param new_project
	 * @param ui
	 * @throws Exception
	 */
	public static void renameProject(String old_project, String new_project, int delay, IUIContext  ui)
		throws Exception {

		delay(delay, ui);
		ui.click(new TreeItemLocator(old_project, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.click(new MenuItemLocator("File/Rename..."));
		ui.wait(new ShellDisposedCondition("Progress Information"));
		ui.wait(new ShellShowingCondition("Rename Resource"));
		ui.enterText(new_project);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Rename Resource"));
	}
	
	/**
	 * Delete logset.
	 * @param projectName
	 * @param logset
	 * @param ui
	 * @throws Exception
	 */
	public static void deleteLogset(String projectName, String logset, IUIContext ui) 
		throws Exception {

		ui.click(new TreeItemLocator(projectName + "/" + logset, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.contextClick(new TreeItemLocator("LA_Test_Project/logset2", new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)), "Delete");
		ui.wait(new ShellShowingCondition("Delete"));
		ui.click(new ButtonLocator("&Yes"));
		ui.wait(new ShellDisposedCondition("Delete"));
		delay(1000, ui);
	}
	
	/**
	 * Delete Log Analyzer Project.
	 * @param projectName
	 * @param ui
	 * @throws Exception
	 */
	public static void deleteProject(String projectName, int delay, IUIContext ui) throws Exception {

		delay(delay, ui);
		ui.click(new TreeItemLocator(projectName, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)));
		ui.keyClick(WT.DEL);
		ui.wait(new ShellDisposedCondition("Progress Information"));
		ui.wait(new ShellShowingCondition("Delete Resources"));
		ui.click(new ButtonLocator("&Delete project contents on disk (cannot be undone)"));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Delete Resources"));
	}
	
	/**
	 * Open specific logset editor.
	 * @param projectName
	 * @param logset
	 * @param editor (Sequence Diagram, Gantt Chart, Gantt Overview Chart, Text Browser, Plot)
	 * @param ui
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void openLogsetEditor(String projectName, String logset, String editor, 
			IUIContext ui) throws Exception {
		
		ui.pause(2500);
		ui.ensureThat(new ViewLocator(OVERVIEW_VIEW_ID).isClosed());
		ui.contextClick(new TreeItemLocator(projectName + "/" + logset, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)),
				"Open/" + editor);	
	}

	/**
	 * Open "Logset Overview" View
	 * @param ui
	 * @throws Exception
	 */
	public static void openLogsetOverview(IUIContext ui) throws Exception {
		ui.ensureThat(new ViewLocator(OVERVIEW_VIEW_ID).isShowing());
	}
	
	/**
	 * Create Assertion Set.
	 * @param projectName
	 * @param assertionSetname
	 * @param ui
	 * @throws Exception
	 */
	public static void createAssertionSet(String projectName, String assertionSetname, 
			IUIContext ui) throws Exception {
		
		ui.contextClick(new TreeItemLocator(projectName, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)),
				"Edit Assertions");
		ui.wait(new ShellShowingCondition("Assertions"));
		ui.click(new ButtonLocator("New...", 0, new ShellLocator("Assertions")));
		ui.wait(new ShellShowingCondition("New Assertion Set"));
		ui.enterText(assertionSetname);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("New Assertion Set"));
		ui.click(new ButtonLocator("      Close      "));
		ui.ensureThat(new ShellLocator("Assertions").isClosed());
		ui.wait(new ShellDisposedCondition("Assertions"));
	}
	
	/**
	 * Create Assertion (Settings the basic requirements)
	 * @param projectName
	 * @param assertionSetname
	 * @param typePackageName
	 * @param eventName
	 * @param assertionName
	 * @param description
	 * @param ui
	 * @throws Exception
	 */
	public static void createBasicAssertion(String projectName, String assertionSetname, 
			String typePackageName, String eventName, String assertionName,  
			String description, IUIContext ui) throws Exception {

		ui.contextClick(new TreeItemLocator(projectName, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)),	"Edit Assertions");
		ui.wait(new ShellShowingCondition("Assertions"));
		ui.click(new ComboItemLocator(assertionSetname));
		ui.click(new ButtonLocator("New...", 1, new ShellLocator("Assertions")));
		ui.wait(new ShellShowingCondition("Assertion"));
		ui.click(new ButtonLocator("Select Type"));
		ui.wait(new ShellShowingCondition("Select Type"));
		ui.click(1, new TreeItemLocator(typePackageName + "/" + eventName), WT.SHIFT);
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Select Type"));
		ui.click(new LabeledTextLocator("Name:"));
		ui.enterText(assertionName);
		ui.click(new LabeledTextLocator("Description:"));
		ui.enterText(description);
	}
	
	/**
	 * Modify Assertion settings.
	 * @param projectName
	 * @param assertionSetName
	 * @param eventName
	 * @param ui
	 * @throws Exception
	 */
	public static void editAssertion(String projectName, String assertionSetName, 
			String eventName, IUIContext ui) throws Exception {

		ui.contextClick(new TreeItemLocator(projectName, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)), "Edit Assertions");
		ui.wait(new ShellShowingCondition("Assertions"));
		ui.click(new ComboItemLocator(assertionSetName));
		ui.click(new ListItemLocator(eventName));
		ui.click(new ButtonLocator("Edit..."));
		ui.wait(new ShellShowingCondition("Assertion"));
	}
	
	/**
	 * Open Search dialog box.
	 * @param projectName
	 * @param logsetName
	 * @param typePackageName
	 * @param eventName
	 * @param ui
	 * @throws Exception
	 */
	public static void openSearchDialog(String projectName, String logsetName, String typePackageName, String eventName, 
			IUIContext ui) throws Exception {

		ui.contextClick(new TreeItemLocator(projectName + "/" + logsetName, new ViewLocator(
				SYSTEM_NAVIGATOR_VIEW_ID)), "Search");
		ui.wait(new ShellShowingCondition("Search"));
		ui.click(new ButtonLocator("Select Type"));
		ui.wait(new ShellShowingCondition("Select Type"));
		ui.click(new TreeItemLocator(typePackageName + "/" + eventName));
		ui.click(new ButtonLocator("OK"));
		ui.wait(new ShellDisposedCondition("Select Type"));
	}
	
	/**
	 * Delay the operation.
	 * @param delay
	 * @param ui
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static void delay(int delay, IUIContext ui) throws Exception {
		ui.pause(delay);
	}
}	
