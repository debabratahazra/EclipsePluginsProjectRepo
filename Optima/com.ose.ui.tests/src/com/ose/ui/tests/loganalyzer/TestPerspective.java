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
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestPerspective extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_Perspective";

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
    * Create Log Analyzer Project and test Log Analyzer Perspective.
    * 
    * @throws Exception
    */
   public void testPerspective() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);

      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);

      changePerspective(ui);
   }

   private void changePerspective(IUIContext ui) throws Exception
   {
      ui.click(new MenuItemLocator("Window/Open Perspective/Other..."));
      ui.wait(new ShellShowingCondition("Open Perspective"));
      ui.click(new TableItemLocator("System Browsing"));
      ui.click(new ButtonLocator("OK"));
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isActive());
      ui.click(new MenuItemLocator("Window/Open Perspective/Other..."));
      ui.wait(new ShellShowingCondition("Open Perspective"));
      ui.click(new TableItemLocator("Log Analyzer"));
      ui.click(new ButtonLocator("OK"));
      ui.ensureThat(PerspectiveLocator.forName("Log Analyzer").isActive());
      ui.click(new MenuItemLocator("Window/Reset Perspective..."));
      ui.wait(new ShellShowingCondition("Reset Perspective"));
      ui.keyClick(WT.CR);
      ui.ensureThat(new ViewLocator(LogAnalyzerUtil.OVERVIEW_VIEW_ID).isClosed());
   }
}
