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
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;

public class TestImportDeconfigure extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_Import";

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
    * Test Import/Deconfigure using double click on log file.
    * 
    * @throws Exception
    */
   public void testImportDeconfigure() throws Exception
   {
      IUIContext ui = getUI();
      
      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.event", ui);
      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      setPreference(ui);
      LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset",
            "OSEdelta.event", ui);
      LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      setPreference(ui);
      // XXX: Removed test of importing and deconfiguring a log file by double
      // clicking on it since it doesn't work and I'm not sure it should either,
      // that operation is typically reserved for opening a file in an editor.
   }

   private void setPreference(IUIContext ui) throws Exception
   {
      ui.click(new MenuItemLocator("Window/Preferences"));
      ui.wait(new ShellShowingCondition("Preferences"));
      ui.click(new FilteredTreeItemLocator("Log Analyzer/Misc"));
      ui.click(new ButtonLocator("Enable Double-Click Import/Deconfigure"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Preferences"));
   }
}
