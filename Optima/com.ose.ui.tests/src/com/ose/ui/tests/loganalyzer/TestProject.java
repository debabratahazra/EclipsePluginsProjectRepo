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

import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestProject extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project";

   private static final String OLD_LOGSET = "logset";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
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
   }

   /**
    * Create/Rename/Delete empty logset.
    * 
    * @throws Exception
    */
   public void testLogset() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
      LogAnalyzerUtil.renameLogset(PROJECT_NAME, OLD_LOGSET, "logset2", ui);
      LogAnalyzerUtil.deleteLogset(PROJECT_NAME, "logset2", ui);
      LogAnalyzerUtil.deleteProject(PROJECT_NAME, ui);

   }

   /**
    * Create/Rename/Delete empty project.
    * 
    * @throws Exception
    */
   public void testProject() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
      LogAnalyzerUtil.renameProject(PROJECT_NAME, "New_LA_Project", ui);
      LogAnalyzerUtil.deleteProject("New_LA_Project", ui);

   }

   /**
    * Try to create Project/logset with same name.
    * 
    * @throws Exception
    */
   public void testFailure() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
      createNewLogset(ui);
      createNewProject(ui);
      LogAnalyzerUtil.deleteProject(PROJECT_NAME, ui);

   }

   /**
    * Open Text Browser after import LOGFILE. Rename logset and Open Text
    * Browser. Rename Project and Open Text Browser.
    * 
    * @throws Exception
    */
   public void testProjectWithTextBrowser() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, ui);
      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      LogAnalyzerUtil.renameLogset(PROJECT_NAME, "logset", "logset2", ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset2",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      LogAnalyzerUtil.renameProject(PROJECT_NAME, "Test_Project", ui);
      LogAnalyzerUtil.deleteProject("Test_Project", ui);
   }

   private void createNewProject(IUIContext ui) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
            "New/Log Analyzer Project");
      ui.wait(new ShellShowingCondition("New Log Analyzer Project"));
      ui.enterText(PROJECT_NAME);
      MiscUtils.waitForJobs(ui);
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("New Log Analyzer Project"));

   }

   private void createNewLogset(IUIContext ui) throws Exception
   {
      ui.click(new TreeItemLocator(PROJECT_NAME + "/logset", new ViewLocator(
            LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "New/Logset");
      ui.wait(new ShellShowingCondition("New Logset"));
      ui.enterText("logset");
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("New Logset"));
      ui.contextClick(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)), "New/Logset");
      ui.wait(new ShellShowingCondition("New Logset"));
      ui.enterText("logset");
      MiscUtils.waitForJobs(ui);
      ui.click(new ButtonLocator("Cancel"));
      ui.wait(new ShellDisposedCondition("New Logset"));
   }
}
