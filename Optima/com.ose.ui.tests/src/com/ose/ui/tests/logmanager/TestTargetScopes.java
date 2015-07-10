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
import com.windowtester.runtime.swt.locator.ListItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestTargetScopes extends AbstractTargetBaseTest
{
   private static final String PROJECT_NAME = "LogManager_Target";

   private static final String NOTIFY_FILENAME = "eventaction-notify";

   private static final String BLOCK = "alfa";

   private static final String PROCESS = "alfa_0";

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

   public void testEventBlockScope() throws Exception
   {
      IUIContext ui = getUI();

      // Get the target node information
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Create .action file
      createNotifyFile(ui);

      // Add target
      ui.contextClick(new TreeItemLocator(
            getBlockRegex(LOAD_MODULE_NAME, BLOCK), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Tab to "Notify"
      ui.click(new CTabItemLocator("Log Manager"));
      ui.click(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)));
      ui.click(new CTabItemLocator("Notify"));

      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, NOTIFY_FILENAME);
      UIUtils.delay(ui, DELAY);

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

   }

   public void testEventProcessScope() throws Exception
   {
      IUIContext ui = getUI();

      // Get the target node information
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target
      ui.contextClick(
            new TreeItemLocator(getProcessRegex(LOAD_MODULE_NAME, BLOCK,
                  PROCESS), new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Tab to "Notify"
      ui.click(new CTabItemLocator("Log Manager"));
      ui.click(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)));
      ui.click(new CTabItemLocator("Notify"));

      // Set .action file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, NOTIFY_FILENAME);
      UIUtils.delay(ui, DELAY);

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }

   private void createNotifyFile(IUIContext ui) throws Exception
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
