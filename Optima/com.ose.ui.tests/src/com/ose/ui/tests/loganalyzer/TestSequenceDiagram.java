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
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestSequenceDiagram extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_SequenceDiagram";

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

   public void testSequenceDiagram() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.SEQUENCE_DIAGRAM, ui);

      stepOperation(true, 5, ui);
      stepOperation(false, 5, ui);
      ui.keyClick(WT.END);
      MiscUtils.waitForJobs(ui);
      ui.keyClick(WT.HOME);
      MiscUtils.waitForJobs(ui);
   }

   private void stepOperation(boolean FW, int step, IUIContext ui)
         throws Exception
   {
      while (step != 0)
      {
         if (FW)
         {
            ui.keyClick(WT.ARROW_DOWN);
         }
         else
         {
            ui.keyClick(WT.ARROW_UP);
         }
         step--;
         MiscUtils.waitForJobs(ui);
      }
   }
}
