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

package com.ose.ui.tests.loganalyzer;

import org.eclipse.swt.widgets.Tree;
import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.MenuItemLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.LabeledLocator;
import com.windowtester.runtime.swt.locator.ShellLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestReport extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_Report";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, getUI());
   }

   protected void setUp() throws Exception
   {
      super.setUp();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();

      LogAnalyzerUtil.deleteProject(PROJECT_NAME, getUI());
   }

   /**
    * Test Report.
    * 
    * @throws Exception
    */
   public void testReport() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TIMELINE_BROWSER, ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.GANTT_CHART, ui);
      runAssertion(ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.PLOT, ui);
      runPlot(ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.SEQUENCE_DIAGRAM, ui);
      ui.click(new MenuItemLocator("File/New/Report"));
      openReportWizard(ui);
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
            "New/Report");
      openReportWizard(ui);
      deleteReport(ui);
   }

   private void openReportWizard(IUIContext ui) throws Exception
   {
      ui.wait(new ShellShowingCondition("New Report"));
      ui.click(new ButtonLocator("&Next >"));
      ui.click(1, new TreeItemLocator(PROJECT_NAME, new LabeledLocator(
            Tree.class, "Select the destination Project:")), WT.SHIFT);
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Report"));
      ui.wait(new ShellDisposedCondition(
            "LogAnalyzer deleting log files Activity"));
      MiscUtils.waitForJobs(ui);
   }

   private void deleteReport(IUIContext ui) throws Exception
   {
      ui.click(1, new TreeItemLocator(PROJECT_NAME + "/reports",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      ui.keyClick(WT.DEL);
      ui.wait(new ShellDisposedCondition("Progress Information"));
      ui.wait(new ShellShowingCondition("Delete Resources"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Delete Resources"));
      MiscUtils.waitForJobs(ui);
   }

   private void runPlot(IUIContext ui) throws Exception
   {
      ui.wait(new ShellShowingCondition("Open Plot"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Open Plot"));
      MiscUtils.waitForJobs(ui);
   }

   private void runAssertion(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "Edit Assertions");
      ui.wait(new ShellShowingCondition("Assertions"));
      ui.click(new ButtonLocator("New...", 0, new ShellLocator("Assertions")));
      ui.wait(new ShellShowingCondition("New Assertion Set"));
      ui.enterText("Set1");
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("New Assertion Set"));
      ui.click(new ButtonLocator("New...", 1, new ShellLocator("Assertions")));
      ui.wait(new ShellShowingCondition("Assertion"));
      ui.enterText("ILogEvent");
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Assertion"));
      ui.click(new ButtonLocator("      Close      "));
      ui.ensureThat(new ShellLocator("Assertions").isClosed());
      ui.wait(new ShellDisposedCondition("Assertions"));
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
            "Run Assertions");
      ui.wait(new ShellShowingCondition("Select Assertion Sets to Run"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Select Assertion Sets to Run"));
      ui.wait(new ShellDisposedCondition("Assertion Results"));

   }

}
