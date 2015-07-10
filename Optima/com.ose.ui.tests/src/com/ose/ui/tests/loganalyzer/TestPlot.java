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

import org.eclipse.swt.widgets.Canvas;
import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestPlot extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_Plot";

   private static final String PLOT_VIEW_ID = "com.zealcore.se.ui.views.PlottableViewer";

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

   public void testPlot() throws Exception
   {

      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.PLOT, ui);
      plotDialog("ProcessExecution", ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.PLOT, ui);
      plotDialog("Process", ui);
   }

   private void plotDialog(String event, IUIContext ui) throws Exception
   {

      ui.wait(new ShellShowingCondition("Open Plot"));
      ui.click(new ButtonLocator("Select Type"));
      ui.wait(new ShellShowingCondition("Select Type"));
      ui.click(new TreeItemLocator("Generic Type Package/" + event));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Select Type"));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Open Plot"));
      ui.click(new XYLocator(new SWTWidgetLocator(Canvas.class,
            new ViewLocator(PLOT_VIEW_ID)), 110, 228));
      MiscUtils.waitForJobs(ui);
   }

}
