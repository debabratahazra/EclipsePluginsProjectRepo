package com.ose.ui.tests.loganalyzer;

import java.io.File;
import org.eclipse.swt.widgets.Tree;
import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestCTFImporter extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_CTF";

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
   }

   public void testCTFImporter() throws Exception
   {
      IUIContext ui = getUI();
      File lttnglogsDir = new File(getLogfilePath(), "kernel");

      LogAnalyzerUtil.importCTFLogfile(lttnglogsDir.getAbsolutePath(),
            PROJECT_NAME, "logset", ui);
      MiscUtils.waitForJobs(ui);
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);

      stepOperation(true, 15, ui);
      stepOperation(false, 15, ui);
      ui.keyClick(WT.END);
      MiscUtils.waitForJobs(ui);
      ui.keyClick(WT.HOME);
      MiscUtils.waitForJobs(ui);

      ui.click(new TreeItemLocator("Project_CTF", new ViewLocator(
            "com.zealcore.se.ui.views.SystemNavigator")));

      // TODO: Current implementation uses Linux Tools CTF parser to import log
      // files. Hence Project cannot be deleted after importing the files, but
      // after few minutes it can be deleted. So project is closed instead of
      // deleting it.
      ui.click(new TreeItemLocator("Project_CTF", new ViewLocator(
            "com.zealcore.se.ui.views.SystemNavigator")));
      ui.contextClick(new SWTWidgetLocator(Tree.class, new ViewLocator(
            "com.zealcore.se.ui.views.SystemNavigator")), "Close Project");
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
