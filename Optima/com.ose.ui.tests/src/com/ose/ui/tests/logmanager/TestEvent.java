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

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.event.ui.EventPlugin;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.ResourceUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestEvent extends AbstractTargetBaseTest
{
   private static final String PROJECT_NAME = "LogManager_Event";
   
   private static final String TRACE_FILENAME = "ea-trace.action";
   
   private static final String NOTIFY_FILENAME = "ea-notify.action";
   
   private static final String NOTIFY2_FILENAME = "ea-notify2.action";
   
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

   public void testTraceEvents() throws Exception
   {
      IUIContext ui = getUI();

      // Create a .action file
      createTraceActionFile(ui);

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target in Log Manager view
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

      // Set Setting file
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, TRACE_FILENAME);
 
      // Start Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      UIUtils.delay(ui, DELAY);

      //Pause so that windowtester does not get flooded with UI input while trying to open context menu
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 17,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      
      // Stop Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      MiscUtils.waitForJobs(ui);

      // Get Setting File
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Get Event Actions Settings...");
      ResourceUtils.saveData(ui, PROJECT_NAME, "saved-ea-settings.action");

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

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Get Trace Events - FIXME (Exception occurred)
      /**
       * ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
       * new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Get Event Trace...");
       * MiscUtils.waitForJobs(ui); ui.keyClick(WT.CR);
       * MiscUtils.waitForJobs(ui);
       */

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");

      // Add target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      
      // Enable Trace - Start Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      UIUtils.delay(ui, DELAY);

      // Clear Setting
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Clear Event Actions Settings");
      UIUtils.delay(ui, DELAY);

      // Stop Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Start\\/Stop Reading Event Trace");
      MiscUtils.waitForJobs(ui);

      // Clear Event Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Clear Event Trace on Target");
      MiscUtils.waitForJobs(ui);

      // Get Trace - FIXME (Exception occurred)
      /**
       * ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
       * new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Get Event Trace...");
       * MiscUtils.waitForJobs(ui); ui.assertThat(new
       * ButtonLocator("OK").isEnabled(false)); ui.click(new
       * ButtonLocator("Cancel")); MiscUtils.waitForJobs(ui);
       */

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");
      MiscUtils.waitForJobs(ui);

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }
   
   public void testNotifyEvents() throws Exception
   {
      IUIContext ui = getUI();

      // Create .action file
      createNotifyActionFile(ui);

      // Get the target node information
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add Target in Log manager view
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.click(new ComboItemLocator("System", new SWTWidgetLocator(Combo.class,
            1, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Tab to "Notify" in Event Editor
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

      // Enable Notify
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Notify");
      UIUtils.delay(ui, DELAY);

      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, NOTIFY2_FILENAME);
      UIUtils.delay(ui, DELAY);

      // Disable Notify
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Notify");
      MiscUtils.waitForJobs(ui);
      
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 17,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));

      // Clear Setting
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Clear Event Actions Settings");
      MiscUtils.waitForJobs(ui);

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");
      // Disable Notify
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Notify");

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }

   private void createNotifyActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.click(new LabeledTextLocator("File na&me:"));
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
      ui.click(new MenuItemLocator("File/Save All"));

      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(NOTIFY2_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Create", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ListItemLocator("Notify", new SWTWidgetLocator(List.class,
            1, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Kill", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
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
      ui.click(new ListItemLocator("Kill", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
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

}
