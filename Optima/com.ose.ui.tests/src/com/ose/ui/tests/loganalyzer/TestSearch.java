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

import org.eclipse.swt.widgets.Group;

import com.ose.ui.tests.AbstractLABaseTest;
import com.ose.ui.tests.utils.LogAnalyzerUtil;
import com.ose.ui.tests.utils.MiscUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.WT;
import com.windowtester.runtime.locator.IWidgetReference;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.FilteredTreeItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.SWTWidgetLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestSearch extends AbstractLABaseTest
{
   private static final String PROJECT_NAME = "Project_Search";

   private static final String SEARCH_VIEW_ID = "org.eclipse.search.ui.views.SearchView";

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
    * Test Search
    * 
    * @throws Exception
    */
   public void testSearch() throws Exception
   {
      IUIContext ui = getUI();

      LogAnalyzerUtil.importLogfile(getLogfilePath(), PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      LogAnalyzerUtil.openLogsetEditor(PROJECT_NAME, "logset",
            LogAnalyzerUtil.GANTT_CHART, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "Error", ui,6);
      searchError(".*peng.*", ui);
      IWidgetReference[] references = (IWidgetReference[]) ui
            .findAll(new TreeItemLocator(".*", new ViewLocator(SEARCH_VIEW_ID)));
      
      ui.click(2, references[0]);
      showSearchPreferencePage(ui);
      LogAnalyzerUtil.deconfigureLogfile(PROJECT_NAME, "logset",
            "OSEdelta.pmd", ui);
      LogAnalyzerUtil.configureLogfile(PROJECT_NAME, "logset", "OSEdelta.pmd",
            ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "Send", ui,33);
      searchSend("debugping.*", false, false, true, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "Send", ui,49);
      searchSend("*ping.*", false, true, false, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
              "Generic Type Package", "Send", ui,49);
      searchSend(".*p?ng.*", false, false, true, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "Send", ui,49);
      searchSend(".*debugping:ping.*", true, true, true, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "User", ui,49);
      searchUser(false, ui);
      LogAnalyzerUtil.openSearchDialog(PROJECT_NAME, "logset",
            "Generic Type Package", "User", ui,33);
      searchUser(true, ui);
   }

   private void searchUser(boolean not, IUIContext ui) throws Exception
   {
      ui.click(new LabeledTextLocator("Entry"));
      ui.enterText("40");
      ui.keyClick(WT.TAB);
      ui.enterText("50");
      if (not)
      {
         ui.click(new ButtonLocator("Not", 1, new SWTWidgetLocator(Group.class,
               "User")));
      }
      ui.click(new ButtonLocator("&Search"));
      ui.wait(new ShellDisposedCondition("Search"));
      ui.wait(new ShellDisposedCondition("Search for User"));
   }

   private void showSearchPreferencePage(IUIContext ui) throws Exception
   {
      ui.click(new MenuItemLocator("Window/Preferences"));
      ui.wait(new ShellShowingCondition("Preferences"));
      ui.click(new FilteredTreeItemLocator("Log Analyzer/Search"));
      MiscUtils.waitForJobs(ui);
      ui.click(new ButtonLocator("OK"));
      ui.wait(new ShellDisposedCondition("Preferences"));
   }

   private void searchSend(String text, boolean not, boolean regx,
         boolean success, IUIContext ui) throws Exception
   {
      int notCheck = 0;
      ui.click(2, new LabeledTextLocator("Date"));
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.enterText(text);
      if (not)
      {
         ui.keyClick(WT.TAB);
         ui.enterText(" ");
         notCheck++;
      }
      if (regx)
      {
         for (int i = 0; i < 38 - notCheck; i++)
         {
            ui.keyClick(WT.TAB);
         }
         ui.enterText(" ");
      }
      ui.click(new ButtonLocator("&Search"));
      if (success)
      {
         ui.wait(new ShellDisposedCondition("Search"));
         ui.wait(new ShellDisposedCondition("Search for Send"));
      }
      else
      {
    	  ui.click(new ButtonLocator("Cancel"));
      }
   }

   private void searchError(String text, IUIContext ui) throws Exception
   {
      ui.click(2, new LabeledTextLocator("Date"));
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.keyClick(WT.TAB);
      ui.enterText(text);
      ui.click(new ButtonLocator("&Search"));
      ui.wait(new ShellDisposedCondition("Search"));
      ui.wait(new ShellDisposedCondition("Search for Error"));
   }
}
