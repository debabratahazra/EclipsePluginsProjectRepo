/* COPYRIGHT-ENEA-SRC-R2 *
 **************************************************************************
 * Copyright (C) 2010 by Enea Software AB.
 * All rights reserved.
 *
 * This Software is furnished under a software license agreement and
 * may be used only in accordance with the terms of such agreement.
 * Any other use or reproduction is prohibited. No title to and
 * ownership of the Software is hereby transferred.
 *
 * PROPRIETARY NOTICE
 * This Software consists of confidential information.
 * Trade secret law and copyright law protect this Software.
 * The above notice of copyright on this Software does not indicate
 * any actual or intended publication of such Software.
 **************************************************************************
 * COPYRIGHT-END */

package com.ose.ui.tests.logmanager;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.event.ui.EventPlugin;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.ProfilerUtils;
import com.ose.ui.tests.utils.ResourceUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestTraceNotifyFromFile extends AbstractTargetBaseTest
{
   private static final String PROJECT_NAME = "LogManager_TraceNotify";

   private static final String TRACE_FILENAME = "eventaction-trace.action";

   private static final String NOTIFY_FILENAME = "ea-notify3.action";

   private static final String TRACE_FILENAME_LA = "log-analyzer.action";

   private static final String PMD_FILENAME = "trace1.pmd";

   private static final String PMD_FILENAME_2 = "notify1.pmd";

   private static final int DELAY = 5000;

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      IUIContext ui = getUI();
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("Log Management").isActive());

      // Create a project
      ResourceUtils.createNewProject(ui, PROJECT_NAME);
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();

      // Remove project
      ResourceUtils.deleteProject(getUI(), PROJECT_NAME);
   }

   public void setUp() throws Exception
   {
      super.setUp();
   }

   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testSaveTraceEvents() throws Exception
   {
      IUIContext ui = getUI();

      // Create a .action file
      createTraceActionFile(ui);

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());

      // Add Target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Enable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, TRACE_FILENAME);

      // Start Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      UIUtils.delay(ui, DELAY);

      // Stop Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      MiscUtils.waitForJobs(ui);

      // Save Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Save Event Trace...");
      ResourceUtils.saveData(ui, PROJECT_NAME, "trace1.event");

      // Open Event file
      ResourceUtils.openFileFromWorkspace(ui, PROJECT_NAME + "/trace1.event",
            "Event Editor");
      MiscUtils.waitForJobs(ui);

      // Save As.. PMD file
      ui.click(new CTabItemLocator("trace1.event"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME);

      // Save As.. PMD-2 file
      ui.click(new CTabItemLocator("trace1.event"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "trace2.pmd");

      // Open PMD file
      ResourceUtils.openFileFromWorkspace(ui, PROJECT_NAME + "/trace2.pmd",
            "Event Editor");
      MiscUtils.waitForJobs(ui);

      // Save As.. PMD-2 file
      ui.click(new CTabItemLocator("trace2.pmd"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "trace2.event");

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

   }

   public void testEventSaveNotify() throws Exception
   {
      IUIContext ui = getUI();

      // Create .action file
      createNotifyActionFile(ui);

      // Get the target node information
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Tab to "Notify"
      ui.click(new CTabItemLocator("Log Manager"));
      ui.click(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)));
      ui.click(new CTabItemLocator("Notify"));
      UIUtils.delay(ui, 1000);
      
      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, NOTIFY_FILENAME);
      UIUtils.delay(ui, DELAY);
      
      //Pause so that windowtester does not get flooded with UI input while trying to open context menu
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 17,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));

      // Disable Notify
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Notify");
      MiscUtils.waitForJobs(ui);

      // Save Event
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Save Event Notifications...");
      ResourceUtils.saveData(ui, PROJECT_NAME, "notify1.event");

      // Open in Editor
      ResourceUtils.openFileFromWorkspace(ui, PROJECT_NAME + "/notify1.event",
            "Event Editor");
      MiscUtils.waitForJobs(ui);

      // Save As.. PMD file
      ui.click(new CTabItemLocator("notify1.event"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME_2);

      // Save As... PMD-2 file
      ui.click(new CTabItemLocator("notify1.event"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "notify2.pmd");

      // Open in Editor
      ResourceUtils.openFileFromWorkspace(ui, PROJECT_NAME + "/notify2.pmd",
            "Event Editor");
      MiscUtils.waitForJobs(ui);

      // Save As... EVENT file
      ui.click(new CTabItemLocator("notify2.pmd"));
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "notify2.event");

      // Enable Notify
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Notify");

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }

   public void testEventTracePMDMonitor() throws Exception
   {
      IUIContext ui = getUI();

      // Run As PMD Dump
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/" + PMD_FILENAME,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")),
            "Run As/1 OSE C\\/C++ Postmortem Dump");
      ui.wait(new ShellShowingCondition("Launch Configuration Selection"));
      ui.click(new ButtonLocator("Create New"));
      ui.wait(new ShellDisposedCondition("Launch Configuration Selection"));
      ui.wait(new ShellShowingCondition("Run Configurations"));
      ui.click(new ButtonLocator("&Run"));
      ui.wait(new ShellDisposedCondition("Run Configurations"));
      MiscUtils.waitForJobs(ui);

      // Get reference of PMD gate.
      IWidgetReference reference = (IWidgetReference) ui
            .click(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)",
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem treeItem = (TreeItem) reference.getWidget();

      // Click on PMD target
      reference = (IWidgetReference) ui.click(new TreeItemLocator(PMD_FILENAME
            + " \\(.+\\)" + "/" + "nameless" + " ("
            + ProfilerUtils.getNodeAdress(treeItem) + "\\/)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem tree_item = (TreeItem) reference.getWidget();

      // Break the tree node of this target
      ui.contextClick(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Connect");
      MiscUtils.waitForJobs(ui);

      // Add PMD target on System profiler view
      ui.contextClick(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Remove Target from view
      ui.contextClick(new TableItemLocator(UIUtils.getText(tree_item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

      // Remove Gate from System Browser
      ui.click(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.contextClick(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Disconnect");
      MiscUtils.waitForJobs(ui);
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Clean");
      MiscUtils.waitForJobs(ui);

   }

   public void testEventNotifyPMDMonitor() throws Exception
   {
      IUIContext ui = getUI();

      // Run As PMD Dump
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/" + PMD_FILENAME_2,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")),
            "Run As/1 OSE C\\/C++ Postmortem Dump");
      ui.wait(new ShellShowingCondition("Launch Configuration Selection"));
      ui.click(new ButtonLocator("Create New"));
      ui.wait(new ShellDisposedCondition("Launch Configuration Selection"));
      ui.wait(new ShellShowingCondition("Run Configurations"));
      ui.click(new ButtonLocator("&Run"));
      ui.wait(new ShellDisposedCondition("Run Configurations"));
      MiscUtils.waitForJobs(ui);

      // Get reference of PMD gate.
      IWidgetReference reference = (IWidgetReference) ui
            .click(new TreeItemLocator(PMD_FILENAME_2 + " \\(.+\\)",
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem treeItem = (TreeItem) reference.getWidget();

      // Click on PMD target
      reference = (IWidgetReference) ui.click(new TreeItemLocator(
            PMD_FILENAME_2 + " \\(.+\\)" + "/" + "nameless" + " ("
                  + ProfilerUtils.getNodeAdress(treeItem) + "\\/)",
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem tree_item = (TreeItem) reference.getWidget();

      // Break the tree node of this target
      ui.contextClick(new TreeItemLocator(PMD_FILENAME_2 + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Connect");
      MiscUtils.waitForJobs(ui);

      // Add PMD target on System profiler view
      ui.contextClick(new TreeItemLocator(PMD_FILENAME_2 + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Remove Target from view
      ui.contextClick(new TableItemLocator(UIUtils.getText(tree_item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

      // Remove Gate from System Browser
      ui.click(new TreeItemLocator(PMD_FILENAME_2 + " \\(.+\\)",
            new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.contextClick(new TreeItemLocator(PMD_FILENAME_2 + " \\(.+\\)" + "/"
            + "nameless" + " (" + ProfilerUtils.getNodeAdress(treeItem)
            + "\\/)", new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Disconnect");
      MiscUtils.waitForJobs(ui);
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Clean");
      MiscUtils.waitForJobs(ui);
   }

   public void testEventSavePMDForLA() throws Exception
   {
      IUIContext ui = getUI();

      // Create .action file
      createActionFileForLA(ui);

      // Get the target node information
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Enable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, TRACE_FILENAME_LA);

      // Start Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      UIUtils.delay(ui, DELAY);

      // Stop Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      MiscUtils.waitForJobs(ui);

      // Save PMD file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Save Event Trace...");
      ResourceUtils.saveData(ui, PROJECT_NAME, "log-analyzer.pmd");

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

   }

   private void createActionFileForLA(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.click(new LabeledTextLocator("File na&me:"));
      ui.enterText(TRACE_FILENAME_LA);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));

      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Send", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ListItemLocator("Trace", new SWTWidgetLocator(List.class, 1,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Receive", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Swap", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Create", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Kill", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));

   }

   private void createNotifyActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(NOTIFY_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Send", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ListItemLocator("Notify", new SWTWidgetLocator(List.class,
            1, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Receive", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Create", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Alloc", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Free", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Timeout", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("User", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));
   }

   private void createTraceActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.click(new LabeledTextLocator("File na&me:"));
      ui.enterText(TRACE_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));

      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Send", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ListItemLocator("Trace", new SWTWidgetLocator(List.class, 1,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Receive", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Swap", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Create", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Timeout", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("User", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));
   }

}
