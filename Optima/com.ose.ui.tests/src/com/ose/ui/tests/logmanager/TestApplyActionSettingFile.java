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

import org.eclipse.swt.custom.StyledText;
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
import com.windowtester.runtime.WT;
import com.windowtester.runtime.WidgetSearchException;
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

public class TestApplyActionSettingFile extends AbstractTargetBaseTest
{
   private static final String PROJECT_NAME = "LogManager_ActionFile";

   private static final String XML_FILENAME = "not_an_ea";

   private static final String BLANK_FILENAME = "not_ea";

   private static final String ERROR_ACTION_FILE = "error_ea.action";

   private static final String CORRECT_ACTION_FILENAME = "correct.action";
   
   private static final String BIND_FILENAME = "withbind.action";
   
   private static final String ONLY_BIND_FILENAME = "onlybind.action";
   
   private static final String BLOCK_NAME = "alfa";
   
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

   public void testNotAnEventActionFile() throws Exception
   {
      IUIContext ui = getUI();

      // Create an XML file
      createXMLFile(ui);

      // Create blank file
      createBlankFile(ui);

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());
      
      // Add Target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Enable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Set XML file to target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      MiscUtils.waitForJobs(ui);
      ResourceUtils.openFile(ui, PROJECT_NAME, (XML_FILENAME + ".action"));
      MiscUtils.waitForJobs(ui);
      // Error occurred
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);
      assertTrue(text.contains("Invalid event action settings file"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

      // Set blank file to target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Set Event Actions Settings...");
      MiscUtils.waitForJobs(ui);
      ResourceUtils.openFile(ui, PROJECT_NAME, (BLANK_FILENAME + ".action"));
      MiscUtils.waitForJobs(ui);
      // Error occurred
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      ref = (IWidgetReference) ui.find(new SWTWidgetLocator(StyledText.class));
      foundWidget = (StyledText) ref.getWidget();
      text = UIUtils.getText(foundWidget);
      assertTrue(text.contains("Invalid event action settings file"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

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

   public void testEventActionHasError() throws Exception
   {
      IUIContext ui = getUI();

      // Create an error .action file
      createErrorActionFile(ui);

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());

      // Add Target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
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
      MiscUtils.waitForJobs(ui);
      ResourceUtils.openFile(ui, PROJECT_NAME, ERROR_ACTION_FILE);
      MiscUtils.waitForJobs(ui);
      // Error occurred
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);
      assertTrue(text.contains("Invalid event action settings file"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

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

   public void testTargetHasSettings() throws Exception
   {
      IUIContext ui = getUI();

      // Create a correct .action file
      createCorrectActionFile(ui);

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
      MiscUtils.waitForJobs(ui);
      ResourceUtils.openFile(ui, PROJECT_NAME, CORRECT_ACTION_FILENAME);
      MiscUtils.waitForJobs(ui);
      
      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

      // Add Target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Get Settings file
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Get Event Actions Settings...");
      ResourceUtils.saveData(ui, PROJECT_NAME, "targets.action");

      // Clear Setting
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Clear Event Actions Settings");

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);

      // Add Target
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Disable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Remove Target
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Remove Target");
      MiscUtils.waitForJobs(ui);
   }
   
   public void testSystemIntercept() throws Exception
   {
      IUIContext ui = getUI();

      // Get the target node information
      TreeItem treeItem = UIUtils.getItemReference(ui, getTargetRegex());

      // Add Target
      ui.contextClick(
            new TreeItemLocator(getBlockRegex(LOAD_MODULE_NAME, BLOCK_NAME),
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in Log Manager");
      ui.wait(new ShellShowingCondition("Target and Scope Selection"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.click(new ButtonLocator("Persistent Event Trace Actions"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Enable Trace
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)),
            "Enable\\/Disable Event Trace");

      // Intercept
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Intercept");

      // Update Target in System Browser View
      ui.click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.F5);

      // Resume
      ui.contextClick(new TableItemLocator(UIUtils.getText(treeItem).trim(),
            new ViewLocator(EventPlugin.EVENT_VIEW_ID)), "Resume");

      // Update Target in System Browser View
      ui.click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.F5);

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

   private void createOnlyBindActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(ONLY_BIND_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Bind", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));
   }

   private void createWithBindActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(BIND_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
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
      ui.click(new ListItemLocator("Bind", new SWTWidgetLocator(List.class, 0,
            new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));
   }

   private void createCorrectActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(CORRECT_ACTION_FILENAME);
      ui.keyClick(WT.CR);
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ListItemLocator("Receive", new SWTWidgetLocator(List.class,
            0, new SWTWidgetLocator(Composite.class))));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));

      ui.click(new MenuItemLocator("File/Save All"));

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));
   }

   private void createErrorActionFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Event Action Settings");
      ui.wait(new ShellShowingCondition("New Event Action Settings"));
      ui.enterText(ERROR_ACTION_FILE);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Event Action Settings"));
      ui.click(new ButtonLocator("Add..."));
      ui.wait(new ShellShowingCondition("New Event Action"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Event Action"));
      ui.click(2, new LabeledTextLocator("Signal Number Range:"));
      ui.keyClick(WT.DEL);

      ui.click(new MenuItemLocator("File/Save All"));

      // Close all editors
      ui.click(new MenuItemLocator("File/Close All"));
   }

   private void createBlankFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")), "New/File");
      ui.wait(new ShellShowingCondition("New File"));
      ui.enterText(BLANK_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New File"));
      ui.enterText("Test File");
      ui.click(new MenuItemLocator("File/Save All"));
      ui.click(new MenuItemLocator("File/Close All"));
      renameFile(ui, BLANK_FILENAME);
   }
   
   private void renameFile(IUIContext ui, String fileName) throws WidgetSearchException
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/" + fileName,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")),
            "Rename...");
      ui.wait(new ShellShowingCondition("Rename Resource"));
      ui.keyClick(WT.DEL);
      ui.click(2, new LabeledTextLocator("New na&me:"));
      ui.enterText(fileName + ".action");
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Rename Resource"));
   }

   private void createXMLFile(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")), "New/File");
      ui.wait(new ShellShowingCondition("New File"));
      ui.enterText(XML_FILENAME);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New File"));
      ui.enterText("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
      ui.click(new MenuItemLocator("File/Save All"));
      ui.click(new MenuItemLocator("File/Close All"));
      renameFile(ui, XML_FILENAME);
   }

}
