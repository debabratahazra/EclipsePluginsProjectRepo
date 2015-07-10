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

import com.ose.ui.tests.AbstractBaseTest;
import com.ose.ui.tests.utils.ResourceUtils;
import com.windowtester.runtime.IUIContext;
import com.windowtester.runtime.swt.condition.shell.ShellDisposedCondition;
import com.windowtester.runtime.swt.condition.shell.ShellShowingCondition;
import com.windowtester.runtime.swt.locator.ButtonLocator;
import com.windowtester.runtime.swt.locator.CTabItemLocator;
import com.windowtester.runtime.swt.locator.ComboItemLocator;
import com.windowtester.runtime.swt.locator.LabeledTextLocator;
import com.windowtester.runtime.swt.locator.MenuItemLocator;
import com.windowtester.runtime.swt.locator.TableItemLocator;
import com.windowtester.runtime.swt.locator.TreeItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.ContributedToolItemLocator;
import com.windowtester.runtime.swt.locator.eclipse.PerspectiveLocator;
import com.windowtester.runtime.swt.locator.eclipse.ViewLocator;

public class TestProfilerEditor extends AbstractBaseTest
{

   private static final String PROJECT_NAME = "pp_files";

   private static final String PROCESS_FILENAME = "new_file.process";

   protected void oneTimeSetup() throws Exception
   {
      super.oneTimeSetup();

      IUIContext ui = getUI();
      ui.ensureThat(PerspectiveLocator.forName("System Browsing").isClosed());
      ui.ensureThat(PerspectiveLocator.forName("System Profiling").isActive());

      // Create a new Project
      ResourceUtils.createNewProject(ui, PROJECT_NAME);
   }

   protected void oneTimeTearDown() throws Exception
   {
      ResourceUtils.deleteProject(getUI(), PROJECT_NAME);
      super.oneTimeTearDown();
   }

   public void setUp() throws Exception
   {
      super.setUp();
   }

   public void tearDown() throws Exception
   {
      super.tearDown();
   }

   public void testProfilerEditorCreateFile() throws Exception
   {
      IUIContext ui = getUI();

      ui.click(new TreeItemLocator(PROJECT_NAME, new ViewLocator(
            "org.eclipse.ui.navigator.ProjectExplorer")));
      ui.click(new MenuItemLocator("File/New/Profiled Processes Settings"));
      ui.wait(new ShellShowingCondition("New Profiled Processes Settings"));
      ui.click(new LabeledTextLocator("File na&me:"));
      ui.enterText("new_file.process");
      ui.click(new ButtonLocator("&Finish"));
      ui.wait(new ShellDisposedCondition("New Profiled Processes Settings"));
      // Close the editor
      ui.ensureThat(new CTabItemLocator("new_file.process").isClosed());
   }

   public void testProfilerEditorAddProc() throws Exception
   {
      IUIContext ui = getUI();

      // Open the editor
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/" + PROCESS_FILENAME,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")));

      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("dots");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("Name Pattern"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("ping");
      ui.click(new ButtonLocator("Add..."));
      ui.click(new ComboItemLocator("ID"));
      ui.click(new LabeledTextLocator("Process:"));
      ui.enterText("0x10000");

      // Save the file
      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.save"));

      ui.ensureThat(new CTabItemLocator(PROCESS_FILENAME).isClosed());
   }

   public void testProfilerEditorRemoveProc() throws Exception
   {
      IUIContext ui = getUI();

      // Open the file
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/" + PROCESS_FILENAME,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")));

      ui.click(new TableItemLocator("ping"));
      ui.click(new ButtonLocator("Remove"));

      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.save"));
      ui.ensureThat(new CTabItemLocator(PROCESS_FILENAME).isClosed());
   }

   public void testProfilerEditorEditProc() throws Exception
   {
      IUIContext ui = getUI();

      // Open the file
      ui.click(2, new TreeItemLocator(PROJECT_NAME + "/" + PROCESS_FILENAME,
            new ViewLocator("org.eclipse.ui.navigator.ProjectExplorer")));

      ui.click(new TableItemLocator("dots"));
      ui.click(new ComboItemLocator("ID"));
      ui.click(2, new LabeledTextLocator("Process:"));
      ui.enterText("0x10001");

      ui.click(new ContributedToolItemLocator("org.eclipse.ui.file.save"));
      ui.ensureThat(new CTabItemLocator(PROCESS_FILENAME).isClosed());
   }

   public void testProfilerEditorOpenFile() throws Exception
   {
      IUIContext ui = getUI();

      ui.ensureThat(new CTabItemLocator(PROCESS_FILENAME).isClosed());
      ui.contextClick(new TreeItemLocator(
            PROJECT_NAME + "/" + PROCESS_FILENAME, new ViewLocator(
                  "org.eclipse.ui.navigator.ProjectExplorer")),
            "Open With/Profiled Processes Editor");
      ui.ensureThat(new CTabItemLocator(PROCESS_FILENAME).isClosed());
   }
}
