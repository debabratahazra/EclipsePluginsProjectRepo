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

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Spinner;
import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.XYLocator;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CComboItemLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.LabeledLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TableCellLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestTextImportWizard extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_TextImport";

   private static final String TEXT_FILENAME = "printouts.txt";

   private static final String LOG_FILENAME = "printouts.log";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();
   }

   protected void setUp() throws Exception
   {
      super.setUp();

      LogAnalyzerUtil.createLogAnalyzerProject(PROJECT_NAME, getUI());
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();

      LogAnalyzerUtil.deleteProject(PROJECT_NAME, getUI());
   }

   protected void oneTimeTearDown() throws Exception
   {
      super.oneTimeTearDown();
   }

   /**
    * Import Text logfile using Import wizard.
    * 
    * @throws Exception
    */
   public void testImportWizard() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            TEXT_FILENAME, ui);
      openImportWizard(ui,TEXT_FILENAME);
      importWizard(ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", TEXT_FILENAME,
            ui);
   }

   /**
    * Handle Exception during import .log file
    * 
    * @throws Exception
    */
   public void testErrorHandling() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            LOG_FILENAME, ui);
      openImportWizard(ui, LOG_FILENAME);
      importWizard(ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.TEXT_BROWSER, ui);
      LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset", LOG_FILENAME,
            ui);

      editLogfile(ui);
      LogAnalyzerUtil
            .configureLogfile(PROJECT_NAME, "logset", LOG_FILENAME, ui);
      exceptionDialogShown(ui);

   }

   private void exceptionDialogShown(IUIContext ui) throws Exception
   {
      ui.wait(new ShellShowingCondition("Failed to import file: "));
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Failed to import file: "));
   }

   private void editLogfile(IUIContext ui) throws Exception
   {
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/logset/printouts.log",
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)));
      ui.click(new XYLocator(new SWTWidgetLocator(StyledText.class), 33, 78));
      ui.keyClick(WT.DEL);
      ui.enterText(":");
      ui.keyClick(WT.ARROW_RIGHT);
      ui.keyClick(WT.ARROW_RIGHT);
      ui.keyClick(WT.DEL);
      ui.enterText(":");
      ui.keyClick(WT.ARROW_RIGHT);
      ui.keyClick(WT.CTRL, 's');
   }

   private void importWizard(IUIContext ui) throws Exception
   {
      ui.click(1, new ButtonLocator("&Next >"));
      ui.click(2, new LabeledLocator(Spinner.class, "Number of header lines"));
      ui.enterText("3");
      ui.click(new ButtonLocator("&Next >"));
      ui.click(1, new TableCellLocator(1, 2));
      ui.click(1, new XYLocator(new CComboItemLocator("String"), 90, 8));
      ui.click(new CComboItemLocator("String"));
      ui.click(new CComboItemLocator("Timestamp (Y-M-D)"));
      ui.click(1, new TableCellLocator(1, 3));
      ui.enterText(" ");
      ui.click(1, new TableCellLocator(2, 2));
      ui.click(new XYLocator(new CComboItemLocator("String"), 69, 5));
      ui.click(new CComboItemLocator("String"));
      ui.click(new CComboItemLocator("Timestamp (H:M:S:us)"));
      ui.click(1, new TableCellLocator(2, 3));
      ui.enterText(" ");
      ui.click(1, new TableCellLocator(3, 2));
      ui.click(new XYLocator(new CComboItemLocator("String"), 69, 5));
      ui.click(new CComboItemLocator("String"));
      ui.click(new CComboItemLocator("Event Type Name"));
      ui.click(1, new TableCellLocator(3, 3));
      ui.enterText("\\s\\r");
      ui.click(1, new TableCellLocator(4, 3));

      ui.click(new CTabItemLocator("Filters"));
      ui.click(new LabeledTextLocator("Regular expression:"));
      ui.enterText(".*Temperature.*");
      ui.click(new ButtonLocator("Add"));

      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("Generic Import Wizard"));
   }

   private void openImportWizard(IUIContext ui, String fileName) throws Exception
   {
      ui.contextClick(new TreeItemLocator(PROJECT_NAME + "/logset/" + fileName,
            new ViewLocator(LogAnalyzerUtil.SYSTEM_NAVIGATOR_VIEW_ID)),
            "Import Wizard...");
      ui.wait(new ShellShowingCondition("Generic Import Wizard"));
   }
}
