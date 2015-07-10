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

package com.ose.ui.tests.systemprofiler;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import com.ose.prof.ui.ProfilerPlugin;
import com.ose.system.ui.SystemBrowserPlugin;
import com.ose.ui.tests.AbstractTargetBaseTest;
import com.ose.ui.tests.utils.MiscUtils;
import com.ose.ui.tests.utils.ProfilerUtils;
import com.ose.ui.tests.utils.ResourceUtils;
import com.ose.ui.tests.utils.UIUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.NamedWidgetLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestCPUProcess extends AbstractTargetBaseTest
{
   private static final String PROJECT_NAME = "Profiler_Project_Process";

   private static final String XML_FILENAME = "ct_reports.report";

   private static final String PMD_FILENAME = "ct_reports.pmd";

   private static final String BLOCK = "psi";

   private static final int DELAY = 5000;
   
   private boolean deleteResources = true;

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      IUIContext ui = getUI();
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("System Profiling").isActive());
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();
   }

   public void setUp() throws Exception
   {
      super.setUp();
      // Create a new Project
      ResourceUtils.createNewProject(getUI(), PROJECT_NAME);
   }

   public void tearDown() throws Exception
   {
      super.tearDown();
      // Remove project
		if (deleteResources) 
		{
			ResourceUtils.deleteProject(getUI(), PROJECT_NAME);
			ResourceUtils.cleanSystemProfilerView(getUI());
		}
   }

   public void testSessionCreate() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Set Environment variable of BLOCK
      ui.contextClick(new TreeItemLocator(
            getBlockRegex(LOAD_MODULE_NAME, BLOCK), new ViewLocator(
                  SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Set Environment Variable...");
      ui.wait(new ShellShowingCondition("Set Environment Variable"));
      ui.enterText("action");
      ui.click(new LabeledTextLocator("Value:"));
      ui.enterText("cpu");
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Set Environment Variable"));

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Remove Target from System Profiler view
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testSaveAndOpenXML() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, XML_FILENAME);

      // Open XML file
      ResourceUtils.openFileFromWorkspace(ui,
            PROJECT_NAME + "/" + XML_FILENAME, "System Profiler Editor");

      // Close all file
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

   }

   public void testSaveAndOpenPMD() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME);

      // Open PMD file
      ResourceUtils.openFileFromWorkspace(ui,
            PROJECT_NAME + "/" + PMD_FILENAME, "System Profiler Editor");

      // Close all file
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testDisableProf() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Disable profiling
      ProfilerUtils.toggleTraceButton(ui, item);

      // Enable Profiling
      ProfilerUtils.toggleTraceButton(ui, item);

      // Start Profiling
      startProfiling(ui, item, DELAY);

      // Remove Target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testRemoveTarget() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

      // Add target with different parameter
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Integration Interval (ticks):"));
      ui.enterText("200");
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("40");
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      
      ui.click(new CTabItemLocator("Table"));
      ui.assertThat(new NamedWidgetLocator("integrationIntervalLabel").hasText("200"));
      ui.assertThat(new NamedWidgetLocator("maxReportsLabel").hasText("40"));
      ui.assertThat(new NamedWidgetLocator("prcPerReportLbl").hasText("8"));
      // Close all the editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testTooManyProc() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Create setting file for profiler process
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Profiled Processes Settings");
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.enterText("four_proc.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Customize the setting file
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("ping");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("pong");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("dots");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("cplusplus");
      // Save the setting file
      ui.click(new MenuItemLocator("File/Save All"));

      // Add target in System profiler view with different parameters
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.click(2, new LabeledTextLocator("Processes per Report:"));
      ui.enterText("3");
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      
      ui.click(new CTabItemLocator("Table"));
      ui.assertThat(new NamedWidgetLocator("integrationIntervalLabel").hasText("100"));
      ui.assertThat(new NamedWidgetLocator("maxReportsLabel").hasText("20"));
      ui.assertThat(new NamedWidgetLocator("prcPerReportLbl").hasText("3"));

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Set .process setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "four_proc.process");

      // Error caught
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);

      assertTrue(text.contains("Error"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testProfFileWithErrors() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System Profiler view
      addTarget(ui);

      // Create profiler setting file with error
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Profiled Processes Settings");
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.enterText("pp_with_errors.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Customize the setting file
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      // Save the setting file
      ui.click(new MenuItemLocator("File/Save All"));

      // Set .process setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "pp_with_errors.process");

      // Error caught
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);

      assertTrue(text.contains("Invalid profiled processes settings file"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

      // Close all the editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testProfFileExists() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in system profiler
      addTarget(ui);

      // Create profiler setting file
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Profiled Processes Settings");
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.enterText("pp_correct.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Customize the setting file
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("alfa_0");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("beta_0");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("psi_0");
      // Save the setting file
      ui.click(new MenuItemLocator("File/Save All"));

      // Set setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "pp_correct.process");
      MiscUtils.waitForJobs(ui);

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

      // Add target to System profiler
      addTarget(ui);

      // Get Settings file
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Get Profiled Processes Settings...");

      ResourceUtils.openFile(ui, PROJECT_NAME, "pp_target.process");
      MiscUtils.waitForJobs(ui);

      // Compare two setting files
      ui.click(new TreeItemLocator(PROJECT_NAME + "/pp_correct.process",
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")));
      ui.click(1, new TreeItemLocator(PROJECT_NAME + "/pp_target.process",
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")),
            WT.CTRL);
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/pp_correct.process",
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")),
            "Compare With/Each Other");
      ui.wait(new ShellShowingCondition(
            "Initializing Compare Editor for Compare .*"));
      ui.wait(new ShellDisposedCondition("Progress Information"));
      ui.wait(new ShellDisposedCondition(
            "Initializing Compare Editor for Compare .*"));

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

   }

   public void testProcProf() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in system profiler
      addTarget(ui);

      // Create .process file
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Profiled Processes Settings");
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.enterText("pp_example.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Customize the setting file
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("alf?_0");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("beta_*");
      // Save the setting file
      ui.click(new MenuItemLocator("File/Save All"));

      // Set .process setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "pp_example.process");

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

   }

   public void testProcProf2_ClearProcFile() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in system profiler
      addTarget(ui);

      // Create .process file
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")),
            "New/Profiled Processes Settings");
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.enterText("pp_example_2.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Customize the setting file
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("alfa:*");
      // Save the setting file
      ui.click(new MenuItemLocator("File/Save All"));

      // Set .process setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "pp_example_2.process");

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Clear Setting from target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Clear Profiled Processes Settings");
      UIUtils.delay(ui, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testPMDToXML() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME);
      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

      ResourceUtils.openFileFromWorkspace(ui,
            PROJECT_NAME + "/" + PMD_FILENAME, "System Profiler Editor");
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "NewXML.report");
      ui.click(new MenuItemLocator("File/Close All"));
      ResourceUtils.openFileFromWorkspace(ui, PROJECT_NAME + "/"
            + "NewXML.report", "System Profiler Editor");
      ui.click(new MenuItemLocator("File/Close All"));

   }

   public void testXMLToPMD() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, XML_FILENAME);
      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);

      ResourceUtils.openFileFromWorkspace(ui,
            PROJECT_NAME + "/" + XML_FILENAME, "System Profiler Editor");
      ui.click(new MenuItemLocator("File/Save As..."));
      ResourceUtils.saveData(ui, PROJECT_NAME, "NewPMD.pmd");
      ui.click(new MenuItemLocator("File/Close All"));
      ResourceUtils.openFileFromWorkspace(ui,
            PROJECT_NAME + "/" + "NewPMD.pmd", "System Profiler Editor");
      ui.click(new MenuItemLocator("File/Close All"));
   }

   public void testPMDMonitorReports() throws Exception
   {
      IUIContext ui = getUI();

      // Get Target reference
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME);
      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
      ui.click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
              SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.KEYPAD_SUBTRACT);


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
            + " \\(.+\\)" + "/" + ProfilerUtils.getNodeName(item) + " ("
            + ProfilerUtils.getNodeAdress(treeItem) + "\\/)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem tree_item = (TreeItem) reference.getWidget();

      // Break the tree node of this target
      ui.keyClick(WT.KEYPAD_ADD);
      MiscUtils.waitForJobs(ui);

      // Add PMD target on System profiler view
      ui.contextClick(
            new TreeItemLocator(PMD_FILENAME + " \\(.+\\)" + "/"
                  + ProfilerUtils.getNodeName(item) + " ("
                  + ProfilerUtils.getNodeAdress(treeItem) + "\\/)",
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target from System Profiler view
      ProfilerUtils.removeTargetFromView(ui, tree_item);

      // Remove Gate from System Browser
      ui.click(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.KEYPAD_SUBTRACT);
      ui.click(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)", new ViewLocator(
              SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Disconnect");
      MiscUtils.waitForJobs(ui);
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Clean");
      MiscUtils.waitForJobs(ui);
   }

   public void testBrowsePMD() throws Exception
   {
      IUIContext ui = getUI();

      // Get Target reference
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Add target in System profiler view
      addTarget(ui);

      // Start profiling
      startProfiling(ui, item, DELAY);

      // Stop profiling
      ProfilerUtils.stopProfiling(ui, item);

      // Save data
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 11,
            new SWTWidgetLocator(ToolBar.class)));
      ResourceUtils.saveData(ui, PROJECT_NAME, PMD_FILENAME);
      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
      ui.click(new TreeItemLocator(getTargetRegex(), new ViewLocator(
              SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      ui.keyClick(WT.KEYPAD_SUBTRACT);

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
            + " \\(.+\\)" + "/" + ProfilerUtils.getNodeName(item) + " ("
            + ProfilerUtils.getNodeAdress(treeItem) + "\\/)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      TreeItem tree_item = (TreeItem) reference.getWidget();

      // Break the tree node of this target
      ui.keyClick(WT.KEYPAD_ADD);
      MiscUtils.waitForJobs(ui);

      // Add PMD target on System profiler view
      ui.contextClick(
            new TreeItemLocator(PMD_FILENAME + " \\(.+\\)" + "/"
                  + ProfilerUtils.getNodeName(item) + " ("
                  + ProfilerUtils.getNodeAdress(treeItem) + "\\/)",
                  new ViewLocator(SystemBrowserPlugin.SYSTEM_VIEW_ID)),
            "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // report_read_next
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new TableItemLocator(UIUtils.getText(tree_item),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)));
      // Click Next Profiling Report button
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 10,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      ui.click(new CTabItemLocator("System Profiler"));
      // Click Previous Profiling Report button
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 9,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));

      // Remove Target from System Profiler view
      ProfilerUtils.removeTargetFromView(ui, tree_item);

      // Remove Gate from System Browser
      ui.click(new TreeItemLocator(PMD_FILENAME + " \\(.+\\)", new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)));
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Disconnect");
      MiscUtils.waitForJobs(ui);
      UIUtils.clickSystemBrowserContextMenuItem(ui, "Clean");
      MiscUtils.waitForJobs(ui);
   }

   public void testParamNotAcceptedHost() throws Exception
   {
      IUIContext ui = getUI();

      // Try to add target with wrong parameters
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Integration Interval (ticks):"));
      ui.enterText("0");
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("0");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("1");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("2");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.click(2, new LabeledTextLocator("Processes per Report:"));
      ui.enterText("-1");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Integration Interval (ticks):"));
      //ui.keyClick(WT.BS);
      ui.enterText("-");
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      //ui.keyClick(WT.BS);
      ui.enterText("-");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));

      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Processes per Report:"));
      //ui.keyClick(WT.BS);
      ui.enterText("-");
      ui.assertThat(new ButtonLocator("OK").isEnabled(false));
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("Target and Profiling Parameters"));
   }

   public void testParamNotAcceptedTarget() throws Exception
   {
      IUIContext ui = getUI();

      // Get Target reference
      TreeItem item = UIUtils.getItemReference(ui, getTargetRegex());

      // Try to add target with wrong parameters
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("500");
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      try
      {
    	  ui.wait(new ShellShowingCondition("Information"));
          ui.click(new ButtonLocator("OK"));
      }
      catch(Exception ex){
    	  ui.wait(new ShellDisposedCondition("Information"));
      }
      MiscUtils.waitForJobs(ui);

      ui.click(new CTabItemLocator("Table"));
      ui.assertThat(new NamedWidgetLocator("integrationIntervalLabel").hasText("100"));
      ui.assertThat(new NamedWidgetLocator("maxReportsLabel").hasText("50"));
      ui.assertThat(new NamedWidgetLocator("prcPerReportLbl").hasText("8")); 
      
      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Remove Target from System Profiler view
      ProfilerUtils.removeTargetFromView(ui, item);
   }

   public void testRefuseSession() throws Exception
   {
      IUIContext ui = getUI();

      // Add target to system profiler
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);

      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Add another target to System profiler view
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Integration Interval (ticks):"));
      ui.enterText("200");
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.click(2, new LabeledTextLocator("Processes per Report:"));
      ui.enterText("20");
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      try
      {
    	  ui.wait(new ShellShowingCondition("Information"));
          ui.click(new ButtonLocator("OK"));
      }
      catch(Exception ex){
    	  ui.wait(new ShellDisposedCondition("Information"));
      }
      MiscUtils.waitForJobs(ui);
      
      ui.click(new CTabItemLocator("Table"));
      ui.assertThat(new NamedWidgetLocator("integrationIntervalLabel").hasText("100"));
      ui.assertThat(new NamedWidgetLocator("maxReportsLabel").hasText("50"));
      ui.assertThat(new NamedWidgetLocator("prcPerReportLbl").hasText("8"));
      
      // Close the all editors
      ui.click(new MenuItemLocator("File/Close All"));

      // Start & Stop Profiling
      ui.click(new CTabItemLocator("System Profiler"));
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 8,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));
      UIUtils.delay(ui, DELAY);
      ui.click(new SWTWidgetLocator(ToolItem.class, "", 8,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))));

      // Remove multiple targets from System Profiler view
      ui.click(new CTabItemLocator("System Profiler"));
      IWidgetReference items[] = (IWidgetReference[]) ui
            .findAll(new TableItemLocator(".*"));
      for (IWidgetReference locator : items)
      {
         ui.click(locator);
         ui.click(new SWTWidgetLocator(ToolItem.class, "", 1,
               new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                     Composite.class))));
         MiscUtils.waitForJobs(ui);
      }
   }
   
   public void testNotProcFile() throws Exception
   {
      IUIContext ui = getUI();

      // Get target node information
      TreeItem item = (TreeItem) UIUtils.getItemReference(ui,
            getTargetRegex());

      // Create blank setting file for profiler process
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")), "New/File");
      ui.wait(new ShellShowingCondition("New File"));
      ui.enterText("nppfile.process");
      ui.click(new ButtonLocator("Finish"));
      ui.wait(new ShellDisposedCondition("New File"));
      ui.click(new MenuItemLocator("File/Close All"));

      // Add target in System profiler view
      addTarget(ui);

      // Set .process setting file to the target
      ui.contextClick(new TableItemLocator(UIUtils.getText(item).trim(),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Set Profiled Processes Settings...");
      ResourceUtils.openFile(ui, PROJECT_NAME, "nppfile.process");

      // Error caught
      ui.wait(new ShellShowingCondition("Problem Occurred"));
      ui.click(new ButtonLocator("&Details >>"));
      // Find styled text widget and get its contents
      IWidgetReference ref = (IWidgetReference) ui.find(new SWTWidgetLocator(
            StyledText.class));
      StyledText foundWidget = (StyledText) ref.getWidget();
      String text = UIUtils.getText(foundWidget);

      assertTrue(text.contains("Invalid profiled processes settings file"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Problem Occurred"));

      // Remove target
      ProfilerUtils.removeTargetFromView(ui, item);
      deleteResources = false;
   }


   private void addTarget(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(getTargetRegex(), new ViewLocator(
            SystemBrowserPlugin.SYSTEM_VIEW_ID)), "Show in System Profiler");
      ui.wait(new ShellShowingCondition("Target and Profiling Parameters"));
      ui.wait(new ShellDisposedCondition("Finding Targets"));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new ComboItemLocator("CPU Usage / Process",
            new SWTWidgetLocator(Combo.class, 1, new SWTWidgetLocator(
                  Composite.class))));
      ui.wait(new ShellDisposedCondition("Restoring profiling parameters"));
      ui.click(new SWTWidgetLocator(Label.class, "Profiling Parameters"));
      ui.click(2, new LabeledTextLocator("Number of Reports on Target:"));
      ui.enterText("20");
      ui.click(new ButtonLocator("OK"));
      MiscUtils.waitForJobs(ui);
      
      ui.click(new CTabItemLocator("Table"));
      ui.assertThat(new NamedWidgetLocator("integrationIntervalLabel").hasText("100"));
      ui.assertThat(new NamedWidgetLocator("maxReportsLabel").hasText("20"));
      ui.assertThat(new NamedWidgetLocator("prcPerReportLbl").hasText("8"));
      
      // Close all the editors
      ui.click(new MenuItemLocator("File/Close All"));
   }

   private void startProfiling(IUIContext ui, TreeItem item, int profilingTime)
         throws Exception
   {
      // Start Profiling
      ui.click(new CTabItemLocator("System Profiler"));
      assertTrue(new SWTWidgetLocator(ToolItem.class, "", 7,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))).isEnabled(ui));
      ui.contextClick(new TableItemLocator(ProfilerUtils.getTargetRegex(item),
            new ViewLocator(ProfilerPlugin.PROFILER_VIEW_ID)),
            "Start\\/Stop Profiling");
      assertTrue(new SWTWidgetLocator(ToolItem.class, "", 8,
            new SWTWidgetLocator(ToolBar.class, 0, new SWTWidgetLocator(
                  Composite.class))).isEnabled(ui));
      UIUtils.delay(ui, profilingTime);

   }

}
